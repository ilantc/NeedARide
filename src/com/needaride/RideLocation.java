package com.needaride;

import java.util.List;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class RideLocation {
	
	static String delimiter = ", ";
	
	private LatLng mLatlng;
	private String mCity;
	private String mStreet;
	private String mHouseNo;
	
	public RideLocation(LatLng latlng, String city, String street, String houseNo) {
		setLatlng(latlng);
		setCity(city);
		setStreet(street);
		setHouseNo(houseNo);
	}
	
	public void unsetFullString() {
		setCity("");
		setStreet("");
		setHouseNo("");
	}
	
	public void setAllString(List<String> in) {
		// use switch with fallthrough to make sure we don't get index out of bounds EX
		Log.e("dddd444",in.toString());
		switch (in.size()) {
			case 3:
				setCity(in.get(2));
//				Log.e("dddd22",in.get(2));
			case 2:
				setHouseNo(in.get(1));
//				Log.e("ddd11",in.get(1));
			case 1:
				setStreet(in.get(0));
//				Log.e("dddd00",in.get(0));
			default:
				break;
		}
	}
	
	public String getFullString() {
		String res = "";
		// if we have a street name
		if ("" != getStreet()) {
			res += getStreet();
//			Log.e("1dddd",res);
		}
		
		// if we have a house number, AND street
		if ("" != getHouseNo() && "" != res) {
			res += " " + getHouseNo();
//			Log.e("2dddd",res);
		}
		
		// if we have a city
		if ("" != getCity()) {
			// add delimiter if needed
			if ("" != res) {
				res += delimiter;
			}
			res += getCity();
//			Log.e("3dddd",res);
		}
//		Log.e("4dddd",res);
		return res;
	}
	
	public LatLng getLatlng() {
		return mLatlng;
	}

	public void setLatlng(LatLng Latlng) {
		this.mLatlng = Latlng;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(String mCity) {
		this.mCity = mCity;
	}

	public String getStreet() {
		return mStreet;
	}

	public void setStreet(String mStreet) {
		this.mStreet = mStreet;
	}

	public String getHouseNo() {
		return mHouseNo;
	}

	public void setHouseNo(String mHouseNo) {
		this.mHouseNo = mHouseNo;
	}
}
