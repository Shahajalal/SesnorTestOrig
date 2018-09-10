package com.lordsofts.sensortest;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static android.content.Context.MODE_PRIVATE;


public class MeasureFingerPressure extends Fragment {

    TextView measurefinger;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_measure_fingerprint_pressure, container, false);
        measurefinger= v.findViewById(R.id.measurefingerpressuretextid);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pressuresend", Context.MODE_PRIVATE);
        measurefinger.setText("The Finger Pressure is \n"+preferences.getString("pressure",null));
        return v;


    }

}
