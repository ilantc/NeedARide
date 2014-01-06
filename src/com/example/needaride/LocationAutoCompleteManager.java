package com.example.needaride;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LocationAutoCompleteManager {

	
	private static final String LOG_TAG = "ExampleApp";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	private String API_KEY;
	
	
	LocationAutoCompleteManager(Context c) {
		API_KEY = c.getResources().getString(R.string.google_places_server_key);
	}
	
	public ArrayList<String> autocomplete(String input) {
		ArrayList<String> resultList = null;

	    HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	        sb.append("?sensor=false&key=" + API_KEY);
	        sb.append("&components=country:il");
	        sb.append("&input=" + URLEncoder.encode(input, "utf8"));
	        Log.e(LOG_TAG, "url is " + sb.toString());
	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());

	        // Load the results into a StringBuilder
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
	        
	        // check if req denied
			try {
				JSONObject jsonObj = new JSONObject(jsonResults.toString());
				JSONObject jobj;
				jobj = jsonObj.getJSONObject("status");
				if ( "REQUEST_DENIED" == jobj.getString("status")  ) {
					Log.e(LOG_TAG, "json status = REQUEST_DENIED");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(LOG_TAG, "exception when trying to parse a single json obj, \n" + jsonResults.toString());
			}
	    } catch (MalformedURLException e) {
	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        return resultList;
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        return resultList;
	    } finally {
	        if (conn != null) {
	        	Log.e(LOG_TAG, "disconnecting");
	            conn.disconnect();
	        }
	    }

	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

	        // Extract the Place descriptions from the results
	        resultList = new ArrayList<String>(predsJsonArray.length());
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	        }
	        Log.e(LOG_TAG, "processed JSON results "+resultList.size());
	    } catch (JSONException e) {
	        Log.e(LOG_TAG, "Cannot process JSON results", e);
	    }

	    return resultList;
	}
	    
}
	
	