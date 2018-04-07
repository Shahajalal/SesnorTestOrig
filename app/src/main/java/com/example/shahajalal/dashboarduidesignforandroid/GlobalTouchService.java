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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GlobalTouchService extends Service implements OnTouchListener {

	private String TAG = this.getClass().getSimpleName();
	// window manager 
	private WindowManager mWindowManager;
	// linear layout will use to detect touch event

	JSONObject jsonObject = new JSONObject();
	private LinearLayout touchLayout1, touchLayout2, touchLayout3;
	int action_outside_counter = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d("Intent:", arg0.toString());
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
		if (mWindowManager != null) {
			if (touchLayout1 != null) mWindowManager.removeView(touchLayout1);
			if (touchLayout2 != null) mWindowManager.removeView(touchLayout2);
			if (touchLayout3 != null) mWindowManager.removeView(touchLayout3);
		}
		super.onDestroy();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {


		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			jsonObject = new JSONObject();

			Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY());

			String s = "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY();
			JSONObject json = new JSONObject();
			try {
				json.put("Down_Value", s);
			} catch (JSONException e) {

			}

				try {
					jsonObject.put("Down_For_Main", json.toString());
				} catch (JSONException e) {

				}
		}



		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY());
			String s2 = "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY();
			JSONObject json2 = new JSONObject();
			try {
				json2.put("Move_Value", s2);
			} catch (JSONException e) {

			}

				try {
					jsonObject.put("Move_Value_For_Main", json2.toString());
				} catch (JSONException e) {

				}

		}

		if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
			//Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY());
			if(action_outside_counter==0) {
				String s3 = "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY();
				JSONObject json3 = new JSONObject();
				try {
					json3.put("Outside_Value", s3);
				} catch (JSONException e) {

				}

				DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
				Date date = new Date();
				String time = dateFormat.format(date);

				DatabaseHelper db = new DatabaseHelper(GlobalTouchService.this);
				int id = db.fatcheventsid();
				db.inserevents_metagysture(id, "Gesture", json3.toString(), time);

				Log.i(TAG, "outside value has been added to database");

			}
			action_outside_counter++;
			//Log.i("ActionCounter",Integer.toString(action_outside_counter));
			if(action_outside_counter>2)action_outside_counter=0;


		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY());
			String s1 = "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY();
			JSONObject json1 = new JSONObject();
			try {
				json1.put("Up_Value", s1);
			} catch (JSONException e) {
			}


				try {
					jsonObject.put("Up_Value_For_Main", json1.toString());

				} catch (JSONException e1) {

				}

			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
			Date date = new Date();
			String time=dateFormat.format(date);

			DatabaseHelper db=new DatabaseHelper(GlobalTouchService.this);
			int id=db.fatcheventsid();
			db.inserevents_metagysture(id,"Gesture",jsonObject.toString(),time);
			Log.i("GlobalTouchToDB",jsonObject.toString());
			Log.i(TAG,"All Gesture has been added to database");
		}


		return true;
	}
}

