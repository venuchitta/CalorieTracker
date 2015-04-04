package com.toptal.calorietracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.toptal.calorietracker.R;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseQuery.CachePolicy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FilterResultsDisplayActivity  extends Activity implements OnItemClickListener {
	private TaskAdapter mAdapter;
	private ListView mListView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acticity_filter_list);
		mAdapter = new TaskAdapter(this, new ArrayList<Entry>());
		mListView = (ListView)  findViewById(R.id.listView01);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		loadData();
	}
	
	protected void loadData()
	{
		ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
		query.whereEqualTo("owner", ParseUser.getCurrentUser());
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		Date temp;
		Intent intent = this.getIntent();
		Date cal1 = (Date)intent.getSerializableExtra("calender1");
		Date cal2 = (Date)intent.getSerializableExtra("calender2");
		query.whereGreaterThanOrEqualTo("date", cal1);
		query.whereLessThanOrEqualTo("date", cal2);
		//query.whereLessThanOrEqualTo("date",df.parse(btnCalendar2.getText().toString()));
		query.findInBackground(new FindCallback<Entry>() {
			@Override
			public void done(List<Entry> entries, ParseException error) {
				if(entries != null){
					mAdapter.clear();
					for (int i = 0; i < entries.size(); i++) 
						mAdapter.add(entries.get(i));
				}
				else 
				{
					Log.d("item", "Error: " + error.getMessage());
				}
			}
		});

	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Entry entry = mAdapter.getItem(position);
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra("date", entry.getDate());
		intent.putExtra("text", entry.getText());
		intent.putExtra("objectid", entry.getObjectId());
		intent.putExtra("calorieNo", entry.getCalorieNo());
		startActivity(intent);

		
	}

}
