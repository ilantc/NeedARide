package com.example.needaride;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Dialog;
import android.content.Context;

public class TaxiOrPrivateDialogGetter  {

	Context context;
	
	public TaxiOrPrivateDialogGetter(Context c) {
		context = c;
	}
	
	public void showDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_private_or_taxi);
	 
		// set the custom dialog buttons
		Button taxiB 	= (Button) dialog.findViewById(R.id.taxiButtonDialog);
		Button privateB = (Button) dialog.findViewById(R.id.privateButtonDialog);		
		
		// if button is clicked, close the custom dialog
		taxiB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO - launch relevant activity
				dialog.dismiss();
			}
		});
	 
		privateB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO - launch relevant activity
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
}
