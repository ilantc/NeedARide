package com.needaride.old;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import com.example.needaride.R;
import com.example.needaride.R.anim;
import com.example.needaride.R.id;
import com.example.needaride.R.layout;
import com.example.needaride.R.menu;
import com.needaride.basePlannerActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HitchhikerPlannerActivity extends basePlannerActivity {
	
	TextView resultFromServerTV = (TextView)findViewById(R.id.resultFromServerTV);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewWithDate(R.layout.activity_hitchhiker_planner);
		final ImageView checkingForSimilarRidesFadeInIV = (ImageView) findViewById(R.id.checkingForSimilarRidesFadeInIV);
		final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		
		final EditText fromET = (EditText) findViewById(R.id.fromET);
		
		Button submitBT = (Button) findViewById(R.id.submitHitchhikerFormBT);
		submitBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				checkingForSimilarRidesFadeInIV.startAnimation(animationFadeIn);
				//set timer to 5 seconds and launches the noSimilarRideActivity
				resultFromServerTV.setText("Proccesing..");
				ClientAsync ca = new ClientAsync();
				String id;  
				
				/*				Timer timer=new Timer();
				timer.cancel();
		        timer = new Timer();
		        timer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		            	//checks is 
	        			
		            	//it doesnt work... goes every time to else !!!!!!!!!!!
		            	if (fromET.getText().equals(null)){
		            		Intent intent = new Intent();
			        		intent.setClassName(getPackageName(), getPackageName() + ".NoSimilarRidesActivity");
			        		startActivity(intent);
		        		}
		        		else{
//		        			Intent intent = new Intent();
			        		//intent.setClassName(getPackageName(), getPackageName() + ".HitchhikerRideResultActivity");
//			        		intent.setClassName(getPackageName(), getPackageName() + ".NoSimilarRidesActivity");
//			        		startActivity(intent);
		        			ClientAsync ca = 
		        		}
		            }
		        }, 4000);
		        //end of timer
*/
    			
			
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitchhiker_planner, menu);
		return true;
	}



	
	
	private class ClientAsync extends AsyncTask<String, Void, String>{
	
		@Override
		protected String doInBackground(String... strs) {
			String response = "";
			try{
				URL url = new URL("https://fifth-sunup-454.appspot.com/" + strs[0]);
				
				String param = "id=" +URLEncoder.encode(strs[1],"UTF-8") + "&" + "val=" + URLEncoder.encode(strs[2],"UTF-8");
				
				//opening a connection
				HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
				
				con.setDoOutput(true);
				
				con.setRequestMethod("POST");
				
				con.setFixedLengthStreamingMode(param.getBytes().length);
				
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
				//send the POST out
				PrintWriter out = new PrintWriter(con.getOutputStream());
				out.print(param);
				out.close();
			
				
				//parsing the response with the scanner class
				Scanner inStream = new Scanner(con.getInputStream());
				while (inStream.hasNextLine())
					response +=(inStream.nextLine());
				
			}
			catch(MalformedURLException e1){
				e1.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			return response;
		}
		protected void onPostExecute(String res){
			resultFromServerTV.setText(res);
		}
	}


}