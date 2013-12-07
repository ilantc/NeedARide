package com.example.needaride;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

public class TaxiOrPrivateDialogGetter  {

	Context context;
	
	public TaxiOrPrivateDialogGetter(Context c) {
		context = c;
	}
	
	public void showDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(context,R.style.Theme_Dialog);
		dialog.setContentView(R.layout.dialog_private_or_taxi);
		dialog.setTitle(R.string.TaxiOrPrivateDialogHeader);
		
		// set the custom dialog buttons
		ImageButton taxiB 	= (ImageButton) dialog.findViewById(R.id.taxiButtonDialog);
		ImageButton privateB = (ImageButton) dialog.findViewById(R.id.privateButtonDialog);		
		// if button is clicked, close the custom dialog
		taxiB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				                   // TODO - launch relevant activity
				Intent intent = new Intent();

				intent.setClassName( context.getPackageName()  , context.getPackageName() + ".TaxiPlannerActivity");
				context.startActivity(intent);
				Toast.makeText(v.getContext(), "pressed taxi button", Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		});	
		
		privateB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();

				intent.setClassName( context.getPackageName()  , context.getPackageName() + ".DriverPlannerActivity");
				context.startActivity(intent);
				
				Toast.makeText(v.getContext(), "pressed private car button", Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}

	private void setTheme(String string) {
		// TODO Auto-generated method stub
		
	}
	
}
