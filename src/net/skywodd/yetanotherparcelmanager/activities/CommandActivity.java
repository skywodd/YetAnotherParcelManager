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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class manage the CommandMenuActivity.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class CommandActivity extends AbstractBaseActivity {

	/** Extra key for the parcel density */
	public static final String EXTRA_PARCEL_DEN = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_DEN";

	/** Extra key for the parcel weight */
	public static final String EXTRA_PARCEL_WEIGHT = "net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity.EXTRA_PARCEL_WEIGHT";

	private EditText weight, surface, packaging;
	private TextView bags;

	private Context mContext;

	private Button control;

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

		setContentView(R.layout.activity_command);

		mContext = getApplicationContext();

		weight = (EditText) findViewById(R.id.tf_command_weight);
		surface = (EditText) findViewById(R.id.tf_command_surface);
		packaging = (EditText) findViewById(R.id.tf_command_packaging);

		if (getIntent().getExtras().getInt(EXTRA_PARCEL_WEIGHT) != 0) {
			weight.setText(Integer.toString(getIntent().getExtras().getInt(
					EXTRA_PARCEL_WEIGHT)));
		}

		bags = (TextView) findViewById(R.id.tv_command_bags);

		control = (Button) findViewById(R.id.btn_command_control);
		control.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent t = new Intent(mContext, ControlActivity.class);
				t.putExtra(ControlActivity.EXTRA_PARCEL_DEN, getIntent()
						.getExtras().getInt(EXTRA_PARCEL_DEN));
				startActivity(t);
			}
		});
		control.setVisibility(View.GONE);

		TextWatcher watcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				double weightVal, surfaceVal, packagingVal, bagsVal;
				if (!weight.getText().toString().equals("")
						&& !surface.getText().toString().equals("")
						&& !packaging.getText().toString().equals("")) {
					weightVal = Double.parseDouble(weight.getText().toString());
					surfaceVal = Double.parseDouble(surface.getText()
							.toString());
					packagingVal = Double.parseDouble(packaging.getText()
							.toString());
					bagsVal = (weightVal * surfaceVal) / packagingVal;
					bags.setText(Integer.toString((int) Math
							.floor(bagsVal + 1.0d)));

					control.setVisibility(View.VISIBLE);
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

		weight.addTextChangedListener(watcher);
		packaging.addTextChangedListener(watcher);
		surface.addTextChangedListener(watcher);
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
		menu.removeItem(R.id.action_command);

		// Menu created
		return true;
	}
}
