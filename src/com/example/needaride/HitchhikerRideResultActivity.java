package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HitchhikerRideResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hitchhiker_ride_result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitchhiker_ride_result, menu);
		return true;
	}

}