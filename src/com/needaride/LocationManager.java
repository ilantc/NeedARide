package com.needaride;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private RideLocation mfromRideLocation;
	private RideLocation mtoRideLocation;
	private String dTag = "locMngr";
	
	private Geocoder mGeocoder;
	private Context c; // only used for toasts.. TODO consider removing
	
	private LocationManager(Context c) {
		mfromRideLocation 	= new RideLocation(null, "", "", "");
		mtoRideLocation 	= new RideLocation(null, "", "", "");
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
		if (null == mfromRideLocation.getLatlng()) {
			setFromLat(point);
		}
		else if (null == mtoRideLocation.getLatlng()) {
			setToLat(point);
		}	
	}
	
	public boolean onMarkerClick(Marker marker) {
		
		if (marker.getTitle().equals("From")) {
			setFromLat(null);
			mfromRideLocation.unsetFullString();
			
			RideDetailsFragment.setTextInFromAutoCompView(mfromRideLocation.getFullString());
		}
		if (marker.getTitle().equals("To")) {
			setToLat(null);
			mtoRideLocation.unsetFullString();
			RideDetailsFragment.setTextInToAutoCompView(mtoRideLocation.getFullString());
		}
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
		mtoRideLocation.setLatlng(toLatLng);
		if (null == mtoRideLocation.getLatlng()) {
			// remove the marker from the map
			MapActivity.addMarker(null, "To");
		}
		else {
			// update the text in the TV
			List<String> toArgs = getTextFromLatlng(mtoRideLocation.getLatlng());
			mtoRideLocation.setAllString(toArgs);
			RideDetailsFragment.setTextInToAutoCompView(mtoRideLocation.getFullString());
			MapActivity.addMarker(mtoRideLocation.getLatlng(),"To");
		}
	}
	
	public void setFromLat(LatLng fromLatLng) {
		mfromRideLocation.setLatlng(fromLatLng);
		if (null == mfromRideLocation.getLatlng()) {
			// remove the marker from the map
			MapActivity.addMarker(null,"From");
		}
		else {
			// update the text in the TV
			List<String> fromArgs = getTextFromLatlng(mfromRideLocation.getLatlng());
			mfromRideLocation.setAllString(fromArgs);
			RideDetailsFragment.setTextInFromAutoCompView(mfromRideLocation.getFullString());
			MapActivity.addMarker(mfromRideLocation.getLatlng(),"From");
		}
	}
	
	public void setStr(String str, String type) {
		if ("From" == type) {
			setFromStr(str);
		}
		else if ("To" == type) {
			setToStr(str);
		}
	}
	
	private void setFromStr(String fromStr) {
		
		List<String> addressArgs = new ArrayList<String>();
		LatLng FromLatLng = getLatLngFromAddress(fromStr, addressArgs);
		Log.d(dTag," after  getLatLngFromAddress: out = '" + addressArgs.get(0) + "' , '" + addressArgs.get(1) + "'");
		// set the output of latlng and address args in the mfromrideLocation object 
		mfromRideLocation.setLatlng(FromLatLng);
		mfromRideLocation.setAllString(addressArgs);
		Log.d(dTag," after  setAllString, full Adress is: '" + mfromRideLocation.getFullString() + "'");
		// set the marker on the map and the text in TV
		RideDetailsFragment.setTextInFromAutoCompView(mfromRideLocation.getFullString());
		MapActivity.addMarker(mfromRideLocation.getLatlng(),"From");
	}
			
	private void setToStr(String toStr) {
		List<String> addressArgs = new ArrayList<String>();
		LatLng toLatLng = getLatLngFromAddress(toStr, addressArgs);
		
		// set the output of latlng and address args in the mtorideLocation object 
		mtoRideLocation.setLatlng(toLatLng);
		mtoRideLocation.setAllString(addressArgs);
		
		// set the marker on the map and the text in TV
		RideDetailsFragment.setTextInToAutoCompView(mtoRideLocation.getFullString());
		MapActivity.addMarker(mtoRideLocation.getLatlng(),"To");
	}
	
	
	private List<String> getTextFromLatlng(LatLng loc){
		
		List<Address> addresses = null;
		List<String> addressArgs = new ArrayList<String>();
		Double lat 	= loc.latitude;
		Double lng 	= loc.longitude;
		Log.d(dTag,"Loc is: " + lat + " , " + lng);
		try {
			addresses = mGeocoder.getFromLocation(lat, lng, 1);
			String address = addresses.get(0).getAddressLine(0);
			
			addressArgs = seperateAdressToStreetAndNo(address);
			Log.d(dTag,"got adress: " + address);
			addressArgs.add(2,addresses.get(0).getAddressLine(1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(c, "Can't find your current location make sure that you GPS is enable to optimize the App functionality\n" + e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e(dTag,"Exception: " + e.getMessage());
		}
		return addressArgs;
	}
	
	
	/* output is LatLng, 
	 * and the parsed address in the returnAdress array that is given as an input */
	public LatLng getLatLngFromAddress(String address, List<String> returnAdress) {
		List<Address> addresses;
		LatLng 	loc_latlng = null;
	    try {
			addresses = mGeocoder.getFromLocationName(address,1);
		    if (addresses == null) {
		        return null;
		    }
		    Address loc_address 	= addresses.get(0);
		    loc_latlng  			= new LatLng(loc_address.getLatitude(), loc_address.getLongitude());
		    List<String> outAdress 	= seperateAdressToStreetAndNo(loc_address.getAddressLine(0));
		    returnAdress.addAll(outAdress);
		    Log.d(dTag," after sep address: out = '" + returnAdress.get(0) + "' , '" + returnAdress.get(1) + "'");
	    } catch (IOException e) {
	    	Log.e(dTag,e.getMessage());
		}
	    return loc_latlng;
	}
	
	/* try to parse the street and house number from a given string
	 * look for a trailing number to figure this out */
	private List<String> seperateAdressToStreetAndNo(String address) {
		String[] streetRes = new String[2];
		List<String> out = new ArrayList<String>();
		out.add(0, ""); // placeholder
		String regex = "\\s\\d+$"; 
		// now we got a string with no trailing numbers
		streetRes = address.split(regex,2);
		// if it diffs from the original string, then the diff is the number
		if (! streetRes[0].matches(address)) {
			// create a new regexp from the street and an optional trailing space
			out.add(1, address.substring(streetRes[0].length()).trim());		
		}
		else {
			out.add(1,"");
		}
		out.set(0, streetRes[0].trim());
		Log.d(dTag,"sep address: out = '" + out.get(0) + "' , '" + out.get(1) + "'\n" + 
				   "Original string is: '" + address + "'");
		return out;
	}
}