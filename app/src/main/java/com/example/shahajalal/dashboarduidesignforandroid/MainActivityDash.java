package com.example.shahajalal.dashboarduidesignforandroid;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class MainActivityDash extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SensorEventListener {
    AccelerometerFragment fragAccelerometer = new AccelerometerFragment();
    gyrometerFragment fragGyrometer = new gyrometerFragment();
    GestureFragment fragGesture = new GestureFragment();
    SensorManager sensorManager;
    Sensor accelerometer,gyro;
    boolean tmp = false;
    Timer t;
    TimerTask task;
    public String startTime,endTime;
    private static final int OVERLAY_REQ_CODE = 25;
    Intent globalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash);
        DatabaseHelper db1=new DatabaseHelper(this);
        db1.CopyDB(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        globalService = new Intent(MainActivityDash.this,GlobalTouchService.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toggle) {

            if(item.getTitle().toString().equals("Start"))
            {
                item.setTitle("Stop");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, OVERLAY_REQ_CODE);
                    }
                }
                startService(globalService);
                accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                if(accelerometer!=null){
                    sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                    Log.d(TAG, "onCreate: initializing accelerometer listener");
                }else{

                }
                gyro=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                if(gyro!=null){
                    sensorManager.registerListener(this,gyro, SensorManager.SENSOR_DELAY_NORMAL);
                    Log.d(TAG, "onCreate: initializing gyro listener");
                }else{

                }


                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
                Date date = new Date();
                startTime=dateFormat.format(date);
                DatabaseHelper db = new DatabaseHelper(this);
                db.insertevents(startTime,endTime);
                Log.d(TAG, "insert events successful ");
                tmp = true;
                if (fragAccelerometer !=null) fragAccelerometer.toggleSwitcher(true);
                if (fragGyrometer !=null) fragGyrometer.toggleSwitcher(true);
                startTimer();

                //Start your timer
            }
            else
            {
                try {
                    stopService(globalService);
                    sensorManager.unregisterListener(this);
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
                    Date date = new Date();
                    endTime = dateFormat.format(date);
                    DatabaseHelper db = new DatabaseHelper(this);
                    int id1=db.fatcheventsid();
                    db.update(id1, endTime);
                    Log.d(TAG, "time updated to database");
                    tmp = false;
                    t.cancel();

                    if (fragAccelerometer !=null) fragAccelerometer.toggleSwitcher(false);
                    if (fragGyrometer !=null) fragGyrometer.toggleSwitcher(false);

                    Log.d(TAG, "End Button Clicked");
                }catch (Exception e){

                }
                //Stop your timer
                item.setTitle("Start");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        if (sensor.getType() == sensor.TYPE_ACCELEROMETER) {
            //Log.d(TAG, "onSensorChanged: X:" + event.values[0] + "Y:" + event.values[1] + "Z:" + event.values[2]);
            if(fragAccelerometer !=null)
            {
                fragAccelerometer.x=event.values[0];
                fragAccelerometer.y=event.values[1];
                fragAccelerometer.z=event.values[2];
            }

        }
        else if (sensor.getType() == sensor.TYPE_GYROSCOPE)
        {
            //Log.d(TAG, "onSensorChanged: X:" + event.values[0] + "Y:" + event.values[1] + "Z:" + event.values[2]);
            if(fragGyrometer !=null)
            {
                fragGyrometer.x=event.values[0];
                fragGyrometer.y=event.values[1];
                fragGyrometer.z=event.values[2];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (id == R.id.nav_accelerometer) {
                ft.replace(R.id.frame, fragAccelerometer);
                ft.commit();
        } else if (id == R.id.nav_gyro) {
                ft.replace(R.id.frame, fragGyrometer);
                ft.commit();
        } else if (id == R.id.nav_gesture) {
                ft.replace(R.id.frame, fragGesture);
                ft.commit();
        }  else if (id == R.id.nav_about) {
            AboutFragment fragAbout = new AboutFragment();
                ft.replace(R.id.frame, fragAbout);
                ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startTimer(){
        t = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
                        Date date = new Date();
                        String time=dateFormat.format(date);
                        Log.d("CurTime","Time is : " + time);


                        JSONObject accelerometerobj=new JSONObject();
                        try{

                            accelerometerobj.put("X", Float.toString(fragAccelerometer.x));
                            accelerometerobj.put("Y",Float.toString(fragAccelerometer.y));
                            accelerometerobj.put("Z",Float.toString(fragAccelerometer.z));
                        }catch(JSONException e){
                            Log.d(TAG, "JSONexception"+e);
                        }

                        JSONObject gyrometerobj=new JSONObject();
                        try{

                            gyrometerobj.put("X",Float.toString(fragGyrometer.x));
                            gyrometerobj.put("Y",Float.toString(fragGyrometer.y));
                            gyrometerobj.put("Z",Float.toString(fragGyrometer.z));
                        }catch(JSONException e){
                            Log.d(TAG, "JSONexception"+e);
                        }
                        catch (NullPointerException e)
                        {
                            //Nothing to do
                        }


                        Log.d(TAG, "All three json object has created");


                        String accelerometerInsert=accelerometerobj.toString();
                        String gyrometerInsert=gyrometerobj.toString();


                        Log.d(TAG, accelerometerInsert);


                        DatabaseHelper db=new DatabaseHelper(MainActivityDash.this);
                        int id=db.fatcheventsid();
                        Log.d(TAG, "" + "Events last id is : "+id);
                        db.inserevents_metaaccelerometer(id,"accelerometer",accelerometerInsert,time);
                        db.inserevents_metagyrometer(id,"Gyrometer",gyrometerInsert,time);


                        Log.d(TAG, "Events_meta inserted successfull");


                    }
                });
            }
        };

        t.scheduleAtFixedRate(task, 0, 1000);
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case OVERLAY_REQ_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivityDash.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivityDash.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
