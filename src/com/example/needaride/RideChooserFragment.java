package com.example.needaride;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class RideChooserFragment extends Fragment {
	ToggleButton taxiTB;
	ToggleButton lookForARidetoggleButton;
	ToggleButton addNewRidetoggleButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_ride_chooser,null);
		
		taxiTB = (ToggleButton) v.findViewById(R.id.TaxitoggleButton);
		lookForARidetoggleButton = (ToggleButton) v.findViewById(R.id.LookForARidetoggleButton);
		addNewRidetoggleButton = (ToggleButton) v.findViewById(R.id.AddNewRidetoggleButton);
	    
		taxiTB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (taxiTB.isChecked()){
					lookForARidetoggleButton.setChecked(false);
					addNewRidetoggleButton.setChecked(false);			
				}
			}
		});
		lookForARidetoggleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lookForARidetoggleButton.isChecked()){
					taxiTB.setChecked(false);
					addNewRidetoggleButton.setChecked(false);			
				}
			}				
		});
		addNewRidetoggleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (addNewRidetoggleButton.isChecked()){
					taxiTB.setChecked(false);	
					lookForARidetoggleButton.setChecked(false);
				}
			}
		});
		
		return v;
	}
}