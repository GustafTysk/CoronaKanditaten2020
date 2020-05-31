package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
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
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class HeatmapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "Fragment Statistics";
    private LatLng yourCurrentLocation = new LatLng(59.8, 17.63);
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

    private com.applandeo.materialcalendarview.CalendarView cal;
    private Calendar minDate = Calendar.getInstance(TimeZone.getDefault());
    private Calendar maxDate = Calendar.getInstance(TimeZone.getDefault());
    private TextView dateDisplayheatmap;
    private Bundle  savedInstanceState;
    private View view;
    private ArrayList<Location> heatLocations;

    private SeekBar seekBarheatmap;
    private int seekBarMaxValue; // get maximum value of the Seek bar
    private int seekbarValue;

    //private Location[] locationsArray;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_heatmap, container, false);
        savedInstanceState=savedInstanceState;


            btnTestChangeDay = (Button) view.findViewById(R.id.btnTestChangeDay);
            btnTestChangeDay.setOnClickListener(this);
            btnZoomInOnMe = (Button) view.findViewById(R.id.btnZoomInOnMe);
            btnZoomInOnMe.setOnClickListener(this);

            mMapView = (MapView) view.findViewById(R.id.mapview);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);

            seekBarheatmap = (SeekBar) view.findViewById(R.id.seekBarHeatmap);
            seekBarheatmap.setOnSeekBarChangeListener(this);
            seekBarMaxValue = seekBarheatmap.getMax();
            dateDisplayheatmap = (TextView) view.findViewById(R.id.dateDisplayheatmap);
            Date today = new Date();
            dateDisplayheatmap.setText(convertDateToString(today));

            AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                    getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
            autocompleteFragment.setCountry("SE");

// Specify the types of place data to return.
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

// Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.

                    LatLng selectlatlong = place.getLatLng();
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectlatlong, 14));

                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });

        //locationsArray = datahandler.getHeatmaplocations();
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
                if(((MainActivity) getActivity()).getCurrentLocation() != null){
                    yourCurrentLocation = ((MainActivity) getActivity()).getCurrentLocation();
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourCurrentLocation,14));
                    break;
                }
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourCurrentLocation,14));
                break;
/*            case R.id.btnHeatmapToStart:
                ((MainActivity) getActivity()).setViewPager(1);
                break;
            case R.id.btnHeatmapToStatistics:
                ((MainActivity) getActivity()).setViewPager(2);
                break;*/
            case R.id.btnTestChangeDay:
                try {
                    getCalendarPicker();
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
//                mProvider.setWeightedData(((ArrayList)GenerateHeatMapCordsList( datahandler.getHeatmaplocations(),"2020-10-16", "placeholder")));
//                mOverlay.clearTileCache();
                break;

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekbarValue = progress;
        String dateString = convertDateToString(convertDayOfInterestToDate(seekbarValue));
        changeToDate(dateString);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public Date convertDayOfInterestToDate(int dayOfInterestLast30Days){
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -dayOfInterestLast30Days);
        Date dateOfInterest = cal.getTime();
        return dateOfInterest;
    }

    public String convertDateToString(Date date){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String dateString =Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
        return dateString;
    }

    public String convertStringPrintToDataFormat(String string){
        String[] parts = string.split("-");
        int werid=Integer.parseInt(parts[1]);



        return parts[2]+"-"+(werid)+"-"+parts[0];
    }

//    public boolean checkIfReportsOnDate(String date){
//        for(int i =0; i < locationsArray.length; i++){
//            if(locationsArray[i].date.equals(date)){
//                System.out.println("found equal date");
//                return true;
//            }
//        }
//        System.out.println("found NO equal date");
//        return false;
//
//    }

    public void changeToDate(String date){
                mProvider.setWeightedData(((ArrayList) GenerateHeatMapCordsList(heatLocations, date, "placeholder")));
                mOverlay.clearTileCache();
    }

    public void getCalendarPicker() throws OutOfDateRangeException {
        OnSelectDateListener listener = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                String changeToDateString = null;

                for (Calendar day: calendars){
                    changeToDateString = convertDateToString(day.getTime());
                }
                if (changeToDateString != null) {
                    changeToDate(changeToDateString);
                }
                else{
                    Toast.makeText(getContext(),"No new date selected", Toast.LENGTH_LONG).show();
                }
            }
        };
        DatePickerBuilder builder = new DatePickerBuilder(getContext(), listener)
                .pickerType(cal.ONE_DAY_PICKER).setMaximumDate(maxDate);

        DatePicker datePicker = builder.build();
        datePicker.show();
    }

//    public void setDaysAvailable(DatePickerBuilder builder, Location[] heatmapdates){
//        List<Calendar> disabledDaysCalendars = new ArrayList<>();
//        for(int i=1; i < heatmapdates.length; i++){
//            builder.setDisabledDays()theatmapdates[i].date
//        }
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sweden,4));
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






    private ArrayList<WeightedLatLng> GenerateHeatMapCordsList(ArrayList<Location> heatmaplocations, String date, String Type) {
        String compareString = convertStringPrintToDataFormat(date);
        System.out.println(compareString);
        // OM DET INTE FINNS DATA FÖR DATUMET RETURN THIS MED EN SATT LOCATION
        ArrayList<WeightedLatLng> listReserve = new ArrayList<WeightedLatLng>();

        listReserve.add(new WeightedLatLng(new LatLng(Double.valueOf(heatmaplocations.get(0).getLatitude()),Double.valueOf(heatmaplocations.get(0).getLongitude())),
                GetLocationWeight(heatmaplocations.get(0),compareString,heatmaplocations.size())));
        //----------------------------------------------------------------------
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        WeightedLatLng HeatCord;
        System.out.println( heatmaplocations.size()+"kkkk");
        for (int i = 0; i < heatmaplocations.size()- 1; i++) {
            System.out.println(compareString+ "   "+heatmaplocations.get(i).date+"    sdfsdfsdfsdfsdfds");

            if(heatmaplocations.get(i).date.equals(compareString)){
                System.out.println("Inne i for if");
                list.add(new WeightedLatLng(new LatLng(Double.valueOf(heatmaplocations.get(i).getLatitude()),Double.valueOf(heatmaplocations.get(i).getLongitude())),
                                            GetLocationWeight(heatmaplocations.get(i),compareString,heatmaplocations.size())));

            }
        }
        if(!list.isEmpty()) {
            dateDisplayheatmap.setText(date);
            return list;
        }
        else{
            dateDisplayheatmap.setText(date+"\n" + getString(R.string.no_reports));

            return listReserve;
        }


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

    public void setup(ArrayList<Location> heatlocations){
        heatLocations=heatlocations;



        dateDisplayheatmap = (TextView) view.findViewById(R.id.dateDisplayheatmap);
        Date today = new Date();
        dateDisplayheatmap.setText(convertDateToString(today));
        mProvider = new HeatmapTileProvider.Builder()
                .weightedData((GenerateHeatMapCordsList( heatLocations,convertDateToString(today), "placeholder")))
                .build();
        mOverlay = mGoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));



    }

}


