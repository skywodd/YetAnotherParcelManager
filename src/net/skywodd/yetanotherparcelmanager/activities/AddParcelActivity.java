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

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.skywodd.yetanotherparcelmanager.R;
import net.skywodd.yetanotherparcelmanager.helpers.GPSHelper;
import net.skywodd.yetanotherparcelmanager.helpers.ReverseGeocodingTask;
import net.skywodd.yetanotherparcelmanager.helpers.TaskFinishedListener;
import net.skywodd.yetanotherparcelmanager.models.Growing;
import net.skywodd.yetanotherparcelmanager.models.Parcel;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This class manage the AddParcelActivity. she manage to send the parcel
 * informations to the database with ORM.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class AddParcelActivity extends AbstractBaseActivity implements
		TaskFinishedListener {

	/** Extra key for adding or updating */
	public static final String EXTRA_PARCEL_ADD = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_ADD";

	/** Extra key for Parcel id */
	public static final String EXTRA_PARCEL_ID = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_ID";

	/** parcel Data for the Modify Version of this Activity */
	private Parcel parcelToShow;

	/** Boolean to test if it's the adding version of this activity */
	private Boolean adding;

	/** Parameters for the Camera use; */
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	/** The Uri of the picture taken by the intent */
	private Uri fileUri;

	/** Parameters for the GPS use and display */
	private GPSHelper gpsService;

	/** The details of the location of the parcel */
	private EditText latitude, longitude, adresse;

	/** A buffer to put the Address when the Gps Thread find it */
	private String bufAddress;

	/** The numberPicker for the parcel Surface */
	private NumberPicker surface;

	/** The parcel name */
	private EditText nom;

	/** The spinners for Growing and Last Growing */
	private Spinner growing, last_growing;

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
		setContentView(R.layout.activity_add_parcel);
		// Testing if the activity must be launch as Add or Midify Acitivity
		adding = getIntent().getExtras().getBoolean(EXTRA_PARCEL_ADD);

		nom = (EditText) findViewById(R.id.tf_parcel_name);
		growing = (Spinner) findViewById(R.id.sp_parcel_actual_culture);
		last_growing = (Spinner) findViewById(R.id.sp_parcel_previous_culture);

		// The Camera launch Button
		Button photo = (Button) findViewById(R.id.btn_picture_parcel);

		photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // create Intent to take a picture and
											// return control to the calling
											// application
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE,
						AddParcelActivity.this); // create a
				// file to
				// save the
				// image
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the
																	// image
																	// file name

				// start the image capture Intent
				startActivityForResult(intent,
						CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

			}
		});

		// Initialize the class to get Localization informations
		gpsService = new GPSHelper(this);

		latitude = (EditText) findViewById(R.id.tf_parcel_latitude);
		longitude = (EditText) findViewById(R.id.tf_parcel_longitude);
		adresse = (EditText) findViewById(R.id.tf_parcel_address);

		// Button to get Localization
		Button gps = (Button) findViewById(R.id.btn_gps_parcel);

		gps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (gpsService.canGetLocation()) {
					Location loc = gpsService.getLocation();
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
							&& Geocoder.isPresent()) {
						ReverseGeocodingTask reverseGeocodingTask = new ReverseGeocodingTask(
								AddParcelActivity.this, AddParcelActivity.this,
								loc);
						reverseGeocodingTask.start();
					}
					latitude.setText(Double.toString(loc.getLatitude()));
					longitude.setText(Double.toString(loc.getLongitude()));
					adresse.setText(bufAddress);
				}
			}
		});

		// Initializing the numberPicker for the Surface.
		surface = (NumberPicker) findViewById(R.id.np_parcel_surface);

		String[] nums = new String[21];

		for (int i = 0; i < nums.length; i++)
			nums[i] = Integer.toString(i);
		surface.setMaxValue(nums.length - 1);
		surface.setMinValue(0);
		surface.setWrapSelectorWheel(false);
		surface.setDisplayedValues(nums);

		// Initialize the Button For adding or modifying
		Button add = (Button) findViewById(R.id.btn_add_parcel);
		if (!adding) {
			add.setText(R.string.btn_label_update_parcel);
		}

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!adding) {
					// Delete the Old Version of the parcel
					getHelper().getParcelDao().deleteById(
							(int) getIntent().getExtras().getLong(
									EXTRA_PARCEL_ID));
				}
				// Test if the Edittexts are all completed
				if (!nom.getText().toString().equals("")
						&& !adresse.getText().toString().equals("")
						&& !latitude.getText().toString().equals("")
						&& !longitude.getText().toString().equals("")) {
					try {
						String filePath = null;
						if (fileUri != null) {
							if (fileUri.toString().contains("file")) {
								filePath = fileUri.toString().substring(6);
							} else {
								filePath = fileUri.toString();
							}
						}
						getHelper().getDao().create(
								new Parcel(nom.getText().toString(), Growing
										.values()[growing
										.getSelectedItemPosition()], Growing
										.values()[last_growing
										.getSelectedItemPosition()], surface
										.getValue(), filePath, Double
										.parseDouble(latitude.getText()
												.toString()), Double
										.parseDouble(longitude.getText()
												.toString()), adresse.getText()
										.toString()));
						if (adding) {

							Toast.makeText(AddParcelActivity.this,
									"Parcel added", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(AddParcelActivity.this,
									"Parcel Updated", Toast.LENGTH_SHORT)
									.show();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			}
		});

		if (!adding) {
			Object buf[] = getHelper().getParcelDao().queryForAll().toArray();
			if (buf.length > 0) {
				Parcel parcelData[] = new Parcel[buf.length];
				for (int i = 0; i < buf.length; ++i)
					parcelData[i] = (Parcel) buf[i];
				for (Parcel p : parcelData) {
					if (p.getId() == getIntent().getExtras().getLong(
							EXTRA_PARCEL_ID)) {
						parcelToShow = p;
					}
				}
			}

			latitude.setText(Double.toString(parcelToShow.getLatitude()));
			longitude.setText(Double.toString(parcelToShow.getLongitude()));
			adresse.setText(parcelToShow.getAddress());
			surface.setValue(parcelToShow.getSurface());
			nom.setText(parcelToShow.getName());
			growing.setSelection(parcelToShow.getGrowing().ordinal());
			last_growing.setSelection(parcelToShow.getLastGrowing().ordinal());

			fileUri = Uri.parse(parcelToShow.getImage());

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (fileUri != null) {
			outState.putString("cameraImageUri", fileUri.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState.containsKey("cameraImageUri")) {
			fileUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */

	@Override
	public void onPause() {
		super.onPause();
		gpsService.stopUsingGPS();
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type, Context parent) {
		return Uri.fromFile(getOutputMediaFile(type, parent));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type, Context parent) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			File mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					parent.getString(R.string.images_folder));
			// This location works best if you want the created images to be
			// shared
			// between applications and persist after your app has been
			// uninstalled.

			// Create the storage directory if it does not exist
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d(parent.getString(R.string.app_name),
							"failed to create directory");
					return null;
				}
			}

			// Create a media file name
			String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss",
					Locale.FRANCE).format(new Date());
			File mediaFile;
			if (type == MEDIA_TYPE_IMAGE) {
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ "IMG_" + timeStamp + ".jpg");
			} else if (type == MEDIA_TYPE_VIDEO) {
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ "VID_" + timeStamp + ".mp4");
			} else {
				return null;
			}
			return mediaFile;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.skywodd.yetanotherparcelmanager.helpers.TaskFinishedListener#
	 * onTaskCompleted(java.lang.String)
	 */
	@Override
	public void onTaskCompleted(String value) {
		if (value != null) {
			bufAddress = value;
		}
	}

}
