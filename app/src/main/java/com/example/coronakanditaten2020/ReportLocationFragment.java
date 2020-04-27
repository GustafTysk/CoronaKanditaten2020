package com.example.coronakanditaten2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

public class ReportLocationFragment extends Fragment  implements OnMapReadyCallback, View.OnClickListener{
    private static final String TAG = "Fragment Statistics";
    GoogleMap mGoogleMap;
    MapView mMapView;

    private Button btnRlToStart;
    private Button btnRlToRs;

    private ImageButton setLocation1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_location, container, false);
        btnRlToStart = (Button) view.findViewById(R.id.btnRlToStart);
        btnRlToStart.setOnClickListener(this);
        btnRlToRs = (Button) view.findViewById(R.id.btnRlToRs);
        btnRlToRs.setOnClickListener(this);

        setLocation1 = (ImageButton) view.findViewById(R.id.setLocation1);
        //setLocation1.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnRlToRs:
                Toast.makeText(getActivity(), "Going to Report Symptoms", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(3);
                break;
            case R.id.btnRlToStart:
                Toast.makeText(getActivity(), "Going to Start", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(0);
                break;
            case R.id.setLocation1:
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                System.out.println("Kommer in hit");
                View mapV = inflater.inflate(R.layout.report_location_map, null);
                final PopupWindow mapWindow = new PopupWindow(mapV, 400, 400);
                mapWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                mMapView = (MapView) mapV.findViewById(R.id.mapViewReport);
                //mMapView.onCreate(savedInstanceState);
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mGoogleMap = googleMap;
                        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.8, 17.63), 10));
                        System.out.println("kkk");

                    }
                });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("jajaj");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
