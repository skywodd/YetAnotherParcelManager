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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class manage ControlMenuActivity.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class ControlActivity extends AbstractBaseActivity {

	/** Extra key for the parcel density */
	public static final String EXTRA_PARCEL_DEN = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_DEN";

	private EditText density, spread;
	private TextView rankNumber, grainsNumber;

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

		setContentView(R.layout.activity_control);

		density = (EditText) findViewById(R.id.tf_control_density);
		spread = (EditText) findViewById(R.id.tf_control_gap);

		rankNumber = (TextView) findViewById(R.id.tv_control_rank);
		grainsNumber = (TextView) findViewById(R.id.tv_control_grains);

		if (getIntent().getExtras().getInt(EXTRA_PARCEL_DEN) != 0) {
			density.setText(Integer.toString(getIntent().getExtras().getInt(
					EXTRA_PARCEL_DEN)));
		}

		TextWatcher watcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				Double densityVal, spreadVal, rankVal;
				if (!spread.getText().toString().equals("")
						&& !density.getText().toString().equals("")) {
					densityVal = Double.parseDouble(density.getText()
							.toString());
					spreadVal = Double.parseDouble(spread.getText().toString());
					rankVal = densityVal / spreadVal;
					rankNumber.setText(Integer.toString((int) Math
							.floor(rankVal + 0.5d)));
					grainsNumber.setText(Integer.toString((int) Math
							.floor((densityVal / rankVal) + 0.5d)));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		};

		density.addTextChangedListener(watcher);
		spread.addTextChangedListener(watcher);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Remove itself from the default menu
		menu.removeItem(R.id.action_control);

		// Menu created
		return true;
	}
}
