package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DriverPlannerActivity extends basePlannerActivity {

	Button mNumOfSitsDialogBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewWithDate(R.layout.activity_driver_planner);	    
	    
		mNumOfSitsDialogBT = (Button) findViewById(R.id.NumOfSitsDialogBT);
		mNumOfSitsDialogBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new NumberPickerDialogGetter(v.getContext(), mNumOfSitsDialogBT)).showDialog();
				
				
				
//				Toast.makeText(getApplicationContext(), "button val is " +  mNumOfSitsDialogBT.getText() + "got focus is " + gotF, Toast.LENGTH_SHORT).show();
				
//				runOnUiThread(new Runnable() {
//
//				    @Override
//				    public void run() {
//				    	mNumOfSitsDialogBT.setText(Integer.toString(mnumberPicked.getV()));
//				    }
//				});	
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitchhiker_planner, menu);
		return true;
	}

}
