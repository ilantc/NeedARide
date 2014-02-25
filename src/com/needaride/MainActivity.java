package com.needaride;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;


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
		    	// session is not open
		    	else {
		    		String msg = "";
		    		if (null != exception) {
		    			msg += "ex is: '" + exception.getMessage() + "'\n";
		    		}
		    		if (null != state) {
		    			msg += "state is: '" + state.toString() + "'";
		    		}
		    		Log.i("FB",msg);
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
