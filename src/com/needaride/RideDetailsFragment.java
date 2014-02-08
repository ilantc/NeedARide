package com.needaride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.needaride.R;
import com.needaride.LocationManager.locationValues;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RideDetailsFragment extends Fragment {
	
	// auto-complete views
	static AutoCompleteTextViewWithDelay fromAutoCompView;
	static AutoCompleteTextViewWithDelay toAutoCompView;
	
	DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
	DecelerateInterpolator sOvershooter = new DecelerateInterpolator(10f);
	TextView choosenDateTV;
	
	//the default is that the user is a hiker
	Boolean isDriver = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_ride_details_form,null);
		
		//Handling the kind of ride - if Driver or Hiker
		//The user can switch between driver or hitchhiker
		final ImageButton driverOrHitchhikerIMGBT = (ImageButton)v.findViewById(R.id.driverOrHitchhikerIMGBT);
		
		//When press on the driverOrHitchhikerIMGBT set the imHikerTB to off
		//cannot turn off the toggle (can only choose the imHikerTB) 
		driverOrHitchhikerIMGBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isDriver){
					driverOrHitchhikerIMGBT.setImageResource(R.drawable.driver_off_hitchhiker_on);
					isDriver = false;			
				}
				else{
					driverOrHitchhikerIMGBT.setImageResource(R.drawable.driver_on_hitchhiker_off);
					isDriver = true;
				}
			}
		});
		//Done handling with the choose ride "toggles"
		
		choosenDateTV = (TextView)v.findViewById(R.id.choosenDateTV);
		choosenDateTV.requestFocus();
		setDate(choosenDateTV);
		
		final ImageView checkingForSimilarRidesFadeInIV = (ImageView) v.findViewById(R.id.checkingForSimilarRidesFadeInIV);
		final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
		
		fromAutoCompView = (AutoCompleteTextViewWithDelay) v.findViewById(R.id.fromET);
        fromAutoCompView.setAdapter(new PlacesAutoCompleteAdapter(v.getContext(), R.layout.list_item));
        fromAutoCompView.setOnClickListener(new OnClickListener() {
			
        	// on click - allow auto complete
			@Override
			public void onClick(View v) {
				((AutoCompleteTextViewWithDelay) v).setAutoComplete(true);
			}
		});
        fromAutoCompView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		        String str = (String) adapterView.getItemAtPosition(position);
		        
		        // set new string and marker
		        LocationManager.getinstance(view.getContext()).setStr(str,locationValues.from);
		        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
		    }
		});        
        
        toAutoCompView = (AutoCompleteTextViewWithDelay) v.findViewById(R.id.toET);
        toAutoCompView.setAdapter(new PlacesAutoCompleteAdapter(v.getContext(), R.layout.list_item));
        toAutoCompView.setOnClickListener(new OnClickListener() {
			
        	// on click - allow auto complete
			@Override
			public void onClick(View v) {
				((AutoCompleteTextViewWithDelay) v).setAutoComplete(true);
			}
		});
        
        // this is the listener for the filter list
        toAutoCompView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		        String str = (String) adapterView.getItemAtPosition(position);
		        
		        // set new string and marker
		        LocationManager.getinstance(view.getContext()).setStr(str,locationValues.to);
		        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
		    }
		});
        
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
						if (!isDriver){
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

					//swap fromTV and ToTv
					String temp = getAddressFromFromTV();
					
					// switch the strings, but cancel the autoComplete 
					fromAutoCompView.setAutoComplete(false);
					LocationManager.getinstance(getActivity()).setStr(getAddressFromToTV(), locationValues.from);
					toAutoCompView.setAutoComplete(false);
					LocationManager.getinstance(getActivity()).setStr(temp, locationValues.to);
					
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