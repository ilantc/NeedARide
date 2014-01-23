package com.needaride.old;

import com.example.needaride.R;
import com.example.needaride.R.anim;
import com.example.needaride.R.id;
import com.example.needaride.R.layout;
import com.example.needaride.R.menu;
import com.needaride.NumberPickerDialogGetter;
import com.needaride.basePlannerActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

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
			}
		});
		
		Button submitDriverPlanFormBT = (Button) findViewById(R.id.submitDriverPlanFormBT);
		final ImageView thankYouFadeInIV = (ImageView) findViewById(R.id.thankYouFadeInIV);
		final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		
		submitDriverPlanFormBT.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			thankYouFadeInIV.startAnimation(animationFadeIn);
			
//			Intent intent = new Intent();
//			intent.setClassName(getPackageName(), getPackageName() + ".MainActivity");
//			startActivity(intent);
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
