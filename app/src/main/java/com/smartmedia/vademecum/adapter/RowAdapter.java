package com.smartmedia.vademecum.adapter;

import com.smartmedia.vademecum.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RowAdapter extends ArrayAdapter<String> {
	private String[] text2;
	private Context context;
	private int rowLayoutId;
	private int[] colors;

	public RowAdapter(Context context, int resource, String[] list,
			String[] text2) {
		super(context, resource, R.id.row_text1, list);

		this.text2 = text2;
		this.context = context;
		rowLayoutId = resource;
	}

	public RowAdapter(Context context, int resource, String[] list,
			String[] text2, int[] colors) {
		super(context, resource, R.id.row_text1, list);

		this.text2 = text2;
		this.context = context;
		rowLayoutId = resource;
		this.colors = colors;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RowViewHolder holder;

		if (row == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(rowLayoutId, parent, false);

			holder = new RowViewHolder();
			holder.second = (TextView) row.findViewById(R.id.row_text2);

            //TODO posicion original del codigo

			row.setTag(holder);
		} else {
			holder = (RowViewHolder) row.getTag();
		}

        //TODO posicion temporal del codigo
        if (text2.length == 1 && colors.length > 0) {
            holder.second.setText(text2[0]);
            holder.second.setTextColor(getContext().getResources().getColor(colors[position]));
        } else
            holder.second.setText(text2[position]);

		return super.getView(position, row, parent);
	}

	static class RowViewHolder {
		TextView second;
	}
}
