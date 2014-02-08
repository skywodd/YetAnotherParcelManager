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
import net.skywodd.yetanotherparcelmanager.helpers.DatabaseHelper;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Base class for all activities with ORM dependency and option menu.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class AbstractBaseActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Inflate the default menu.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);

		// Menu created.
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
		case R.id.action_command: // Command action
			startActivity(new Intent(this, CommandActivity.class));
			return true;

		case R.id.action_control: // Control action
			startActivity(new Intent(this, ControlActivity.class));
			return true;

		case R.id.action_converter: // Converter action
			startActivity(new Intent(this, ConverterActivity.class));
			return true;

		case R.id.action_parcel: // Parcel action
			startActivity(new Intent(this, ListParcelActivity.class));
			return true;

		default: // Unknown action
			return super.onOptionsItemSelected(item);
		}
	}
}
