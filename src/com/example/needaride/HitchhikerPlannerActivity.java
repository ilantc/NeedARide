package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HitchhikerPlannerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hitchhiker_planner);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitchhiker_planner, menu);
		return true;
	}

}
