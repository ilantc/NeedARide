package com.example.needaride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class TimeDialogGetter  {

	Context context;
	TextView dateBT;
	TimePicker tp;
	DatePicker dp;
	
	public TimeDialogGetter(Context c) {
		context = c;
		//Toast.makeText(context, "initialized time dialog getter", Toast.LENGTH_LONG).show();
	}
	
	
	public void showDialog(TextView dateTV) {
		
		//Toast.makeText(context, "showing dialog", Toast.LENGTH_LONG).show();
		dateBT = dateTV;
		// custom dialog
		final Dialog dialog = new Dialog(context,R.style.Theme_Dialog);
		dialog.setContentView(R.layout.dialog_choose_time_and_date);
		dialog.setTitle("choose time and date");
		
		// set the custom dialog buttons
		tp 	= (TimePicker) dialog.findViewById(R.id.timePicker);
		dp 	= (DatePicker) dialog.findViewById(R.id.datePicker);
		Button 	   ok 	= (Button)     dialog.findViewById(R.id.OkButton);
		// if button is clicked, close the custom dialog
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO - launch relevant activity
				String time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
				Calendar c = Calendar.getInstance();
				int   day  = dp.getDayOfMonth();
				int   month= dp.getMonth();
				int   year = dp.getYear();
				c.set(year, month, day);
//				Toast.makeText(v.getContext(), "year is " + year, Toast.LENGTH_LONG).show();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				
				String FormatedDate = df.format(c.getTime());
				dateBT.setText(FormatedDate + "\n" + time);
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
}
