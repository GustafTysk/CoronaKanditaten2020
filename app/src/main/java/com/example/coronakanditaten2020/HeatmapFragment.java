package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HeatmapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "Fragment Statistics";
    MapView mMapView;
    GoogleMap mGoogleMap;
    HeatmapTileProvider mProvider;
    Location[] Locations = new Location[12];
    Datahandler datahandler=new Datahandler();
    TileOverlay mOverlay;

    private Button btnHeatmapToStatistics;
    private Button btnHeatmapToStart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_heatmap, container, false);
        btnHeatmapToStart = (Button) view.findViewById(R.id.btnHeatmapToStart);
        btnHeatmapToStatistics = (Button) view.findViewById(R.id.btnHeatmapToStatistics);

        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);



        btnHeatmapToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Start Page", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(0);
            }
        });
        btnHeatmapToStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Statistics", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(1);
            }
        });

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mProvider = new HeatmapTileProvider.Builder()
                .weightedData((GenerateHeatMapCordsList( datahandler.getHeatmaplocations(),"2020-10-15", "placeholder")))
                .build();
        mOverlay = mGoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.8, 17.63), 10));


    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }






    private ArrayList<WeightedLatLng> GenerateHeatMapCordsList(Location[] heatmaplocations, String date, String Type) {
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        WeightedLatLng HeatCord;
        for (int i = 0; i < heatmaplocations.length - 1; i++) {
            System.out.println(i);
            if(this.locationtype(heatmaplocations[i],Type)&& heatmaplocations[i].date==date){

                list.add(new WeightedLatLng(new LatLng(Double.valueOf(heatmaplocations[i].getLatitude()),Double.valueOf(heatmaplocations[i].getLongitude())),
                                            GetLocationWeight(heatmaplocations[i],date,heatmaplocations.length)));

            }
        }
        return list;

    }

    //this funcitons weights a point based on symtoms and date and number of Cords

       private double GetLocationWeight(Location weightlocation, String date, int NummberOfCords ){
        //write the functions to return weight

        return 5.0;


       }

    //This function can be used to decide what type of location should be shown
    private boolean locationtype (Location location, String type){
        if(true){
            return true;
        }


        return false;
    }



}


