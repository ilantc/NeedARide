package com.needaride;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


// singleton class, as all fragments of MapActivity share this logic,
// no need for them all to hold new objects of LocationManager
// this object is the only one that is allowed to add/remove markers from the map

public class LocationManager {
	
	private static LocationManager instance = null;
	private LatLng FromLatLng;
	private LatLng ToLatLng;
	
	private String FromStr;
	private String ToStr;
	
	private String dTag = "locMngr";
	
	private Geocoder mGeocoder;
	private Context c; // only used for toasts.. TODO consider removing
	
	private LocationManager(Context c) {
		FromLatLng 	= null;
		ToLatLng  	= null;
		FromStr 	= "";
		ToStr		= "";
		this.c = c;
		mGeocoder = new Geocoder(c, Locale.getDefault());
	}
	
	public static LocationManager getinstance(Context c) {
		if (null == instance) {
			instance = new LocationManager(c);
		}
		return instance;
	}
	
	
	// capture all listeners from the map 
	public void onMapClick(final LatLng point) {
		if (null == getFromLat()) {
			setFromLat(point);
//			String toastText = "set from marker: latlng = " + getFromLat().latitude + 
//					" " + getFromLat().longitude + "text = " + getFromStr();
//			Toast.makeText(c, toastText, Toast.LENGTH_SHORT).show();
		}
		else if (null == getToLat()) {
			setToLat(point);
//			String toastText = "set to marker: latlng = " + getToLat().latitude + 
//					" " + getToLat().longitude + "text = " +getToStr();
//			Toast.makeText(c, toastText, Toast.LENGTH_SHORT).show();
		}	
	}
	
	public boolean onMarkerClick(Marker marker) {
		
		if (marker.getTitle().equals("From")) {
			setFromLat(null);
			FromStr = "";
			RideDetailsFragment.setTextInFromAutoCompView(FromStr);
		}
		if (marker.getTitle().equals("To")) {
			setToLat(null);
			ToStr = "";
			RideDetailsFragment.setTextInToAutoCompView(ToStr);
		}
//		Toast.makeText(getApplicationContext(), "title=" + marker.getTitle() + " From="+isFromPinOnTheMap + " To="+isToPinOnTheMap, Toast.LENGTH_SHORT).show();
		return true;
	}
	
	
	// Setters
	public void setLat(LatLng latLng, String type) {
		if ("From" == type) {
			setFromLat(latLng);
		}
		else if ("To" == type) {
			setToLat(latLng);
		}
	}
	
	public void setToLat(LatLng toLatLng) {
		this.ToLatLng = toLatLng;
		if (null == ToLatLng) {
			// remove the marker from hte map
			MapActivity.addMarker(null, "To");
		}
		else {
			// update the text in the TV
			ToStr = getTextFromLatlng(ToLatLng);
			RideDetailsFragment.setTextInToAutoCompView(ToStr);
			MapActivity.addMarker(toLatLng,"To");
		}
	}
	
	public void setFromLat(LatLng fromLatLng) {
		FromLatLng = fromLatLng;
		if (null == FromLatLng) {
			// remove the marker from the map
			MapActivity.addMarker(null,"From");
		}
		else {
			// update the text in the TV
			FromStr = getTextFromLatlng(FromLatLng);
			Log.d("locMngr","adding from string: " + FromStr);
			RideDetailsFragment.setTextInFromAutoCompView(FromStr);
			MapActivity.addMarker(FromLatLng,"From");
		}
	}
	
	public void setStr(String str, String type) {
		if ("From" == type) {
			setFromStr(str);
		}
		else if ("To" == type) {
			setToStr(type);
		}
	}
	
	public void setFromStr(String fromStr) {
		FromStr = fromStr;
		
		FromLatLng = getLatLngFromAddress(fromStr);
		MapActivity.addMarker(FromLatLng,"From");
	}
			
	public void setToStr(String toStr) {
		ToStr = toStr;
		if ("" == toStr) {
			ToLatLng = null;
			MapActivity.addMarker(null, "To");
		}
		else {
			ToLatLng = getLatLngFromAddress(toStr);
			Log.d(dTag,"inside setToStr, tostr is: " + toStr + "ToLatlng is null?" + (null == ToLatLng));
			MapActivity.addMarker(ToLatLng,"To");
		}
	}
	
	
	// simple and more complex getters
	private String getTextFromLatlng(LatLng loc){
		
		List<Address> addresses = null;
		String fullAddress = "";
		Double lat 	= loc.latitude;
		Double lng 	= loc.longitude;
		Log.d(dTag,"Loc is: " + lat + " , " + lng);
		try {
			addresses = mGeocoder.getFromLocation(lat, lng, 1);
			String address = addresses.get(0).getAddressLine(0);
			Log.d(dTag,"got adress: " + address);
			String city = addresses.get(0).getAddressLine(1);
			//String country = addresses.get(0).getAddressLine(2);
			fullAddress = address +" "+ city;
			Log.e(dTag,"current address is:"+ address +" "+ city);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(c, "Can't find your current location make sure that you GPS is enable to optimize the App functionality\n" + e.getMessage(), Toast.LENGTH_LONG).show();
			Log.d(dTag,e.getMessage());
		}
		return fullAddress;
	}
	
	private LatLng getLatLngFromAddress(String address) {
		List<Address> addresses;
		LatLng 	loc_latlng = null;
	    try {
			addresses = mGeocoder.getFromLocationName(address,1);
		    if (addresses == null) {
		        return null;
		    }
		    Address loc_address = addresses.get(0);
		    loc_latlng  		= new LatLng(loc_address.getLatitude(), loc_address.getLongitude());
		    
	    } catch (IOException e) {
	    	Log.e(dTag,e.getMessage());
		}
	    return loc_latlng;
	}
	
	public LatLng getToLat() {
		return ToLatLng;
	}
	public LatLng getFromLat() {
		return FromLatLng;
	}
	public String getFromStr() {
		return FromStr;
	}
	public String getToStr() {
		return ToStr;
}

}