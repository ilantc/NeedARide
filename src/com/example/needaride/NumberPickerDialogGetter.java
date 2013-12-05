package com.example.needaride;

import com.example.needaride.util.WrapInt;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

public class NumberPickerDialogGetter  {

	Context context;
	NumberPicker np;
	WrapInt retVal;
	
	public NumberPickerDialogGetter(Context c, WrapInt ret) {
		context = c;
		retVal  = ret;
	}
	
	public void showDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_number_picker);
		
		dialog.setTitle(R.string.numberPickerDialogHeader);
		
		// set the buttons numberPicker
		np = (NumberPicker) dialog.findViewById(R.id.sitsNumPick);
	    int maxVal = context.getResources().getInteger(R.integer.maxNumOfSits);
	    
	    Log.i("DDDD","max int is " + maxVal);
	    
		String[] range = new String[maxVal + 1];
		for (int i = 0 ; i < maxVal + 1 ; i++ )
			range[i] = Integer.toString(i);
		
		Log.i("DDDD","after loop");
		
		np.setMinValue(0);
	    np.setMaxValue(maxVal);
	    np.setWrapSelectorWheel(false);
	    np.setDisplayedValues(range);
	    np.setValue(0);
	    // don't show soft keyboard on short click
	    np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		Button OKBT 	= (Button) dialog.findViewById(R.id.OKButtonDialog);
		
		// if button is clicked, close the custom dialog
		OKBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO - launch relevant activity
				retVal.setV(np.getValue());
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
}
