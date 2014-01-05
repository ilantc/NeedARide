package com.example.needaride;

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
import android.location.Location;
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
	LatLng mFromLat;
	LatLng mToLat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mLocationClient = new LocationClient(this, this, this);
		
		
		
		if (ConnectionResult.SUCCESS == GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) ){
//			Toast.makeText(getApplicationContext(), "maps are avaliable", Toast.LENGTH_LONG).show();
			mMap = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			mMap.setMyLocationEnabled(true);
			mMap.setOnMapClickListener(new OnMapClickListener() {
				
				@Override
				public void onMapClick(final LatLng point) {
					if (null == mFromLat) {
						mFromLat = point;
						mMap.addMarker(new MarkerOptions().position(point).title("From").icon(BitmapDescriptorFactory.fromResource(R.drawable.jumping_frog)));
						Toast.makeText(getApplicationContext(), "lat is: "+ mFromLat.latitude + "long is:"+mFromLat.longitude, Toast.LENGTH_SHORT).show();
						
						mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
							@Override
							public boolean onMarkerClick(Marker marker) {
								// TODO Auto-generated method stub
								marker.remove();
								mFromLat = null;
								return true;
							}
						});
					}
					else if (null == mToLat) {
						mToLat = point;
						mMap.addMarker(new MarkerOptions().position(point).title("To").icon(BitmapDescriptorFactory.fromResource(R.drawable.landing_frog)));
						Toast.makeText(getApplicationContext(), "lat is: "+ mToLat.latitude + "long is:"+mToLat.longitude, Toast.LENGTH_SHORT).show();
						mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					
					@Override
					public boolean onMarkerClick(Marker marker) {
						// TODO Auto-generated method stub
						marker.remove();
						mToLat = null;
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
		Location myLoc = mLocationClient.getLastLocation();
		try{
			LatLng latLng = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
			mMap.animateCamera(cameraUpdate);
		}
		catch(Exception e){
			Log.e("BUG",e.toString());
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();        
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
}
