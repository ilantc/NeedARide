package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.NumberPicker;

public class DriverPlannerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_planner);
		
		NumberPicker np = (NumberPicker) findViewById(R.id.sitsNumPick);
	    int maxVal = getResources().getInteger(R.integer.maxNumOfSits);
	    
	    Log.i("DDDD","max int is " + maxVal);
	    
		String[] range = new String[maxVal];
		for (int i = 0 ; i < maxVal ; i++ )
			range[i] = Integer.toString(i + 1);
		
		Log.i("DDDD","after loop");
		
		np.setMinValue(1);
	    np.setMaxValue(maxVal);
	    np.setWrapSelectorWheel(false);
	    np.setDisplayedValues(range);
	    np.setValue(1);
	    // don't show soft keyboard on short click
	    np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

	    
	    
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitchhiker_planner, menu);
		return true;
	}

}
