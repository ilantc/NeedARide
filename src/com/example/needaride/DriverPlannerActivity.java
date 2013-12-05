package com.example.needaride;

import com.example.needaride.util.WrapInt;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class DriverPlannerActivity extends Activity {

	Button mNumOfSitsDialogBT;
	WrapInt mnumberPicked = new WrapInt(0);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_planner);	    
	    
		mNumOfSitsDialogBT = (Button) findViewById(R.id.NumOfSitsDialogBT);
		mNumOfSitsDialogBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new NumberPickerDialogGetter(v.getContext(), mnumberPicked)).showDialog();
			}
		});
		
	}

	public void setNumOfSits(int numOfSits) {
		mNumOfSitsDialogBT.setText(Integer.toString(numOfSits));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitchhiker_planner, menu);
		return true;
	}

}
