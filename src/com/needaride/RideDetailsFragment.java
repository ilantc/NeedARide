package com.needaride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.needaride.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RideDetailsFragment extends Fragment {
	
	ToggleButton imHikerTB;
	ToggleButton imDriverTB;
	
	static AutoCompleteTextView fromAutoCompView;
	static AutoCompleteTextView toAutoCompView;
	DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
	DecelerateInterpolator sOvershooter = new DecelerateInterpolator(10f);
	TextView choosenDateTV;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_ride_details_form,null);
		
		//Handling the kind of ride - Im the Driver or Hiker
		//The user cannot set both toggles to off but can switch between them
		final ToggleButton imHikerTB = (ToggleButton)v.findViewById(R.id.imHikerTB);
		final ToggleButton imDriverTB = (ToggleButton)v.findViewById(R.id.imDriverTB);
		//the default is that the user is a hiker
		imHikerTB.setChecked(true);
		imDriverTB.setChecked(false);
		//When press on the imDriverTB set the imHikerTB to off
		//cannot turn off the toggle (can only choose the imHikerTB) 
		imDriverTB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (imHikerTB.isChecked()){
					imHikerTB.setChecked(false);			
				}
				else{
					imDriverTB.setChecked(true);
				}
			}
		});
		//When press on the imHikerTB set the imDriver to off
		//cannot turn off the toggle (can only choose the imDriverTB) 
		imHikerTB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (imDriverTB.isChecked()){
					imDriverTB.setChecked(false);			
				}
				else{
					imHikerTB.setChecked(true);
				}
			}
		});
		//Done handling with the choose ride toggles
		
		choosenDateTV = (TextView)v.findViewById(R.id.choosenDateTV);
		choosenDateTV.requestFocus();
		setDate(choosenDateTV);
		
		final ImageView checkingForSimilarRidesFadeInIV = (ImageView) v.findViewById(R.id.checkingForSimilarRidesFadeInIV);
		final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
		
		fromAutoCompView = (AutoCompleteTextView) v.findViewById(R.id.fromET);
        fromAutoCompView.setAdapter(new PlacesAutoCompleteAdapter(v.getContext(), R.layout.list_item));
        fromAutoCompView.addTextChangedListener(new autoCompManager(fromAutoCompView));
        fromAutoCompView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		        String str = (String) adapterView.getItemAtPosition(position);
		        
		        // remove old marker from map if it exists
		        LocationManager.getinstance(view.getContext()).setFromLat(null);
		        
		        // set new string and marker
		        LocationManager.getinstance(view.getContext()).setFromStr(str);
		        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
		    }
		});
        
//        fromAutoCompView.setOnFocusChangeListener(new OnFocusChangeListener() {	
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (false == hasFocus) {
//					LocationManager.getinstance(v.getContext()).setFromStr(((AutoCompleteTextView) v).getText().toString());
//				}
//			}
//		});
        
//        fromAutoCompView.onk
        
        
        toAutoCompView = (AutoCompleteTextView) v.findViewById(R.id.toET);
        toAutoCompView.setAdapter(new PlacesAutoCompleteAdapter(v.getContext(), R.layout.list_item));
        toAutoCompView.addTextChangedListener(new autoCompManager(toAutoCompView));
        toAutoCompView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		        String str = (String) adapterView.getItemAtPosition(position);
		        
		        // remove old marker from map if it exists
		        LocationManager.getinstance(view.getContext()).setToLat(null);
		        
		        // set new string and marker
		        LocationManager.getinstance(view.getContext()).setToStr(str);
		        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
		        //LatLng point = new LatLng(latitude, longitude)
		        //MapActivity.setToPinOnMap(point);
		    }
		});
//       toAutoCompView.setOnFocusChangeListener(new OnFocusChangeListener() {	
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (false == hasFocus) {
//					LocationManager.getinstance(v.getContext()).setToStr(((AutoCompleteTextView) v).getText().toString());
//				}
//			}
//		});
//        
        
        final ImageButton chooseDateIMGBT = (ImageButton)v.findViewById(R.id.chooseDateIMGBT);
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
					(new TimeDialogGetter(v.getContext())).showDialog(choosenDateTV);
				}
				return false;
			}
		});
        
        final ImageButton submitIMGBT = (ImageButton)v.findViewById(R.id.submitIMGBT);
        submitIMGBT.animate().setDuration(200);
        submitIMGBT.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					submitIMGBT.animate().setInterpolator(sDecelerator).scaleX(.7f).scaleY(.7f);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP){
					submitIMGBT.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f);
					//Validate that the From To and Date fields were inserted
					if (fromAutoCompView.getText().toString().equals("") ){
						//Toast.makeText(getActivity(), "Where do you wonna go out from?", Toast.LENGTH_LONG).show();
						Toast.makeText(getActivity(), "מאיפה יוצאים?", Toast.LENGTH_LONG).show();
					}
					else if (toAutoCompView.getText().toString().equals("")){
						//Toast.makeText(getActivity(), "Where do you wonna go?", Toast.LENGTH_LONG).show();
						Toast.makeText(getActivity(), "לאיפה נוסעים?", Toast.LENGTH_LONG).show();
					}
					//if the fromAutoCompView and toAutoCompView fields are not empty
					else {
						//If the user chose to be the hitchHiker - look for relevant rides
						if (imHikerTB.isChecked()){
							checkingForSimilarRidesFadeInIV.startAnimation(animationFadeIn);
							Intent intent = new Intent();
							intent.setClassName(getActivity(),"com.needaride.TakeARideActivity");
							startActivity(intent);
						}
						//If the user chose to be the Driver - send to DriverAddRideDetailsActivity
						else{
							Intent intent = new Intent();
							intent.setClassName(getActivity(),"com.needaride.DriverAddRideDetailsActivity");
							intent.putExtra("From", fromAutoCompView.getText().toString());
							intent.putExtra("To", toAutoCompView.getText().toString());
							intent.putExtra("Date", choosenDateTV.getText().toString());
							startActivity(intent);
						}
					}
				}
				return false;
			}
		});
        
		//When pressing the switch button it swaps the From and To fields
        final ImageButton switchIMGBT = (ImageButton)v.findViewById(R.id.switchIMGBT);
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
					String temp = getAddressFromFromTV();
					setTextInFromAutoCompView(getAddressFromToTV());
					setTextInToAutoCompView(temp);
				}
				return false;
			}
		});
        return v;
	}
	//Done handling the swap button
	
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
	
	
	private class autoCompManager implements TextWatcher {
		
		private CountDownTimer autoCompCDT;
		private CharSequence text;
		private long lastUpdateTime;
		private long timeForUpdateTextWhileTyping;
		
		public autoCompManager(final AutoCompleteTextView autoCompView) {
			int autoCompTimeout = getResources().getInteger(R.integer.secsTillAutoComplete);
			lastUpdateTime = System.currentTimeMillis();
			
			// count for auto completes during a typing "session" (to help him while he is typing long inputs)
			timeForUpdateTextWhileTyping = (long) 1000 * getResources().getInteger(R.integer.secsForUpdateTextWhileTyping);
			
			autoCompCDT = new CountDownTimer((long) autoCompTimeout * 1000, (long) autoCompTimeout * 1000) {

				@Override
				public void onTick(long millisUntilFinished) {}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					Filter f = ((PlacesAutoCompleteAdapter) autoCompView.getAdapter()).getFilter();
		        	Log.e("autoComp","sending req, s is: " + text);
		        	f.filter(text);
		        	lastUpdateTime = System.currentTimeMillis(); // remember last update
				}
			};			
		}
		
		@Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			text = s;
			
			// cancel pending request if last update was done recently 
			if (System.currentTimeMillis() - lastUpdateTime < timeForUpdateTextWhileTyping) {
				autoCompCDT.cancel();
			}
			// start new timer
			autoCompCDT.start();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
	}
	
	//set text in the TV
	public static void setTextInFromAutoCompView(String address){
		fromAutoCompView.setText(address);
	}
	public static void setTextInToAutoCompView(String address){
		toAutoCompView.setText(address);
	}
	public static String getAddressFromToTV(){
		return toAutoCompView.getText().toString();
	}
	public static String getAddressFromFromTV(){
		return fromAutoCompView.getText().toString();
	}
}