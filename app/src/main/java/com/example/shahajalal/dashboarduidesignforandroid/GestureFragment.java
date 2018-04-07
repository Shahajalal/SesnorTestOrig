package com.example.shahajalal.dashboarduidesignforandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GestureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestureFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public boolean strGesture = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textView;


    public GestureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GestureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GestureFragment newInstance(String param1, String param2) {
        GestureFragment fragment = new GestureFragment();
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
        View v = inflater.inflate(R.layout.fragment_gesture, container, false);
        TextView tFrag = v.findViewById(R.id.textView);
        if (strGesture==true)
        {
            tFrag.setText("Gesture service is running");
        }
        else
        {
            tFrag.setText("Gesture Service is not running");
        }

        // Inflate the layout for this fragment
        return v;
    }

}
