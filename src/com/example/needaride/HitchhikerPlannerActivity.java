package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HitchhikerPlannerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hitchhiker_planner);
		
		ImageButton submitBT = (ImageButton) findViewById(R.id.submitHitchhikerFormBT);
		submitBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				(new TaxiOrPrivateDialogGetter(v.getContext())).showDialog();
			
				Intent intent = new Intent();
				intent.setClassName(getPackageName(), getPackageName() + ".NoSimilarPlannerActivity");
				startActivity(intent);
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
