
package com.needaride;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ClientAsync extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... strs) {
		String response ="";
		try{
			//the url of the server
			URL url = new URL("https://fifth-sunup-454.appspot.com/" + strs[0]);
			
			//encoding the parameters
			/*String param = "userID=" + URLEncoder.encode(strs[1], "UTF-8") + "&" + 
			"from="	+ URLEncoder.encode(strs[2], "UTF-8") + "&" + 
			"to=" + URLEncoder.encode(strs[3], "UTF-8") + "&" +
			"date="	+ URLEncoder.encode(strs[4], "UTF-8");
			*/
			//encoding the parameters
			String param = "userID=" + URLEncoder.encode(strs[1], "UTF-8") + "&" + 
					"fromCity="	+ URLEncoder.encode(strs[2], "UTF-8") + "&" +
					"fromStreet="	+ URLEncoder.encode(strs[3], "UTF-8") + "&" +
					"fromHouseNo="	+ URLEncoder.encode(strs[4], "UTF-8") + "&" +
					
					"toCity=" + URLEncoder.encode(strs[5], "UTF-8") + "&" +
					"toStreet=" + URLEncoder.encode(strs[6], "UTF-8") + "&" +
					"toHouseNo=" + URLEncoder.encode(strs[7], "UTF-8") + "&" +
					
					"date="	+ URLEncoder.encode(strs[8], "UTF-8")+ "&" + 
					"time="	+ URLEncoder.encode(strs[9], "UTF-8")+ "&" +
					
					"availableSits="	+ URLEncoder.encode(strs[10], "UTF-8")+ "&" +
					"rideFee="	+ URLEncoder.encode(strs[11], "UTF-8")+ "&" +
					"driversComment="	+ URLEncoder.encode(strs[12], "UTF-8");
			
			//opening a connection
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
			//set the connection for uploadinf data
			con.setDoOutput(true);
			
			//set the connection for POST kind
			con.setRequestMethod("POST");
			
			//set the length to fixed according to Android documentation suggestion
			con.setFixedLengthStreamingMode(param.getBytes().length);
			
			//for safety
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					
			//send the POST out
			PrintWriter out = new PrintWriter(con.getOutputStream());
			out.print(param);
			out.close();
			
			//parsing the response with the Scanner class
			Scanner inStream = new Scanner(con.getInputStream());
			while (inStream.hasNextLine())
				response += (inStream.nextLine());
		} catch (MalformedURLException e1){
			e1.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		Log.e("ClientAsyc", "doInBackground:"+response);
		return response;
	}
	protected void onPostExecute(String res){
		Log.e("ClientAsyc", "onPostExecute:"+res);
	}
}
