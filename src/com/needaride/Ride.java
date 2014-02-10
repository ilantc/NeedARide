package com.needaride;

import android.util.Log;

public class Ride {
	
	String driverUserID=null;
	RideLocation fromLocation;
	RideLocation toLocation;
	String time=null;
	String date=null;
	int availableSits = 0;
	int rideFee = 0;
	String driversComment = null;
	
	private String debugTag = "Ride";
	
	public Ride(String mDriverID,RideLocation mFromLocation,RideLocation mToLocation,String mTime,
						String mDate,int mAvailableSits,int mRideFee,String mDriversComment){
		driverUserID = mDriverID;
		fromLocation = mFromLocation;
		toLocation = mToLocation;
		time = mTime;
		date = mDate;
		availableSits = mAvailableSits;
		rideFee = mRideFee;
		driversComment = mDriversComment;
	}
	public Boolean InsertToDB(){
		try{
			ClientAsync ca = new ClientAsync();
			String userID = driverUserID;
			
			String fromCity = fromLocation.getCity();
			String fromStreet = fromLocation.getStreet();
			String fromHouseNo = fromLocation.getHouseNo();
			String fromLat = String.valueOf(fromLocation.getLatlng().latitude);
			String fromLng = String.valueOf(fromLocation.getLatlng().longitude);
			
			String toCity = toLocation.getCity();
			String toStreet = toLocation.getStreet();
			String toHouseNo = toLocation.getHouseNo();
			String toLat = String.valueOf(toLocation.getLatlng().latitude);
			String toLng = String.valueOf(toLocation.getLatlng().longitude);
			
			String availableSitsString = String.valueOf(availableSits);
			String feeString = String.valueOf(rideFee);
			
			ca.execute("addnewride", userID,fromCity,fromStreet,fromHouseNo,fromLat,fromLng,toCity,toStreet,toHouseNo,toLat,toLng,date,availableSitsString,feeString,driversComment);
			
			return true;
		}
		catch(Exception e){
			Log.e(debugTag,"could not insert to DB");
			return false;
		}
	}
		
		public Boolean InsertToDB_old(){
			try{
				ClientAsync ca = new ClientAsync();
				String userID = driverUserID;
				String from = fromLocation.getFullString();
				String to = toLocation.getFullString();
				ca.execute("addnewride", userID,from,to,date);
				//Log.e(debugTag,"Ride was inserted successfuly to DB");
				return true;
			}
			catch(Exception e){
				Log.e(debugTag,"could not insert to DB");
				return false;
			}
	}
}
