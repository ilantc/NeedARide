package com.needaride.old;

import com.example.needaride.NumberPickerDialogGetter;
import com.example.needaride.R;
import com.example.needaride.basePlannerActivity;
import com.example.needaride.R.id;
import com.example.needaride.R.layout;
import com.example.needaride.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TaxiPlannerActivity extends basePlannerActivity {
	
	Button mNumOfSitsDialogBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewWithDate(R.layout.activity_taxi_planner);	    
	    
		mNumOfSitsDialogBT = (Button) findViewById(R.id.NumOfSitsDialogBT);
		mNumOfSitsDialogBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new NumberPickerDialogGetter(v.getContext(), mNumOfSitsDialogBT)).showDialog();
			}
		});
		
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.taxi_planner, menu);
		return true;
	}

}



