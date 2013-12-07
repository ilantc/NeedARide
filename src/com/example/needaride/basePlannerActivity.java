package com.example.needaride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class basePlannerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	protected void setContentViewWithDate(int layoutResID) {
		String FormattedTime;
		String FormattedDate;

		// get date 
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
		FormattedDate = df.format(c.getTime());
		
		// get time
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int currHour = today.hour + 1 ;
		FormattedTime = currHour + ":" + today.format("%M:%S");
		
		setContentView(layoutResID);
		
		// take care of all the buttons and stuff from form_basic_plan
		TextView tv = (TextView) findViewById(R.id.chooseDateBT);
		tv.setText(FormattedDate + "\n" + FormattedTime);
	}
}
