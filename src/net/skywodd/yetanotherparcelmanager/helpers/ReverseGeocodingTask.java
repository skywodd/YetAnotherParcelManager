/*
 *  This file is part of YetAnotherParcelManager.
 *
 *   YetAnotherParcelManager is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   YetAnotherParcelManager is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with YetAnotherParcelManager. If not, see <http://www.gnu.org/licenses/>.
 */
package net.skywodd.yetanotherparcelmanager.helpers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

/**
 * A class to get an Address from a location (Latitude, Longitude).
 * 
 * @author Linkdelaudela
 * @Verison 1.0.0
 */
public class ReverseGeocodingTask extends Thread {
	Context mContext;
	String mAddress;
	Location mParams;
	TaskFinishedListener mListener;

	public ReverseGeocodingTask(Context context, TaskFinishedListener listener,
			Location params) {
		super();
		mContext = context;
		mListener = listener;
		mParams = params;
	}

	@Override
	public void run() {
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		List<Address> addresses = null;

		try {
			addresses = geocoder.getFromLocation(mParams.getLatitude(),
					mParams.getLongitude(), 1);
		} catch (IOException e) {
			e.printStackTrace();
			mAddress = e.toString();
		}

		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			mAddress = String.format("%s, %s, %s", address
					.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0)
					: "", address.getLocality(), address.getCountryName());

		}
		mListener.onTaskCompleted(mAddress);

	}

}
