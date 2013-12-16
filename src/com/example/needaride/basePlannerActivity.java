package com.example.needaride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public abstract class basePlannerActivity extends Activity {
	
	Button datePic;
	Button dateBT;
	
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
		FormattedTime = currHour + ":" + today.minute;
		
		setContentView(layoutResID);
		
		// take care of all the buttons and stuff from form_basic_plan
		dateBT = (Button) findViewById(R.id.chooseDateBT);
		dateBT.setText(FormattedDate + "\n" + FormattedTime);
		
		datePic = (Button) findViewById(R.id.chooseDatepicBT);
		
		dateBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "clikkk", Toast.LENGTH_LONG).show();
				(new TimeDialogGetter(v.getContext())).showDialog(dateBT);
				
			}
		});
		
		datePic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new TimeDialogGetter(v.getContext())).showDialog(dateBT);
				
			}
		});
	}
}
