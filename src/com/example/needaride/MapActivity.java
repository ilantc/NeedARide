package com.example.needaride;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
	LocationClient mLocationClient;
	GoogleMap mMap;
	static GoogleMap mToMap;
	LatLng mFromLat;
	LatLng mToLat;
	
	Boolean isFromPinOnTheMap = false;
	Boolean isToPinOnTheMap = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mLocationClient = new LocationClient(this, this, this);
		if (ConnectionResult.SUCCESS == GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) ){
			mToMap = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			mMap = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			mMap.setMyLocationEnabled(true);
			mMap.setOnMapClickListener(new OnMapClickListener() {
				@Override
				public void onMapClick(final LatLng point) {
					if (null == mFromLat) {
						mFromLat = point;
						mMap.addMarker(new MarkerOptions().position(point).title("From").icon(BitmapDescriptorFactory.fromResource(R.drawable.start_pin)));
//						Toast.makeText(getApplicationContext(), "lat is: "+ mFromLat.latitude + "long is:"+mFromLat.longitude, Toast.LENGTH_SHORT).show();					
						//set the address that was pointed to the TV
						setTextOnTV(point.latitude,point.longitude,"from");
						isFromPinOnTheMap = true;
						//When click on the  From marker
						mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
							@Override
							public boolean onMarkerClick(Marker marker) {
								marker.remove();
								mFromLat = null;
								isFromPinOnTheMap = false;
								Toast.makeText(getApplicationContext(), "From="+isFromPinOnTheMap + " To="+isToPinOnTheMap, Toast.LENGTH_SHORT).show();					
								return true;
							}
						});
					}
					else if (null == mToLat) {
						mToLat = point;
						mToMap.addMarker(new MarkerOptions().position(point).title("To").icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_pin)));
//						Toast.makeText(getApplicationContext(), "lat is: "+ mToLat.latitude + "long is:"+mToLat.longitude, Toast.LENGTH_SHORT).show();
						setTextOnTV(point.latitude,point.longitude,"to");
						//draeing the roat
						Polyline line = mMap.addPolyline(new PolylineOptions()
		                .add(new LatLng(mFromLat.latitude, mFromLat.longitude), new LatLng(mToLat.latitude,   mToLat.longitude))
		                .width(2)
		                .color(Color.BLUE).geodesic(true));
						//end of drowing
						isToPinOnTheMap = true;
						mToMap.setOnMarkerClickListener(new OnMarkerClickListener() {
							@Override
							public boolean onMarkerClick(Marker marker) {
								// TODO Auto-generated method stub
								marker.remove();
								mToLat = null;
								isToPinOnTheMap = false;
								Toast.makeText(getApplicationContext(), "From="+isFromPinOnTheMap + " To="+isToPinOnTheMap, Toast.LENGTH_SHORT).show();
								return true;
							}
						});
						
					}					
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

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {}

	@Override
	public void onConnected(Bundle arg0) {
		//set the last address that was inserted in the last use of this app to ToTV
		SetToAddressFromSharedPreferences();
		//set the current location in the FromTV
		Location myLoc = mLocationClient.getLastLocation();
		try{
			LatLng latLng = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
			mMap.animateCamera(cameraUpdate);
			//Set the FromTV address to the current location
			setTextOnTV(myLoc.getLatitude(), myLoc.getLongitude(),"from");
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), "Can't find your current location", Toast.LENGTH_LONG).show();
			Log.e("MapActivity","Cant operate the camera animation to the current location");
			Log.e("MapActivity",e.toString());
		}
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		saveDestToSharedPreference(RideDetailsFragment.getAddressFromToTV());
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
        SetToAddressFromSharedPreferences();
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
    	saveDestToSharedPreference(RideDetailsFragment.getAddressFromToTV());
    	mLocationClient.disconnect();
        super.onStop();
    }
    
    
    
    
    
  //Set the FromTV address to the current location
  	private void setTextOnTV(Double mLatitude, Double mLongitude,String textTV){
  		
  		Geocoder geocoder;
  		List<Address> addresses = null;
  		geocoder = new Geocoder(this, Locale.getDefault());
  		try {
  			addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
  			String address = addresses.get(0).getAddressLine(0);
  			String city = addresses.get(0).getAddressLine(1);
  			String country = addresses.get(0).getAddressLine(2);
  			String fullAddress = address +" "+ city +" "+ country;
  			Log.e("MapActivity","current address is:"+ address +" "+ city +" "+ country);
  			if (textTV == "from"){
  				RideDetailsFragment.setTextInFromAutoCompView(fullAddress);
  			}
  			else{ //in "to" field
  				RideDetailsFragment.setTextInToAutoCompView(fullAddress);
  			}
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			Toast.makeText(getApplicationContext(), "Can't find your current location make sure that you GPS is enable to optimize the App functionality", Toast.LENGTH_LONG).show();
  			Toast.makeText(getApplicationContext(), "Can't find your current location make sure that you GPS is enable to optimize the App functionality", Toast.LENGTH_LONG).show();
  			Toast.makeText(getApplicationContext(), "Can't find your current location make sure that you GPS is enable to optimize the App functionality", Toast.LENGTH_LONG).show();
  			Log.e("MapActivity","Can't find your current location make sure that you GPS is enable to optimize the App functionality");
  			Log.e("MapActivity",e.toString());
  		}
  	}
  	
    //save the destination address to the Shared Preferences
  	//in use when : onStop, onDisconnect
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
  	//in use when the user connect to the map Activity
  	public void SetToAddressFromSharedPreferences(){
		SharedPreferences addressDetails = this.getSharedPreferences("addressDetails", MODE_PRIVATE);
		String destAddress = addressDetails.getString("destAddress", null);
		RideDetailsFragment.setTextInToAutoCompView(destAddress);
		Log.e("MapActivity", "retrived from sheredPref: "+ destAddress);
	}


  	public static void setToPinOnMap(LatLng point){
  		mToMap.addMarker(new MarkerOptions().position(point).title("To").icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_pin)));
  	}


}
