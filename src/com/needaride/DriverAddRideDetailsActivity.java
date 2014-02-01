package com.needaride;

import com.example.needaride.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

//This activity comes after filling the From, To and Date in the mapActivity and the user is the driver of this ride
//and exists in order to add more details of the ride as
//available sits
//ride fee to charge from the hikers
//additional comment
public class DriverAddRideDetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_add_ride_details);
		
		TextView fromTV = (TextView)findViewById(R.id.Driver_FromTV); 
		TextView toTV = (TextView)findViewById(R.id.Driver_ToTV);
		TextView dateTV = (TextView)findViewById(R.id.Driver_DateTV);
		
		//Checks the from,to,date data was sent from the rideDetailsFragment
		//and put it into their fields in the current activity
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    fromTV.setText(extras.getString("From"));
		    toTV.setText(extras.getString("To"));
		    dateTV.setText(extras.getString("Date"));
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driver_add_ride_details, menu);
		return true;
	}

}