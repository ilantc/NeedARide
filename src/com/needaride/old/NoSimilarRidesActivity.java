package com.needaride.old;

import com.example.needaride.R;
import com.example.needaride.R.id;
import com.example.needaride.R.layout;
import com.example.needaride.R.menu;

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
		ImageButton goToOpenARideIMGBT = (ImageButton) findViewById(R.id.goToOpenARideBT);
		goToOpenARideIMGBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				(new TaxiOrPrivateDialogGetter(v.getContext())).showDialog();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}