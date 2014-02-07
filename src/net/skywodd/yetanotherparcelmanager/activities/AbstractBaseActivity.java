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
import net.skywodd.yetanotherparcelmanager.activities.menus.CommandMenuActivity;
import net.skywodd.yetanotherparcelmanager.activities.menus.ControlMenuActivity;
import net.skywodd.yetanotherparcelmanager.activities.menus.ConverterMenuActivity;
import net.skywodd.yetanotherparcelmanager.activities.parcels.ListParcelActivity;
import net.skywodd.yetanotherparcelmanager.helpers.DatabaseHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * This class is the base for the creation of every other activity, 
 * she implement the creation of an option menu which will be used in other activties
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */

public class AbstractBaseActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	/*
	 * (non-Javadoc)
	 * @see com.j256.ormlite.android.apptools.OrmLiteBaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/*
	 * (non-Javadoc)
	 * @see com.j256.ormlite.android.apptools.OrmLiteBaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_main, menu);
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
	        case R.id.action_command:
	        	intent = new Intent(this, CommandMenuActivity.class);
	    	    startActivity(intent);
	            return true;
	        case R.id.action_control:
	        	intent  = new Intent(this, ControlMenuActivity.class);
	    	    startActivity(intent);
	            return true;
	        case R.id.action_converter:
	        	intent  = new Intent(this, ConverterMenuActivity.class);
	    	    startActivity(intent);
	            return true;
	        case R.id.action_parcel:
	        	intent  = new Intent(this, ListParcelActivity.class);
	    	    startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
