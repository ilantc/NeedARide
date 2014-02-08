package com.needaride;

import com.example.needaride.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;


public class AutoCompleteTextViewWithDelay extends AutoCompleteTextView {

	// initialization
	long delay;
	long delayOverride; // max time between requests
	long lastUpdateTime;
	boolean noAutoComplete;
	Handler handler = new Handler();
	Runnable run;

	// constructor
	public AutoCompleteTextViewWithDelay(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    delay = 1000 * getResources().getInteger(R.integer.secsTillAutoComplete);
	    delayOverride = 1000 * getResources().getInteger(R.integer.secsForUpdateTextWhileTyping);
	    lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	protected void performFiltering(final CharSequence text, final int keyCode) {
		
		// do nothing if no autocomplete flag is on
		if (noAutoComplete) {
			return;
		}
		// perform filter on null to hide dropdown
	    doFiltering(null, keyCode);

	    // stop execution of previous handler
	    handler.removeCallbacks(run);

	    // creation of new runnable and prevent filtering of texts which length
	    // does not meet threshold
	    run = new Runnable() {
	        public void run() {
	        	if (isAutoCompleteOn()) {
	        		setLastUpdateTime();
	        		doFiltering(text, keyCode);
	        	}
	        }
	    };

	    // restart handler - if last update was not done recently, run handler without delay
	    if (System.currentTimeMillis() - lastUpdateTime > delayOverride) {
	    	handler.post(run);
	    }
	    // last update time was recently - call handler with delay
	    else {
	    	handler.postDelayed(run, delay);
	    }
	}

	// starts the actual filtering
	private void doFiltering(CharSequence text, int keyCode) {
	    super.performFiltering(text, keyCode);
	}
	
	public boolean isAutoCompleteOn() {
		return (! noAutoComplete);
	}
	
	public void setAutoComplete(boolean isOn) {
		noAutoComplete = (! isOn);
	}
	
	private void setLastUpdateTime() {
		lastUpdateTime = System.currentTimeMillis();
	}
}