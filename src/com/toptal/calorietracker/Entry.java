package com.toptal.calorietracker;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.parse.*;

@ParseClassName("Entry")
public class Entry extends ParseObject{
	public Entry()
	{
		super();
	}

	public Entry(Date date,String text, double calorieNo) throws ParseException {
		super();
		setDate(date);
		setText(text);
		setCalorieNo(calorieNo);
	}

	public Date getDate() {
		return (Date) this.get("date");
	}
	
	public void setDate(Date date) throws ParseException {
		put("date",date);
	}
	
	public String getText() {
		return getString("text");
	}
	public void setText(String text) {
		put("text",text);
	}
	public double getCalorieNo() {
		return getDouble("calorieNo");
	}
	public void setCalorieNo(double calorieNo) {
		put("calorieNo",calorieNo);
	}
	public ParseUser getUser()  {
		return getParseUser("owner");
	}

	public void setOwner(ParseUser user) {
		put("owner", user);
	}

}
