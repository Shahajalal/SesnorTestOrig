package com.example.shahajalal.dashboarduidesignforandroid;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class GlobalTouchService extends Service implements OnTouchListener {

	private String TAG = this.getClass().getSimpleName();
	// window manager 
	private WindowManager mWindowManager;
	// linear layout will use to detect touch event
	private LinearLayout touchLayout1,touchLayout2,touchLayout3;
	@Override
	public IBinder onBind(Intent arg0) {
        Log.d("Intent:" , arg0.toString());
		return null;
	}
	@Override
	public void onCreate() {

		super.onCreate();
		// create linear layout
		touchLayout1 = new LinearLayout(this);
		touchLayout2 = new LinearLayout(this);
		touchLayout3 = new LinearLayout(this);

		// set layout width 30 px and height is equal to full screen
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		touchLayout1.setLayoutParams(lp);
		touchLayout2.setLayoutParams(lp);
		touchLayout3.setLayoutParams(lp);

		//touchLayout1.setBackgroundColor(Color.RED);
		//touchLayout2.setBackgroundColor(Color.RED);
		//touchLayout3.setBackgroundColor(Color.RED);
		// set color if you want layout visible on screen

		// fetch window manager object 
		 mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		touchLayout1.setOnTouchListener(this);
		touchLayout2.setOnTouchListener(this);
		touchLayout3.setOnTouchListener(this);

		 // set layout parameter of window manager
		 WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(30,
				 WindowManager.LayoutParams.MATCH_PARENT,
				 WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
				 WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
				 PixelFormat.TRANSPARENT);
         mParams.gravity = Gravity.START | Gravity.TOP;
	     mWindowManager.addView(touchLayout1, mParams);
	     mParams.gravity = Gravity.END | Gravity.TOP;
		 mWindowManager.addView(touchLayout2, mParams);
		 mParams.height = 30;
		 mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		 mParams.gravity = Gravity.END | Gravity.TOP;
		mWindowManager.addView(touchLayout3, mParams);
	}
	

	@Override
	public void onDestroy() {
		 if(mWindowManager != null) {
		 	 if(touchLayout1 != null) mWindowManager.removeView(touchLayout1);
			 if(touchLayout2 != null) mWindowManager.removeView(touchLayout2);
			 if(touchLayout3 != null) mWindowManager.removeView(touchLayout3);
	        }
		super.onDestroy();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP)
			Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());


		return false;
	}


}
