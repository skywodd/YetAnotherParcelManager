package net.skywodd.yetanotherparcelmanager.helpers;

import net.skywodd.yetanotherparcelmanager.R;
import net.skywodd.yetanotherparcelmanager.models.Parcel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ParcelAdapter extends ArrayAdapter<Parcel> {

	Context context;
	int layoutResourceId;
	Parcel data[] = null;

	public ParcelAdapter(Context context, int layoutResourceId, Parcel[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ParcelHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ParcelHolder();
			holder.txtName = (TextView) row.findViewById(R.id.tv_list_name);
			holder.txtCulture = (TextView) row
					.findViewById(R.id.tv_list_culture);
			holder.imgInfo = (ImageView) row.findViewById(R.id.img_list_infos);
			holder.imgParcel = (ImageView) row
					.findViewById(R.id.img_list_parcel);

			row.setTag(holder);
		} else {
			holder = (ParcelHolder) row.getTag();
		}

		Parcel Parcel = data[position];
		holder.txtName.setText(Parcel.getName());
		holder.txtCulture.setText(context.getResources().getStringArray(
				R.array.array_cultures_types)[Parcel.getGrowing().ordinal()]);
		holder.imgInfo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_next_item));
		holder.imgParcel.setImageURI(Parcel.getImage());
		return row;
	}

	static class ParcelHolder {
		ImageView imgParcel;
		TextView txtName;
		TextView txtCulture;
		ImageView imgInfo;
	}
}