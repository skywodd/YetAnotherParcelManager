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
import net.skywodd.yetanotherparcelmanager.helpers.ParcelAdapter;
import net.skywodd.yetanotherparcelmanager.models.Growing;
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

/**
 * This class manage the ListParcelActivity, by getting the information from the
 * ORM.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class ListParcelActivity extends AbstractBaseActivity {

	/** Listview for the parcel list */
	private ListView parcelsList;

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

		// Dummy data for testing purpose
		Parcel parcelData[] = new Parcel[] {
				new Parcel("Test1", Growing.CORN, Growing.COLZA, 5, null, 0d,
						0d, "test1"),
				new Parcel("Test2", Growing.CORN, Growing.COLZA, 5, null, 0d,
						0d, "test2"),
				new Parcel("Test3", Growing.CORN, Growing.COLZA, 5, null, 0d,
						0d, "test3"),
				new Parcel("Test4", Growing.CORN, Growing.COLZA, 5, null, 0d,
						0d, "test4"),
				new Parcel("Test5", Growing.CORN, Growing.COLZA, 5, null, 0d,
						0d, "test5") };

		// Create the list view adapter for Parcel data.
		ParcelAdapter adapter = new ParcelAdapter(this,
				R.layout.listview_item_row, parcelData);

		// Create the list header.
		View header = (View) getLayoutInflater().inflate(
				R.layout.listview_header_row, null);

		// Add the lit header and bind the adapter.
		parcelsList = (ListView) findViewById(R.id.list_parcel);
		parcelsList.addHeaderView(header);
		parcelsList.setAdapter(adapter);

		// Register for context menu creation
		registerForContextMenu(parcelsList);
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
		inflater.inflate(R.menu.menu_add, menu);
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
			startActivity(new Intent(this, AddParcelActivity.class));
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
		case R.id.list_parcel:
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Parcel p = (Parcel) parcelsList.getItemAtPosition(info.position);
			menu.setHeaderTitle(p.getName());
			menu.add(Menu.NONE, 0, 0, "Test");
			break;

		default:
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
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}
}
