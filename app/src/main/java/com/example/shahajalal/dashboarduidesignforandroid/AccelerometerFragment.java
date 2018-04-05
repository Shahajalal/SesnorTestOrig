package com.example.shahajalal.dashboarduidesignforandroid;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AccelerometerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccelerometerFragment extends Fragment{
    SensorManager sensorManager;
    Sensor accelerometer;
    public Handler mHandler = new Handler();
    public Runnable mTimer;
    public float x = 0,y=0,z=0;
    public boolean boolSwitch = false;
    GraphView graph;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LineGraphSeries<DataPoint> xseries;
    LineGraphSeries<DataPoint> yseries;
    LineGraphSeries<DataPoint> zseries;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int counter = 1;


    public AccelerometerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccelerometerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccelerometerFragment newInstance(String param1, String param2) {
        AccelerometerFragment fragment = new AccelerometerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_accelerometer, container, false);

        graph = (GraphView) v.findViewById(R.id.graph);

        xseries = new LineGraphSeries<>(new DataPoint[] {});
        yseries = new LineGraphSeries<>(new DataPoint[] {});
        zseries = new LineGraphSeries<>(new DataPoint[] {});

        xseries.setColor(Color.RED);
        yseries.setColor(Color.GREEN);
        zseries.setColor(Color.BLUE);



        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-10);
        graph.getViewport().setMaxX(10);



        toggleSwitcher(boolSwitch);


        graph.addSeries(xseries);
        graph.addSeries(yseries);
        graph.addSeries(zseries);




        // Inflate the layout for this fragment
        return v;

    }
    public void toggleSwitcher(boolean bs)
    {
        counter = 1;
        boolSwitch = bs;
        xseries = new LineGraphSeries<>(new DataPoint[] {});
        yseries = new LineGraphSeries<>(new DataPoint[] {});
        zseries = new LineGraphSeries<>(new DataPoint[] {});
        xseries.setColor(Color.RED);
        yseries.setColor(Color.GREEN);
        zseries.setColor(Color.BLUE);


        mHandler = null;
        mTimer = null;

        mHandler = new Handler();
        mTimer = new Runnable() {
            @Override
            public void run() {
                xseries.appendData(new DataPoint(counter, x),true,counter);
                yseries.appendData(new DataPoint(counter, y),true,counter);
                zseries.appendData(new DataPoint(counter, z),true,counter);
                Log.d("Accelerometer",Integer.toString(counter));
                counter++;
                if (boolSwitch) mHandler.postDelayed(this, 300);
            }
        };


        if (boolSwitch) mHandler.postDelayed(mTimer, 300);
    }
}
