package com.example.needaride;

import android.os.Bundle;
import android.app.Activity;

import com.facebook.*;
import com.facebook.model.*;

import android.util.Log;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends Activity {
	public static GraphUser mUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {
			
		    // callback when session changes state
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		    	Log.e("main", "sdfjlhdfkjhsdfijsdfsdlfk;sdlfs");
		    	if (session.isOpened()) {
		    		// make request to the /me API
		    		Request.newMeRequest(session, new Request.GraphUserCallback() {

		    			// callback after Graph API response with user object
		    			@Override
		    			public void onCompleted(GraphUser user, Response response) {
		    				mUser = user;
		    				if (user != null) {
		    					TextView welcome = (TextView) findViewById(R.id.welcome);
		    					welcome.setText("Hello " + user.getName() + "!");
		    					
		    					try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    					Intent intent = new Intent();
								intent.setClassName(getApplicationContext(),"com.needaride.MapActivity");
								startActivity(intent);
		    				}
		    			}
		    		}).executeAsync();;
		    	}
		    }
		});
	}

	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

}
