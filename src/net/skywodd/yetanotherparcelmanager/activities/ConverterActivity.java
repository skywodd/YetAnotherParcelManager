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
 * This class manage converterMenuActivity.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class ConverterActivity extends AbstractBaseActivity {

	private EditText density, pmg;
	private TextView weight;

	private Button com;

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

		setContentView(R.layout.activity_converter);

		density = (EditText) findViewById(R.id.tf_converter_density);
		pmg = (EditText) findViewById(R.id.tf_converter_pmg);

		weight = (TextView) findViewById(R.id.tv_converter_weight);

		mContext = this.getApplicationContext();

		com = (Button) findViewById(R.id.btn_converter_command);
		com.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent t = new Intent(mContext, CommandActivity.class);
				t.putExtra(CommandActivity.EXTRA_PARCEL_DEN,
						(int) Math.floor(Double.parseDouble(density.getText()
								.toString())));
				t.putExtra(CommandActivity.EXTRA_PARCEL_WEIGHT,
						(int) Math.floor(Double.parseDouble(weight.getText()
								.toString()) + 0.5d));
				startActivity(t);
			}
		});
		com.setVisibility(View.GONE);

		TextWatcher watcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				Double densityVal, pmgVal, weightVal;
				if (!pmg.getText().toString().equals("")
						&& !density.getText().toString().equals("")) {
					densityVal = Double.parseDouble(density.getText()
							.toString());
					pmgVal = Double.parseDouble(pmg.getText().toString());
					weightVal = densityVal * (pmgVal / 100);
					weight.setText(Integer.toString((int) Math
							.floor(weightVal + 0.5d)));
					com.setVisibility(View.VISIBLE);

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
		pmg.addTextChangedListener(watcher);

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
		menu.removeItem(R.id.action_converter);

		// Menu created
		return true;
	}
}
