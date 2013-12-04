package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button mapBT = (Button)findViewById(R.id.mapButton);
		mapBT.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
                Toast.makeText(getApplicationContext(), "mapButton", Toast.LENGTH_SHORT).show();

				}
		});
		Button favouritesBT = (Button)findViewById(R.id.favouritesButton);
		favouritesBT.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
                Toast.makeText(getApplicationContext(), "favouritesButton", Toast.LENGTH_SHORT).show();
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
