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

import android.os.Bundle;
import net.skywodd.yetanotherparcelmanager.R;

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
	
	/*
	 * (non-Javadoc)
	 * @see net.skywodd.yetanotherparcelmanager.activities.AbstractBaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_info_parcel);
		
	}
}