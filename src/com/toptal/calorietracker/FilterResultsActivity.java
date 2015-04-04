package com.toptal.calorietracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.toptal.calorietracker.R;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseQuery.CachePolicy;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;

public class FilterResultsActivity extends Activity implements OnClickListener{ 
	private EditText btnCalendar1, btnTimePicker1,btnCalendar2, btnTimePicker2;
	Button calender1,calender2,time1,time2;
	 private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	 private SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
	 private SimpleDateFormat com = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	private int mYear1, mMonth1, mDay1, mHour1, mMinute1,mYear2, mMonth2, mDay2, mHour2, mMinute2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_screen_filter);
		btnCalendar1 = (EditText) findViewById(R.id.EditText01);
		btnTimePicker1 = (EditText) findViewById(R.id.EditText02);
		btnCalendar2 = (EditText) findViewById(R.id.EditText03);
		btnTimePicker2 = (EditText) findViewById(R.id.EditText04);
		calender1 = (Button) findViewById(R.id.button1);
		time1 = (Button) findViewById(R.id.button2);
		calender2 = (Button) findViewById(R.id.Button01);
		time2 = (Button) findViewById(R.id.Button02);
		calender1.setOnClickListener(this);
		calender2.setOnClickListener(this);
		time1.setOnClickListener(this);
		time2.setOnClickListener(this);
	}

@Override
public void onClick(View v) {
	Calendar c;
	if (v == calender1) {
		c = Calendar.getInstance();
		mYear1 = c.get(Calendar.YEAR);
		mMonth1 = c.get(Calendar.MONTH);
		mDay1= c.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				btnCalendar1.setText(String.format("%02d",dayOfMonth) + "-"
						+ String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%04d",year));
			}
		}, mYear1, mMonth1, mDay1);
		dpd.show();
	}
	if (v == time1) {

		// Process to get Current Time
		c = Calendar.getInstance();
		mHour1 = c.get(Calendar.HOUR_OF_DAY);
		mMinute1 = c.get(Calendar.MINUTE);
		TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay,
					int minute) {
				btnTimePicker1.setText(String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
			}
		}, mHour1, mMinute1, false);
		tpd.show();
	}
	if (v == calender2) {
		c = Calendar.getInstance();
		mYear2 = c.get(Calendar.YEAR);
		mMonth2 = c.get(Calendar.MONTH);
		mDay2= c.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				btnCalendar2.setText(String.format("%02d",dayOfMonth) + "-"
						+ String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%04d",year));
			}
		}, mYear2, mMonth2, mDay2);
		dpd.show();
	}
	if (v == time2) {
		c = Calendar.getInstance();
		mHour2 = c.get(Calendar.HOUR_OF_DAY);
		mMinute2 = c.get(Calendar.MINUTE);
		TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay,
					int minute) {
				btnTimePicker2.setText(String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
			}
		}, mHour2, mMinute2, false);
		tpd.show();
	}
}

public void filterResults(View v) throws java.text.ParseException
{
	Intent intent = new Intent(this, FilterResultsDisplayActivity.class);
	intent.putExtra("calender1", com.parse(btnCalendar1.getText().toString()+" "+btnTimePicker1.getText().toString()));
	intent.putExtra("calender2",df.parse(btnCalendar2.getText().toString()+" "+btnTimePicker1.getText().toString()));
	startActivity(intent);
}

}