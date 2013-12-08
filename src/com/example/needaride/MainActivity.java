package com.example.needaride;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
		
		ImageButton profileIMGBT = (ImageButton) findViewById(R.id.profileIMGBT);
		profileIMGBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent();
				intent.setClassName(getPackageName(), getPackageName() + ".ProfileDetailsActivity");
				startActivity(intent);
			}
		});
		
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
//				Toast.makeText(getApplicationContext(), "take a ride  button clicked", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClassName(getPackageName(), getPackageName() + ".HitchhikerPlannerActivity");
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
//				Toast.makeText(getApplicationContext(), "map button clicked", Toast.LENGTH_SHORT).show();
				final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
				if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
					Toast.makeText(getApplicationContext(), "GPS not enable", Toast.LENGTH_SHORT).show();
					buildAlertMessageNoGps();
			    }
				else{
//					Intent intent = new Intent();
//					intent.setClassName(getPackageName(), getPackageName() + ".MapActivity");
//					startActivity(intent);
					
					
					//open map in browser
					String lat="41.9040";
					String lng="12.4530";
					Uri uri = Uri.parse("http://maps.google.com/maps?q=loc:"+lat+","+lng);
					Intent intent = new Intent(Intent.ACTION_VIEW,uri); 
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?").setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	            	   //startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)); 
	            	   dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	
	}

}
