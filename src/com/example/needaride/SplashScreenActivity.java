package com.example.needaride;

import java.util.Timer;
import java.util.TimerTask;

import com.example.needaride.R;
import com.example.needaride.R.layout;
import com.example.needaride.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		Timer timer=new Timer();
		timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO: do what you need here (refresh list)
                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
        		Intent intent = new Intent();
        		intent.setClassName(getApplicationContext(),"com.needaride.old.MainActivity");
        		startActivity(intent);
        		finish();
            }
        }, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
