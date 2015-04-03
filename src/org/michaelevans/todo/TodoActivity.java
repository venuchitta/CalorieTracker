package org.michaelevans.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseQuery.CachePolicy;
import org.michaelevans.*;
import org.michaelevans.todo.R;;

public class TodoActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private static TaskAdapter mAdapter;
	public String AppId="6DqKcrZXWU7HmEsbEo8XlmNiMgnTocwkqz2CRQtX";
	public String ClientKey="TTsd2t6auzKL5lBrCfhEdTkjWeyrcajUwL0pFuES";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setProgressBarIndeterminateVisibility(true);
		setContentView(R.layout.entry_screen_main);
		Parse.initialize(this, AppId, ClientKey);
		ParseAnalytics.trackAppOpened(getIntent());
		ParseObject.registerSubclass(Entry.class);

		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		mAdapter = new TaskAdapter(this, new ArrayList<Entry>());
		mListView = (ListView)  findViewById(R.id.listView1);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		updateData();
	}
	
	protected void onResume (Bundle savedInstanceState) {
		super.onResume();
		updateData();	
	}
	

	public static void updateData(){
		ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
		query.whereEqualTo("owner", ParseUser.getCurrentUser());
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.addDescendingOrder("date");
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout: 
			ParseUser.logOut();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true; 
		case R.id.action_new: 
			Intent intent1 = new Intent(this, NewentryActivity.class);
			startActivity(intent1);
			return true;
		case R.id.action_refresh: 
			TodoActivity.updateData();
			return true;
		case R.id.action_filter: 
			Intent intent2 = new Intent(this, FilterResults.class);
			startActivity(intent2);
			return true;
		
		} 
		return false; 
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Entry entry = mAdapter.getItem(position);
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra("date", entry.getDate());
		intent.putExtra("time", entry.getTime());
		intent.putExtra("text", entry.getText());
		intent.putExtra("objectid", entry.getObjectId());
		intent.putExtra("calorieNo", entry.getCalorieNo());
		startActivity(intent);

	}

}
