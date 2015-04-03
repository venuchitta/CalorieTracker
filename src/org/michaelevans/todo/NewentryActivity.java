package org.michaelevans.todo;

import java.text.SimpleDateFormat;
import java.util.*;

import android.app.Activity;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseQuery.CachePolicy;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;


public class NewentryActivity  extends Activity implements OnClickListener {

	private EditText textString,calorieString,dateString,timeString;
	Button btnCalendar, btnTimePicker;
	private int mYear, mMonth, mDay, mHour, mMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_screen_edit);
		textString = (EditText) findViewById(R.id.editText1);
		calorieString = (EditText) findViewById(R.id.editText2);
		dateString = (EditText) findViewById(R.id.EditText01);
		timeString = (EditText) findViewById(R.id.EditText02);
		btnCalendar = (Button) findViewById(R.id.button1);
		btnTimePicker = (Button) findViewById(R.id.button2);
		btnCalendar.setOnClickListener(this);
		btnTimePicker.setOnClickListener(this);
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
		Date today = Calendar.getInstance().getTime();        
		String date = df.format(today);
		String time = df1.format(today);
		dateString.setText(date);
		timeString.setText(time);
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


	// TODO: Add toast
	public void entryButtonClicked(View v) {	
		if (textString.getText().length() > 0&&calorieString.getText().length()>0){
			Entry entry = new Entry();
			entry.setDate(dateString.getText().toString());
			entry.setTime(timeString.getText().toString());
			entry.setCalorieNo(Double.parseDouble(calorieString.getText().toString()));
			entry.setText(textString.getText().toString());
			entry.setOwner(ParseUser.getCurrentUser());
			try {
				entry.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TodoActivity.updateData();

			Intent intent1 = new Intent(this,TodoActivity.class);
			startActivity(intent1);
			finish();
		}
	}

}



