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

import java.util.ArrayList;

import net.skywodd.yetanotherparcelmanager.R;
import net.skywodd.yetanotherparcelmanager.helpers.ParcelAdapter;
import net.skywodd.yetanotherparcelmanager.models.Parcel;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This class manage the ListParcelActivity, by getting the information from the
 * ORM.
 * 
 * @author Linkdelaudela
 * @author skywodd
 * @version 1.0.0
 */
public class ListParcelActivity extends AbstractBaseActivity {

	/** Listview for the parcel list */
	private ListView parcelsList;

	private ParcelAdapter adapter;

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
		setContentView(R.layout.activity_list_parcel);
		ArrayList<Parcel> parcelData = chargeParcelsFromDatabase();
		loadList("onCreate", parcelData);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// Create the list view adapter
		// for Parcel data.
		ArrayList<Parcel> parcelData = chargeParcelsFromDatabase();
		loadList("onResume", parcelData);

	};

	/**
	 * The function to load the Parcel list on ListView.
	 * 
	 * @param function The calling Fucntion to avoid multiple headers
	 * @param parcelData The list of parcel to display
	 */
	public void loadList(String function, ArrayList<Parcel> parcelData) {
		if (parcelData != null) {
			adapter = new ParcelAdapter(this, R.layout.listview_item_row,
					parcelData);

			if (function.equals("onCreate")) {
				// Create the list header.
				View header = (View) getLayoutInflater().inflate(
						R.layout.listview_header_row, null);

				// Add the lit header and bind the adapter.
				parcelsList = (ListView) findViewById(R.id.list_parcel);
				parcelsList.addHeaderView(header);
				parcelsList.setAdapter(adapter);

				// Register for context menu creation
				registerForContextMenu(parcelsList);
			} else {
				parcelsList = (ListView) findViewById(R.id.list_parcel);
				parcelsList.setAdapter(adapter);
			}
		} else {
			Toast.makeText(this, "Database is empty", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * A function to get The parcel List from Database
	 * @return The list of parcel from the database
	 */
	public ArrayList<Parcel> chargeParcelsFromDatabase() {
		// Query all known parcel data

		Object buf[] = getHelper().getParcelDao().queryForAll().toArray();
		if (buf.length > 0) {
			ArrayList<Parcel> parcelData = new ArrayList<Parcel>();
			for (int i = 0; i < buf.length; ++i)
				parcelData.add((Parcel) buf[i]);
			if (parcelData.size() > 0) {

				return parcelData;
			}
			return null;
		} else {

			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Extend the default menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_parcel_add, menu);
		// Remove itself from the default menu
		menu.removeItem(R.id.action_parcel);

		// Menu created
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_add_parcel: // Add parcel action
			Intent t = new Intent(this, AddParcelActivity.class);
			t.putExtra(AddParcelActivity.EXTRA_PARCEL_ID, 0);
			t.putExtra(AddParcelActivity.EXTRA_PARCEL_ADD, true);
			startActivity(t);
			return true;

		default: // Call super for action handling
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
	 * android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		// Handle context menu creation
		switch (v.getId()) {
		case R.id.list_parcel: // Display the list context menu
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Parcel p = (Parcel) parcelsList.getItemAtPosition(info.position);
			menu.setHeaderTitle(p.getName());
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu_parcel_edit, menu);
			break;

		default: // Delegate context menu creation
			super.onCreateContextMenu(menu, v, menuInfo);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_show_parcel: { // Show the parcel
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			Parcel p = (Parcel) parcelsList.getItemAtPosition(info.position);
			Intent t = new Intent(this, InfoParcelActivity.class);
			t.putExtra(InfoParcelActivity.EXTRA_PARCEL_ID, p.getId());
			startActivity(t);
		}
			break;

		case R.id.action_edit_parcel: { 
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
		
			Parcel p = (Parcel) parcelsList.getItemAtPosition(info.position);
			Intent t = new Intent(this, AddParcelActivity.class);
			t.putExtra(AddParcelActivity.EXTRA_PARCEL_ID, p.getId());
			t.putExtra(AddParcelActivity.EXTRA_PARCEL_ADD, false);
			startActivity(t);
		} 
			break;

		case R.id.action_delete_parcel: { // Delete the parcel
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			Parcel p = (Parcel) parcelsList.getItemAtPosition(info.position);
			getHelper().getParcelDao().delete(p);
			adapter.remove(adapter.getItem(info.position-1));
			adapter.notifyDataSetChanged();
		}
			break;

		default: // Delegate item selection handling
			return super.onContextItemSelected(item);
		}

		// Item selection handled
		return true;
	}
}
