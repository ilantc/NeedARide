package com.example.needaride;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class HitchhikerPlannerActivity extends basePlannerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewWithDate(R.layout.activity_hitchhiker_planner);
		final ImageView checkingForSimilarRidesFadeInIV = (ImageView) findViewById(R.id.checkingForSimilarRidesFadeInIV);
		final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		
		final EditText fromET = (EditText) findViewById(R.id.fromET);
		
		Button submitBT = (Button) findViewById(R.id.submitHitchhikerFormBT);
		submitBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				checkingForSimilarRidesFadeInIV.startAnimation(animationFadeIn);
				//set timer to 5 seconds and launches the noSimilarRideActivity
				Timer timer=new Timer();
				timer.cancel();
		        timer = new Timer();
		        timer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		            	//checks is 
	        			
		            	//it doesnt work... goes every time to else !!!!!!!!!!!
		            	if (fromET.getText().equals(null)){
		            		Intent intent = new Intent();
			        		intent.setClassName(getPackageName(), getPackageName() + ".NoSimilarRidesActivity");
			        		startActivity(intent);
		        		}
		        		else{
		        			Intent intent = new Intent();
			        		//intent.setClassName(getPackageName(), getPackageName() + ".HitchhikerRideResultActivity");
			        		intent.setClassName(getPackageName(), getPackageName() + ".NoSimilarRidesActivity");
			        		
			        		startActivity(intent);
		        		}
		            }
		        }, 4000);
		        //end of timer
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
