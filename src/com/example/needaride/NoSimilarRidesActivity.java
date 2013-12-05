package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NoSimilarRidesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_similar_rides);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.no_similar_rides, menu);
		return true;
	}

}
