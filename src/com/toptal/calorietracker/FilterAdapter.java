package com.toptal.calorietracker;
import java.text.SimpleDateFormat;
import java.util.List;

import com.toptal.calorietracker.R;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FilterAdapter extends ArrayAdapter<Entry> {
	private Context mContext;
	private List<Entry> mTasks;
	private TextView textView1,textView2,textView3;
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	public FilterAdapter(Context context, List<Entry> objects) {
		super(context, R.layout.task_row_item, objects);
		this.mContext = context;
		this.mTasks = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
			convertView = mLayoutInflater.inflate(R.layout.task_row_item, null);
		}
		Entry entry = mTasks.get(position);
		textView1 = (TextView) convertView.findViewById(R.id.textView1);
		textView2 = (TextView) convertView.findViewById(R.id.textView2);
		textView3 = (TextView) convertView.findViewById(R.id.textView3);
		Entry entry1 = mTasks.get(position);
		textView1.setText(entry1.getText());
		textView2.setText(String.valueOf(entry1.getCalorieNo()));
		textView3.setText(df.format(entry1.getDate()));
		return convertView;
	}

}

