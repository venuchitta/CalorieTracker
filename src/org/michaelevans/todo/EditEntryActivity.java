package org.michaelevans.todo;
import java.util.Calendar;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class EditEntryActivity extends Activity implements OnClickListener {
	private EditText textString,calorieString,dateString,timeString;
	Button btnCalendar, btnTimePicker;
	private int mYear, mMonth, mDay, mHour, mMinute;
	private Entry entry;
	private String objectid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_screen_edit);
		Intent intent = this.getIntent();
		textString = (EditText) findViewById(R.id.editText1);
		calorieString = (EditText) findViewById(R.id.editText2);
		dateString = (EditText) findViewById(R.id.EditText01);
		timeString = (EditText) findViewById(R.id.EditText02);
		btnCalendar = (Button) findViewById(R.id.button1);
		btnTimePicker = (Button) findViewById(R.id.button2);
		
		
		if (intent.getExtras() != null) {
			entry = new Entry(intent.getStringExtra("date"), intent.getStringExtra("time"),
					intent.getStringExtra("text"),intent.getDoubleExtra("calorieNo",0.0 ));
			dateString.setText(entry.getDate());
			timeString.setText(entry.getTime());
			textString.setText(entry.getText());
			calorieString.setText(String.valueOf(entry.getCalorieNo()));
			objectid=intent.getStringExtra("objectid");
			btnCalendar.setOnClickListener(this);
			btnTimePicker.setOnClickListener(this);
		}
	}

	public void entryButtonClicked(View v) {	
		if (textString.getText().length() > 0&&calorieString.getText().length()>0){
			ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);		
			query.whereEqualTo("owner", ParseUser.getCurrentUser());
			Intent intent1;
			query.getInBackground(objectid, new GetCallback<Entry>() {
				public void done(Entry post, ParseException e) {
					if (e == null) {
						post.setText(textString.getText().toString());
						post.setCalorieNo(Double.parseDouble(calorieString.getText().toString()));
						post.setDate(dateString.getText().toString());
						post.setTime(timeString.getText().toString());
						post.setOwner(ParseUser.getCurrentUser());
						//setProgressBarIndeterminateVisibility(true);
							try {
								post.save();
								TodoActivity.updateData();

							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}					
					}
				}
			});
 			intent1 = new Intent(this,TodoActivity.class);
			startActivity(intent1);
			finish();
		}
	}

	public void deleteButtonPressed(View v)
	{
		Entry.createWithoutData(Entry.class, objectid).deleteEventually();
	}

	public void onClick(View v) {
		Calendar c;
		if (v == btnCalendar) {
			c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					dateString.setText(String.format("%02d",dayOfMonth) + "-"
							+ String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%04d",year));

				}
			}, mYear, mMonth, mDay);
			dpd.show();
		}
		if (v == btnTimePicker) {

			// Process to get Current Time
			c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay,
						int minute) {
					timeString.setText(String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
				}
			}, mHour, mMinute, false);
			tpd.show();
		}
	}



}
