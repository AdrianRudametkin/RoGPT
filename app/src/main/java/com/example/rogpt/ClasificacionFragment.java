package com.example.rogpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rogpt.api.FootBallAPI;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClasificacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClasificacionFragment extends Fragment{
    Spinner spinner;

    public ClasificacionFragment() {
        // Required empty public constructor
    }

    public static ClasificacionFragment newInstance() {
        return new ClasificacionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_clasificacion, container,false);
        spinner = v.findViewById(R.id.spinner);
        FootBallAPI api = FootBallAPI.getInstance();
        Handler h = new Handler();
        h.postDelayed(new  Runnable() {
            @Override
            public void run() {
                changeSpinner(api.requestLeagues());
            }
        }, 3000);

        return v;
    }

    public void changeSpinner(ArrayList<String> list){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }
}