package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HeatmapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private static final String TAG = "Fragment Statistics";
    private LatLng yourCurrentLocation;
    private LatLng sweden = new LatLng(62.3875,16.325556);
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private HeatmapTileProvider mProvider;
    Location[] Locations = new Location[12];
    Datahandler datahandler=new Datahandler();
    TileOverlay mOverlay;

    private Button btnHeatmapToStatistics;
    private Button btnHeatmapToStart;
    private Button btnTestChangeDay;
    private Button btnZoomInOnMe;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_heatmap, container, false);
/*        btnHeatmapToStart = (Button) view.findViewById(R.id.btnHeatmapToStart);
        btnHeatmapToStart.setOnClickListener(this);
        btnHeatmapToStatistics = (Button) view.findViewById(R.id.btnHeatmapToStatistics);
        btnHeatmapToStatistics.setOnClickListener(this);*/
        btnTestChangeDay = (Button) view.findViewById(R.id.btnTestChangeDay);
        btnTestChangeDay.setOnClickListener(this);
        btnZoomInOnMe = (Button) view.findViewById(R.id.btnZoomInOnMe);
        btnZoomInOnMe.setOnClickListener(this);

        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setCountry("SE");

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                LatLng selectlatlong=place.getLatLng();
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectlatlong,14));

                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        return view;
    }

    public void setHeatmapBottomNav(){
        ((MainActivity) requireActivity()).bottomNav = (BottomNavigationView) getView().findViewById(R.id.bottom_navigation);
        ((MainActivity) requireActivity()).bottomNav.setOnNavigationItemSelectedListener(((MainActivity) getActivity()).navListener);
        ((MainActivity) requireActivity()).bottomNav.getMenu().findItem(R.id.nav_heatmap).setChecked(true);    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnZoomInOnMe:
                yourCurrentLocation = ((MainActivity) getActivity()).getCurrentLocation();
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourCurrentLocation,14));
                break;
/*            case R.id.btnHeatmapToStart:
                ((MainActivity) getActivity()).setViewPager(1);
                break;
            case R.id.btnHeatmapToStatistics:
                ((MainActivity) getActivity()).setViewPager(2);
                break;*/
            case R.id.btnTestChangeDay:
                mProvider.setWeightedData(((ArrayList)GenerateHeatMapCordsList( datahandler.getHeatmaplocations(),"2020-10-16", "placeholder")));
                mOverlay.clearTileCache();
                break;

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mProvider = new HeatmapTileProvider.Builder()
                .weightedData((GenerateHeatMapCordsList( datahandler.getHeatmaplocations(),"2020-10-15", "placeholder")))
                .build();
        mOverlay = mGoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sweden,4));


        System.out.println("jajajaj");



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

       private double GetLocationWeight(Location weightlocation, String date, int NummberOfCords ) {
           double weight = 0;

           weight += weightlocation.diarrheaRatingBar * 0.2;
           weight += weightlocation.runnyNoseRatingBar * 1.0;
           weight += weightlocation.breathingRatingBar * 2.0;
           weight += weightlocation.coughRatingBar * 3.0;
           weight += weightlocation.feverRatingBar * 3.0;
           weight += weightlocation.headacheRatingBar * 0.8;
           weight += weightlocation.throatRatingBar * 2.0;
           weight += weightlocation.nasalCongestionRatingBar * 2.0;
           weight += weightlocation.tirednessRatingBar * 0.1;


           if (NummberOfCords > 1000000) {
               weight= NummberOfCords/2.0;
           }

           return weight;
           }





    //This function can be used to decide what type of location should be shown
    private boolean locationtype (Location location, String type){
        if(true){
            return true;
        }


        return false;
    }



}


