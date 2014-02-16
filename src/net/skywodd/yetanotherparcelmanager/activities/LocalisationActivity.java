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
package net.skywodd.yetanotherparcelmanager.activities;

import net.skywodd.yetanotherparcelmanager.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An activity to display the Localization of a Parcel on a map.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class LocalisationActivity extends FragmentActivity {

	/** Extra key for the parcel Lat */
	public static final String EXTRA_PARCEL_LAT = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_LAT";

	/** Extra key for the parcel Long */
	public static final String EXTRA_PARCEL_LONG = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_LONG";

	/** Extra key for the parcel name */
	public static final String EXTRA_PARCEL_NAME = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_NAME";

	private GoogleMap mGoogleMap;
	private Marker mMarker;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_parcel);
		mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map_localisation_parcel)).getMap();
		LatLng position = new LatLng(getIntent().getExtras().getDouble(
				EXTRA_PARCEL_LAT), getIntent().getExtras().getDouble(
				EXTRA_PARCEL_LONG));
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
		mMarker = mGoogleMap.addMarker(new MarkerOptions().title(
				getIntent().getExtras().getString(EXTRA_PARCEL_NAME)).position(
				position));
		mMarker.setPosition(position);
	}
}
