package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageButton offerRideBT = (ImageButton) findViewById(R.id.offerRideIMGBT);
		offerRideBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				(new TaxiOrPrivateDialogGetter(v.getContext())).showDialog();
			}
		});
		
		ImageButton takeARideBT = (ImageButton) findViewById(R.id.getARideIMGBT);
		takeARideBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				Toast.makeText(getApplicationContext(), "take a ride  button clicked", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClassName(getPackageName(), "com.example.needaride.HitchhikerPlannerActivity");
				startActivity(intent);
			}
		});
		
		ImageButton favouritesBT = (ImageButton) findViewById(R.id.favouritesIMGBT);
		favouritesBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				Toast.makeText(getApplicationContext(), "favourites button clicked", Toast.LENGTH_SHORT).show();
			}
		});
		ImageButton mapBT = (ImageButton) findViewById(R.id.mapIMGBT);
		mapBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				Toast.makeText(getApplicationContext(), "map button clicked", Toast.LENGTH_SHORT).show();}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
