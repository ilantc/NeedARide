package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
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
		ImageButton offerRideBT = (ImageButton) findViewById(R.id.offerRideBT);
		offerRideBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				(new TaxiOrPrivateDialogGetter(v.getContext())).showDialog();
			}
		});
		
		ImageButton offerOrTakeARideBT = (ImageButton) findViewById(R.id.offerOrTakeARideBT);
		offerRideBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				(new TaxiOrPrivateDialogGetter(v.getContext())).showDialog();
			}
		});
		
		Button favouritesBT = (Button) findViewById(R.id.favouritesBT);
		favouritesBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				Toast.makeText(getApplicationContext(), "favourites button clicked", Toast.LENGTH_SHORT).show();
			}
		});
		Button mapBT = (Button) findViewById(R.id.mapButtonBT);
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
