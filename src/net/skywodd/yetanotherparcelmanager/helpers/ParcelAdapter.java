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
package net.skywodd.yetanotherparcelmanager.helpers;

import net.skywodd.yetanotherparcelmanager.R;
import net.skywodd.yetanotherparcelmanager.activities.InfoParcelActivity;
import net.skywodd.yetanotherparcelmanager.models.Parcel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Array adapter for the parcel list view.
 * 
 * @author Linkdelaudela
 * @version 1.0.0
 */
public class ParcelAdapter extends ArrayAdapter<Parcel> {

	/** Parent context */
	private Context context;

	/** Item layout resource ID */
	private int layoutResourceId;

	/** Array of display-able parcel. */
	private Parcel data[] = null;

	/**
	 * Create a new array adapter for Parcel list display.
	 * 
	 * @param context
	 *            Parent context.
	 * @param layoutResourceId
	 *            Item layout resource ID.
	 * @param data
	 *            Array of parcel to be displayed.
	 */
	public ParcelAdapter(Context context, int layoutResourceId, Parcel[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Container for the parcel data
		ParcelHolder holder = null;

		/* Create a new view or recycle an old one. */
		if (convertView == null) {

			// Inflate a new list item
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);

			// Handle click (show details) 
			convertView.setClickable(true);
			convertView.setLongClickable(true); // For the context menu
			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
					// Get the associated holder
					ParcelHolder ph = (ParcelHolder) v.getTag();
					
					// Craft the "show details" intent
					Intent t = new Intent(context, InfoParcelActivity.class);
					t.putExtra(InfoParcelActivity.EXTRA_PARCEL_ID, ph.targetID);
					
					// Broadcast the intent
					context.startActivity(t);
				}
			});

			// Create and populate a new container
			holder = new ParcelHolder();
			holder.txtName = (TextView) convertView
					.findViewById(R.id.tv_list_name);
			holder.txtCulture = (TextView) convertView
					.findViewById(R.id.tv_list_culture);
			holder.imgParcel = (ImageView) convertView
					.findViewById(R.id.img_list_parcel);

			// Add the "more informations" button
			ImageView imgInfo = (ImageView) convertView
					.findViewById(R.id.img_list_infos);
			imgInfo.setImageDrawable(context.getResources().getDrawable(
					R.drawable.ic_action_next_item));

			// Bind the container as view tag
			convertView.setTag(holder);
		} else {

			// Recycle the old view
			holder = (ParcelHolder) convertView.getTag();
		}

		// Populate the container fields
		Parcel p = data[position];
		holder.targetID = p.getId();
		holder.txtName.setText(p.getName());
		holder.txtCulture.setText(context.getResources().getStringArray(
				R.array.array_cultures_types)[p.getGrowing().ordinal()]);
		if (p.getImage() != null)
			holder.imgParcel.setImageURI(p.getImage());
		else
			// Default image
			holder.imgParcel.setImageDrawable(context.getResources()
					.getDrawable(R.drawable.ic_action_picture));

		// Return the builded view
		return convertView;
	}

	/**
	 * Container for an list item displaying Parcel data.
	 * 
	 * @author Linkdelaudela
	 * @version 1.0.0
	 */
	static public class ParcelHolder {

		/** Target parcel ID. */
		public long targetID;

		/** Parcel name TextView. */
		public TextView txtName;

		/** Current culture name TextView. */
		public TextView txtCulture;

		/** Parcel picture ImageView */
		public ImageView imgParcel;
	}
}