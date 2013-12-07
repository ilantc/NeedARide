package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class NoSimilarRidesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_similar_rides);
	

	ImageButton openARideIMGBT = (ImageButton) findViewById(R.id.goHomeIMGBT);
			openARideIMGBT.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {				
					Intent intent = new Intent();
					intent.setClassName(getPackageName(), getPackageName() +".MainActivity");
					startActivity(intent);
				}
			});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.no_similar_rides, menu);
		return true;
	}

}
