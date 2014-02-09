package com.needaride;

import java.util.List;

import org.w3c.dom.ls.LSException;

import com.example.needaride.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.needaride.LocationManager.locationValues;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class MapActivity extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
LocationClient mLocationClient;
//LocationManager mLocationManager;
static Marker fromMarker;
static Marker toMarker;
static GoogleMap Map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mLocationClient = new LocationClient(this, this, this);
//		mLocationManager = LocationManager.getinstance(this);
		
		if (ConnectionResult.SUCCESS == GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) ){
			Map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			Map.setMyLocationEnabled(true);
			
			Map.setOnMapClickListener(new OnMapClickListener() {
				@Override
				public void onMapClick(final LatLng point) {
					LocationManager.getinstance(getBaseContext()).onMapClick(point);				
				}
			});
			
			Map.setOnMarkerClickListener(new OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker marker) {					
					return LocationManager.getinstance(getBaseContext()).onMarkerClick(marker);	
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	//on map ConnectionFailed
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		//Toast.makeText(getApplicationContext(), "onConnectionFailed", Toast.LENGTH_SHORT).show();
	}
	
	//Launch when the location client is connected
	@Override
	public void onConnected(Bundle arg0) {
		//set the current location in the FromTV if no marker is on and the edit text are empty
		// (meaning that no user interaction has begun)
		LocationManager lm = LocationManager.getinstance(getBaseContext());
		Log.d("","loc client onConnected:\nfromTv = '" + RideDetailsFragment.getAddressFromFromTV() + 
				"'\ntoTv = '" + RideDetailsFragment.getAddressFromFromTV() + "'");
		if ( 	(null == lm.getFromRideLocation().getLatlng() ) 	 	&&
				(null == lm.getToRideLocation().getLatlng() ) 		 	&& 
				(RideDetailsFragment.getAddressFromFromTV().equals("")) && 
				(RideDetailsFragment.getAddressFromToTV().equals(""))	) {
			Log.d("","loc client - entered if");
			Location myLoc = mLocationClient.getLastLocation();
			Log.d("","loc client - found last loc: " + myLoc.getLatitude() + ", " + myLoc.getLongitude());
			try{
				LatLng latLng = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
				//Set the FromTV address to the current location
				lm.onMapClick(latLng);
			}
			catch(Exception e){
				Toast.makeText(getApplicationContext(), "Can't find your current location", Toast.LENGTH_LONG).show();
				Log.e("MapActivity","Cant operate the camera animation to the current location");
				Log.e("MapActivity",e.toString());
			}
		}
		// since this is the only use of the location client - we can disconnect it now
		mLocationClient.disconnect();
	}

	//Launch when map is disconnected from the fragment
	@Override
	public void onDisconnected() {
		//Toast.makeText(getApplicationContext(), "onDisconnected", Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
	}
	
	//When activity start
	@Override
    protected void onStart() {
        super.onStart();
		//Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
		//Focusing on Israel
		//Focusing TelAviv
        //LatLng initLatlng = new LatLng(32.06632, 34.77782);
		//Focusing Technion
        fixZoom(this);
        // Connect the client.
        mLocationClient.connect();
        SetToAddressFromSharedPreferences();

    }

    //Called when the Activity is no longer visible.
    @Override
    protected void onStop() {
		//Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_SHORT).show();

    	// Disconnecting the client invalidates it.
    	saveDestToSharedPreference(RideDetailsFragment.getAddressFromToTV());
    	mLocationClient.disconnect();
        super.onStop();
    }
  	
    //save the destination address to the Shared Preferences
  	//in use when : onStop
  	public Boolean saveDestToSharedPreference(String address){
		try{
			SharedPreferences addressDetails = this.getSharedPreferences("addressDetails", MODE_PRIVATE);
			Editor edit = addressDetails.edit();
			edit.clear();
			//edit.putString("username", txtUname.getText().toString().trim());
			//edit.putString("password", txtPass.getText().toString().trim());
			edit.putString("destAddress", address);
			edit.commit();
			Log.e("MapActivity", "address was saved to shared pref "+address);
			return true;
			//Toast.makeText(this, "Login details are saved..", 3000).show();
		}
		catch(Exception ex){
			System.err.println("Error during trying to save to SharedPreference:"+ex);
//			Toast.makeText(this, "Error during trying to save to SharedPreference", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

  //set the destination address from Shared Preferences
  	//in use when the user connect to the map Activity - onStart
  	public void SetToAddressFromSharedPreferences(){
		SharedPreferences addressDetails = this.getSharedPreferences("addressDetails", MODE_PRIVATE);
		String destAddress = addressDetails.getString("destAddress", null);
		RideDetailsFragment.setTextInToAutoCompView(destAddress);
		Log.e("MapActivity", "retrived from sheredPref: "+ destAddress);
	}
  	
	public static void addMarker(LatLng point, locationValues type, Context c){
		if (null == point) {
			Log.d("locMng","mapActivity: add marker, point = null, type = " + type.toString());
			if (locationValues.from == type) {
				// only remove the marker if it exists
				if (null != fromMarker) {
					fromMarker.remove();
				}
			}
			else if (locationValues.to == type) {
				if (null != toMarker) {
					toMarker.remove();
				}
			}
		}
		else if (locationValues.from == type) {
			Log.d("locMng","mapActivity: add marker, point = " + point.latitude + ", " + point.longitude + ", type = " + type.toString());
			fromMarker =  Map.addMarker(new MarkerOptions().position(point).title(type.toString()).icon(BitmapDescriptorFactory.fromResource(R.drawable.start_pin_no_outline)));
		}
		else if (locationValues.to == type) {
			toMarker =  Map.addMarker(new MarkerOptions().position(point).title(type.toString()).icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_pin_no_outline)));
		}
		fixZoom(c);
	}
	
	private static void fixZoom(Context c) {
		
	    List<LatLng> points = LocationManager.getinstance(c).getPointsForMapZoom();
	    CameraUpdate cu; 
	    
	    // if more than one point - move the camera to contain all points
	    if (points.size() > 1) {
		    LatLngBounds.Builder bc = new LatLngBounds.Builder();

		    for (LatLng item : points) {
		        bc.include(item);
		    }
		    cu = CameraUpdateFactory.newLatLngBounds(bc.build(), 70);
	    }
	    // only one point - move the camera to contain this point with fixed zoom level of 14
	    else if (points.size() == 1){
	    	cu = CameraUpdateFactory.newLatLngZoom(points.get(0), 14);
	    }
	    // should never get here as there is a default point in LocationManager
	    else {
	    	Log.e("","no points were given to zoom");
	    	return;
	    }
	    Map.animateCamera(cu);
	}
}
