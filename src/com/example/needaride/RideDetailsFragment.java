package com.example.needaride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RideDetailsFragment extends Fragment {
	
	AutoCompleteTextView fromAutoCompView;
	AutoCompleteTextView toAutoCompView;
	ImageButton chooseDateIMGBT;
	TextView choosenDateTV;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_ride_details_form,null);
		
		chooseDateIMGBT = (ImageButton)v.findViewById(R.id.chooseDateIMGBT);
		choosenDateTV = (TextView)v.findViewById(R.id.choosenDateTV);
		setDate(choosenDateTV);
		Button submitBTN = (Button)v.findViewById(R.id.submitBTN);
		final ImageView checkingForSimilarRidesFadeInIV = (ImageView) v.findViewById(R.id.checkingForSimilarRidesFadeInIV);
		final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
		
		fromAutoCompView = (AutoCompleteTextView) v.findViewById(R.id.fromET);
        fromAutoCompView.setAdapter(new PlacesAutoCompleteAdapter(v.getContext(), R.layout.list_item));
        fromAutoCompView.addTextChangedListener(new autoCompManager(fromAutoCompView));
        fromAutoCompView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		        String str = (String) adapterView.getItemAtPosition(position);
		        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
		    }
		});
 
        toAutoCompView = (AutoCompleteTextView) v.findViewById(R.id.toET);
        toAutoCompView.setAdapter(new PlacesAutoCompleteAdapter(v.getContext(), R.layout.list_item));
        toAutoCompView.addTextChangedListener(new autoCompManager(toAutoCompView));
        toAutoCompView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		        String str = (String) adapterView.getItemAtPosition(position);
		        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
		    }
		});
        
        chooseDateIMGBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new TimeDialogGetter(v.getContext())).showDialog(choosenDateTV);
			}
		});
        
        submitBTN.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("RideDetailsFragment", "submit button was clicked");
				checkingForSimilarRidesFadeInIV.startAnimation(animationFadeIn);
			}
		});
        
		return v;
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
	 		int currHour = today.hour + 1 ;
	 		if (currHour == 24){
	 			currHour = 0;
	 			//Ilan need to correct the date  to tomorrow instade of today
	 		}
	 		formattedTime = currHour + ":" + today.minute;
	 		choosenDateTV.setText(formattedDate + "\n" + formattedTime);
	}
	
	
	private class autoCompManager implements TextWatcher {
		
		private CountDownTimer CDT;
		private CharSequence text;
		private long lastUpdateTime;
		private long timeForUpdateTextWhileTyping;
		
		public autoCompManager(final AutoCompleteTextView autoCompView) {
			int timeout = getResources().getInteger(R.integer.secsTillAutoComplete);
			lastUpdateTime = System.currentTimeMillis();
			timeForUpdateTextWhileTyping = (long) 1000 * getResources().getInteger(R.integer.secsForUpdateTextWhileTyping);
			CDT = new CountDownTimer((long) timeout * 1000, (long) timeout * 1000) {

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
				CDT.cancel();
			}
			// start new timer
			CDT.start();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
            // TODO Auto-generated method stub
        }
        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
	}
	
	
	
}