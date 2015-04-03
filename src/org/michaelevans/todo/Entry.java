package org.michaelevans.todo;
import java.util.*;

import com.parse.*;

@ParseClassName("Entry")
public class Entry extends ParseObject{

	public Entry()
	{
		super();
	}

	public Entry(String date, String time,String text, double calorieNo) {
		super();
		setDate(date);
		setTime(time);
		setText(text);
		setCalorieNo(calorieNo);
	}

	public String getDate() {
		return getString("date");
	}
	
	public void setDate(String date) {
		put("date",date);
	}
	public String getTime() {
		return getString("time");
	}
	public void setTime(String time) {
		put("time",time);
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
