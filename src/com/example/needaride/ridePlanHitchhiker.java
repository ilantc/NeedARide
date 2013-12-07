package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ridePlanHitchhiker extends basePlannerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewWithDate(R.layout.form_basic_ride);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
