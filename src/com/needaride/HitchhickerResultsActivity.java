package com.needaride;

import com.example.needaride.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HitchhickerResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hitchhicker_results);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitchhicker_results, menu);
		return true;
	}

}
