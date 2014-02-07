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

package net.skywodd.yetanotherparcelmanager.activities.parcels;

import net.skywodd.yetanotherparcelmanager.R;
import net.skywodd.yetanotherparcelmanager.activities.AbstractBaseActivity;
import net.skywodd.yetanotherparcelmanager.activities.menus.ControlMenuActivity;
import net.skywodd.yetanotherparcelmanager.activities.menus.ConverterMenuActivity;
import net.skywodd.yetanotherparcelmanager.helpers.ParcelAdapter;
import net.skywodd.yetanotherparcelmanager.models.Growing;
import net.skywodd.yetanotherparcelmanager.models.Parcel;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * This class manage the ListParcelActivity, by getting the information from the
 * ORM.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class ListParcelActivity extends AbstractBaseActivity {

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

		Parcel Parcel_data[] = new Parcel[] { 
				new Parcel("Test1",Growing.CORN,Growing.COLZA,5, null,0d, 0d, "test1"), 
				new Parcel("Test2",Growing.CORN,Growing.COLZA,5, null,0d, 0d, "test2"),

				new Parcel("Test3",Growing.CORN,Growing.COLZA,5, null,0d, 0d, "test3"),
				new Parcel("Test4",Growing.CORN,Growing.COLZA,5, null,0d, 0d, "test4"),
				new Parcel("Test5",Growing.CORN,Growing.COLZA,5, null,0d, 0d, "test5") };
		ParcelAdapter adapter = new ParcelAdapter(this,
				R.layout.listview_item_row, Parcel_data);

		ListView lv = (ListView) findViewById(R.id.list_parcel);

		View header = (View) getLayoutInflater().inflate(
				R.layout.listview_header_row, null);
		lv.addHeaderView(header);

		lv.setAdapter(adapter);

	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_add, menu);
		return true;

	}
	

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_add_parcel:
	        	intent = new Intent(this, AddParcelActivity.class);
	    	    startActivity(intent);
	            return true;
	      
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
