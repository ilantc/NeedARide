package com.needaride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.needaride.R;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//This activity comes after filling the From, To and Date in the mapActivity and the user is the driver of this ride
//and exists in order to add more details of the ride as
//available sits
//ride fee to charge from the hikers
//additional comment
public class DriverAddRideDetailsActivity extends Activity {
	DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
	DecelerateInterpolator sOvershooter = new DecelerateInterpolator(10f);
	Boolean isSuggestSimilarIMGBTchecked = false;
	
	private String debugTag = "DriverAddRideDetailsActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_add_ride_details);
		
		final EditText fromET = (EditText)findViewById(R.id.Driver_FromET); 
		final EditText toET = (EditText)findViewById(R.id.Driver_ToET);
		final TextView dateTV = (TextView)findViewById(R.id.Driver_DateTV);
		//
		
		final ImageButton submitIMGBT = (ImageButton)findViewById(R.id.driverSubmitIMGBT);
		//Checks the from,to,date data was sent from the rideDetailsFragment
		//and put it into their fields in the current activity
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    fromET.setText(extras.getString("From"));
		    toET.setText(extras.getString("To"));
		    dateTV.setText(extras.getString("Date"));
		}
		
		//When pressing the switch button it swaps the FromET and ToET fields
        final ImageButton switchIMGBT = (ImageButton)findViewById(R.id.Driver_SwitchIMGBT);
        switchIMGBT.animate().setDuration(200);
        switchIMGBT.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					switchIMGBT.animate().setInterpolator(sDecelerator).scaleX(.7f).scaleY(.7f);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP){
					switchIMGBT.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f);
//					Log.e("RideDetailsFragment", "submit button was clicked");
					//swap fromTV and ToTv
					String temp = fromET.getText().toString();
					fromET.setText(toET.getText().toString());
					toET.setText((temp));
				}
				return false;
			}
		});
        //Done handling the switch button
		
        
        final ImageButton chooseDateIMGBT = (ImageButton)findViewById(R.id.Driver_ChooseDateIMGBT);
        chooseDateIMGBT.animate().setDuration(200);
        chooseDateIMGBT.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					chooseDateIMGBT.animate().setInterpolator(sDecelerator).scaleX(.7f).scaleY(.7f);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP){
					chooseDateIMGBT.animate().setInterpolator(sOvershooter).scaleX(1.5f).scaleY(1.5f);
					chooseDateIMGBT.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f);
//					Log.e("RideDetailsFragment", "chooseDateIMGBT button was clicked");
					(new TimeDialogGetter(v.getContext())).showDialog(dateTV);
				}
				return false;
			}
		});
        
        //Popup the dialog box to chose number
/*		final Button Driver_availableSitsBT = (Button)findViewById(R.id.Driver_availableSitsBT);	
 		Driver_availableSitsBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new NumberPickerDialogGetter(v.getContext(), Driver_availableSitsBT)).showDialog();
			}
		});
	*/	
		//Handling the number of available sits
		ImageButton availableSitsPlus1IMGBT = (ImageButton)findViewById(R.id.Driver_availableSitsPlus1IMGBT);
		ImageButton availableSitsMinus1IMGBT = (ImageButton)findViewById(R.id.Driver_availableSitsMinus1IMGBT);
		final EditText availableSitsET = (EditText)findViewById(R.id.Driver_availableSitsET);
		availableSitsET.setText("1");
		//Handling Plus1 button
		//increase the available sits - max 10 
		availableSitsPlus1IMGBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int numberofAvailableSits = Integer.parseInt(availableSitsET.getText().toString());
				//Log.d("Driver","the number is:"+numberofAvailableSits);
				//Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT).show();
				if (numberofAvailableSits < 10){
					numberofAvailableSits = Integer.parseInt(availableSitsET.getText().toString());
					availableSitsET.setText(Integer.toString(++numberofAvailableSits));
				}
			}
		});
		availableSitsMinus1IMGBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String st = availableSitsET.getText().toString();
				int numberofAvailableSits = Integer.parseInt(st);
				//Log.d("Driver","the number is:"+numberofAvailableSits);
				if (numberofAvailableSits > 0){
					numberofAvailableSits = Integer.parseInt(availableSitsET.getText().toString());
					availableSitsET.setText(Integer.toString(--numberofAvailableSits));
				}
			}
		});
		
		final ImageButton suggestSimilarIMGBT = (ImageButton)findViewById(R.id.Driver_SuggestSimilarIMGBT);
		suggestSimilarIMGBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isSuggestSimilarIMGBTchecked){
					suggestSimilarIMGBT.setImageResource(R.drawable.find_similar_rides_un_checked);
					isSuggestSimilarIMGBTchecked = false;
				}
				else{
					suggestSimilarIMGBT.setImageResource(R.drawable.find_similar_rides_checked);
					isSuggestSimilarIMGBTchecked = true;
				}
				
			}
		});
		
		
		//Handling the submitIMGBT
		//set the animation
		submitIMGBT.animate().setDuration(200);
		submitIMGBT.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//when touch the imageButton
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					submitIMGBT.animate().setInterpolator(sDecelerator).scaleX(.7f).scaleY(.7f);
				}
				//When taking the finger away from the imageButtom
				else if (event.getAction() == MotionEvent.ACTION_UP){
					submitIMGBT.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f);
					//Validate that the From To and Date fields were inserted
					Log.e("DriverAddRideDetailsActivity", "submitDriverIMGBT was ACTION_UP");
					if (fromET.getText().toString().equals("") ){
						//Toast.makeText(getApplicationContext(), "Where do you wonna go out from?", Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "מאיפה יוצאים?", Toast.LENGTH_LONG).show();
					}
					else if (toET.getText().toString().equals("")){
						//Toast.makeText(getApplicationContext(), "Where do you wonna go?", Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "לאיפה נוסעים?", Toast.LENGTH_LONG).show();
					}
					//if the fromAutoCompView and toAutoCompView fields are not empty
					else {
						//Insert the ride to the DB
						Log.e("DriverAddRideDetailsActivity", "Inserting to DB");
						try{
							
							ClientAsync ca = new ClientAsync();
							String userID = getMyPhoneNumber();
							//Toast.makeText(getApplicationContext(), "My Phone number:"+getMyPhoneNumber(), Toast.LENGTH_SHORT).show();
							String from = fromET.getText().toString();
							String to = toET.getText().toString();
							String date = dateTV.getText().toString();
							ca.execute("addnewride", userID,from,to,date);
							Toast.makeText(getApplicationContext(), "Ride inserted successfully", Toast.LENGTH_SHORT).show();
						}
						catch(Exception e){
							Log.e("DriverAddRideDetailsActivity","Could not insert to DB");
							Log.e("DriverAddRideDetailsActivity",e.toString());
						}
					}
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driver_add_ride_details, menu);
		return true;
	}
	private void setDate(TextView choosenDateTV){
		Log.e("RideDetailsFregment","set date");
	     // get date 
	        String formattedTime;
			String formattedDate;	
	        Calendar c = Calendar.getInstance();
	     	SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
	     	formattedDate = df.format(c.getTime());
	     // get time
	 		Time today = new Time(Time.getCurrentTimezone());
	 		today.setToNow();
	 		int currHour = today.hour;
	 		if (currHour == 24){
	 			currHour = 0;
	 		}
	 		formattedTime = currHour + ":" + today.minute;
	 		choosenDateTV.setText(formattedDate + " " + formattedTime);
	}

	//call the getMyPhoneNumberFromMyMobile() and getMyPhoneNumberFromWhatsApp() in order to get the phone number
	private String getMyPhoneNumber(){
		String phoneNumber = null;
		phoneNumber = getMyPhoneNumberFromMyMobile();
		if (phoneNumber.isEmpty()){
			phoneNumber = getMyPhoneNumberFromWhatsApp();
		}
		return phoneNumber;
	}
	//Try to get the phone number from the phone (not always succeed)
	private String getMyPhoneNumberFromMyMobile(){
		TelephonyManager mTelephonyMgr;
		getApplicationContext();
		mTelephonyMgr = (TelephonyManager)
				getSystemService(Context.TELEPHONY_SERVICE); 
		//Log.d(debugTag,"in getMyPhoneNumberFromMyMobile");
		return mTelephonyMgr.getLine1Number();
	}
	//Try to get the phone number from the WhatsApp account (works only if have whatsApp account on the phone)
	private String getMyPhoneNumberFromWhatsApp(){
		String phoneNumber = null;
		AccountManager am = AccountManager.get(getApplicationContext());
		Account[] accounts = am.getAccounts();
		for (Account ac : accounts) {
		    //String acname = ac.name;
		    String actype = ac.type;
		    // Take your time to look at all available accounts
		    //System.out.println("Accounts : " + acname + ", " + actype);
		    if(actype.contains("com.whatsapp")){
		        phoneNumber = ac.name;
		        //Toast.makeText(getApplicationContext(), "My Phone number:"+phoneNumber, Toast.LENGTH_SHORT).show();
		    }
		}
		//Log.d(debugTag,"in getMyPhoneNumberFromWhatsApp");
		return phoneNumber;
	}
}