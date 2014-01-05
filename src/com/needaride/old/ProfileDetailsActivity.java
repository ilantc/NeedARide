package com.needaride.old;

import com.example.needaride.R;
import com.example.needaride.R.layout;
import com.example.needaride.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProfileDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_details, menu);
		return true;
	}

}
