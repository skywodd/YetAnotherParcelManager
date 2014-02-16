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
import net.skywodd.yetanotherparcelmanager.models.Parcel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class manage InfoParcelActivity, she gets the parcel information from
 * the database with ORM.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class InfoParcelActivity extends AbstractBaseActivity {

	/** Extra key for the parcel ID */
	public static final String EXTRA_PARCEL_ID = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_ID";

	private TextView name, growing, last_growing, surface, latitude, longitude,
			address;
	private ImageView image;

	private Context mContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.skywodd.yetanotherparcelmanager.activities.AbstractBaseActivity#onCreate
	 * (android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_parcel);

		mContext = getApplicationContext();
		name = (TextView) findViewById(R.id.tv_info_parcel_name);
		growing = (TextView) findViewById(R.id.tv_info_culture_actual);
		last_growing = (TextView) findViewById(R.id.tv_info_culture_last);
		surface = (TextView) findViewById(R.id.tv_info_parcel_surface);
		latitude = (TextView) findViewById(R.id.tv_info_parcel_latitude);
		longitude = (TextView) findViewById(R.id.tv_info_parcel_longitude);
		address = (TextView) findViewById(R.id.tv_info_parcel_address);
		image = (ImageView) findViewById(R.id.img_parcel_info);

		Button maps = (Button) findViewById(R.id.btn_maps_parcel);
		maps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, LocalisationActivity.class);
				intent.putExtra(LocalisationActivity.EXTRA_PARCEL_NAME, name
						.getText().toString());
				intent.putExtra(LocalisationActivity.EXTRA_PARCEL_LAT,
						Double.parseDouble(latitude.getText().toString()));
				intent.putExtra(LocalisationActivity.EXTRA_PARCEL_LONG,
						Double.parseDouble(longitude.getText().toString()));

				startActivity(intent);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Parcel parcelToShow = null;
		long id = getIntent().getExtras().getLong(EXTRA_PARCEL_ID);
		Object buf[] = getHelper().getParcelDao().queryForAll().toArray();
		if (buf.length > 0) {
			Parcel parcelData[] = new Parcel[buf.length];
			for (int i = 0; i < buf.length; ++i)
				parcelData[i] = (Parcel) buf[i];
			for (Parcel p : parcelData) {
				if (p.getId() == id) {
					parcelToShow = p;
				}
			}
		}

		if (buf != null) {
			name.setText(parcelToShow.getName());
			growing.setText(mContext.getResources().getStringArray(
					R.array.array_cultures_types)[parcelToShow.getGrowing()
					.ordinal()]);
			last_growing.setText(mContext.getResources().getStringArray(
					R.array.array_cultures_types)[parcelToShow.getLastGrowing()
					.ordinal()]);
			surface.setText(Integer.toString(parcelToShow.getSurface()));
			latitude.setText(Double.toString(parcelToShow.getLatitude()));
			longitude.setText(Double.toString(parcelToShow.getLongitude()));
			address.setText(parcelToShow.getAddress());
			Options op = new Options();
			op.inSampleSize = 5;// You want to scale from 500 to 100
			if (parcelToShow.getImage() != null) {
				Bitmap thumbnail = BitmapFactory.decodeFile(
						parcelToShow.getImage(), op);
				image.setImageBitmap(thumbnail);
			}
		}
	}
}
