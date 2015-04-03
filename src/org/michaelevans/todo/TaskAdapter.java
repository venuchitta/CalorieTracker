package org.michaelevans.todo;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Entry> {
	private Context mContext;
	private List<Entry> mTasks;
	
	public TaskAdapter(Context context, List<Entry> objects) {
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
		TextView descriptionView = (TextView) convertView.findViewById(R.id.task_description);
		descriptionView.setText(entry.getDate()+"         "+entry.getCalorieNo());
		return convertView;
	}

}
