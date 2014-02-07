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
			
			String toCity = toLocation.getCity();
			String toStreet = toLocation.getStreet();
			String toHouseNo = toLocation.getHouseNo();
			
			//ca.execute("addnewrideservlet", userID,fromCity,fromStreet,fromHouseNo,toCity,toStreet,toHouseNo,date,time,availableSits,rideFee,driversComment);
			
			return true;
		}
		catch(Exception e){
			Log.e("Ride","could not insert to DB");
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
				return true;
			}
			catch(Exception e){
				Log.e("Ride","could not insert to DB");
				return false;
			}
	}
}
