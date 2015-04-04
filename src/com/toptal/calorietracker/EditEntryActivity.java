package com.toptal.calorietracker;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.toptal.calorietracker.R;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;

public class EditEntryActivity extends Activity implements OnClickListener {
	private EditText textString,calorieString,dateString,timeString;
	Button btnCalendar, btnTimePicker;
	private int mYear, mMonth, mDay, mHour, mMinute;
	private Entry entry;
	private String objectid;
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
	Date date,time;

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
			try {
				entry = new Entry((Date)intent.getSerializableExtra("date"), 
						intent.getStringExtra("text"),intent.getDoubleExtra("calorieNo",0.0 ));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dateString.setText(df.format(entry.getDate()));
			timeString.setText(df1.format(entry.getDate()));
			textString.setText(entry.getText());
			calorieString.setText(String.valueOf(entry.getCalorieNo()));
			objectid=intent.getStringExtra("objectid");
			btnCalendar.setOnClickListener(this);
			btnTimePicker.setOnClickListener(this);
			date=entry.getDate();
			time = entry.getDate();
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
						try {
							Date temp1 = df.parse(dateString.getText().toString());
							Date temp = df1.parse(timeString.getText().toString());
							temp1.setHours(temp.getHours());
							temp1.setMinutes(temp.getMinutes());
							post.setDate(temp1);

						} catch (java.text.ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
			
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
		Log.e("ObjectId",objectid.toString());
		Entry.createWithoutData(Entry.class, objectid).deleteEventually();
		Intent intent1 = new Intent(this,TodoActivity.class);
		TodoActivity.updateData();
		startActivity(intent1);
		finish();
	}

	public void onClick(View v) {
		Calendar c;
		if (v == btnCalendar) {
			c = Calendar.getInstance();
			c.setTime(date);
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
					try {
						date=df.parse(dateString.getText().toString());
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}, mYear, mMonth, mDay);
			dpd.show();
		}
		if (v == btnTimePicker) {

			// Process to get Current Time
			c = Calendar.getInstance();
			c.setTime(time);
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay,
						int minute) {
					timeString.setText(String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
					try {
						time=df.parse(timeString.getText().toString());
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, mHour, mMinute, false);
			tpd.show();
		}
	}




}
