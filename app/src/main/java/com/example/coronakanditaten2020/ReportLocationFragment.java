package com.example.coronakanditaten2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.example.coronakanditaten2020.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class ReportLocationFragment extends Fragment  implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {
    private static final String TAG = "Fragment Statistics";
    private static final int RESULT_OK = 2;
    private static final int RESULT_CANCELED = 3;
    private ViewGroup containerThis;
    private com.applandeo.materialcalendarview.CalendarView cal;
    private Calendar minDate = Calendar.getInstance(TimeZone.getDefault());
    private Calendar maxDate = Calendar.getInstance(TimeZone.getDefault());
    private long dateNumberOfDaysAgo = 1209600000;                              //get 14 days im ms, 14 days = 14 * 24* 60 * 60 * 1000 = 1209600000 ms
    private long minDateTime = minDate.getTimeInMillis()-dateNumberOfDaysAgo;   //get date 14 days ago (today - 14 days in ms)

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    public PopupWindow mapWindow;

    private Marker reportedLocation;

    ArrayList<String> YourlocationsStrings=new ArrayList<String>();


    private Bundle savedInstance;

    private Button btnRlToRs;
    private Button btnUpdateMyLocations;
    private ImageView btnAddLocation;


    private ImageButton setLocation1,setLocation2, setLocation3, setLocation4, setLocation5, setLocation6,
            setLocation7, setLocation8, setLocation9, setLocation10, setLocation11, setLocation12;
    ArrayList<ImageButton> setlocations=new ArrayList<ImageButton>();
    private ImageButton setCalendarLocation1, setCalendarLocation2, setCalendarLocation3, setCalendarLocation4, setCalendarLocation5, setCalendarLocation6,
            setCalendarLocation7, setCalendarLocation8, setCalendarLocation9, setCalendarLocation10, setCalendarLocation11, setCalendarLocation12;
    ArrayList<ImageButton> setCalandarlocations=new ArrayList<ImageButton>();
    private ImageButton btnRemoveLocation1, btnRemoveLocation2, btnRemoveLocation3, btnRemoveLocation4, btnRemoveLocation5, btnRemoveLocation6,
            btnRemoveLocation7, btnRemoveLocation8, btnRemoveLocation9, btnRemoveLocation10, btnRemoveLocation11, btnRemoveLocation12;
    ArrayList<ImageButton> btnRemoveLocations=new ArrayList<ImageButton>();
    private TextView textViewLocation1, textViewLocation2, textViewLocation3, textViewLocation4, textViewLocation5, textViewLocation6,
            textViewLocation7, textViewLocation8, textViewLocation9,textViewLocation10, textViewLocation11, textViewLocation12;
    ArrayList<TextView> textViewLocations=new ArrayList<TextView>();


    ArrayList<Boolean>AlllocationDecided =new ArrayList<Boolean>();
    ArrayList<Boolean>AlllocationVisible =new ArrayList<Boolean>();

    private int currentLocationReport = 0;
    private LatLng yourLocation = new LatLng(59.8, 17.63);
    private String yourLocationString;
    ArrayList<Location> userLocations;
    ArrayList<Location> postlocations;
    ArrayList<Location> deletelocations;
    ArrayList<Location> putlocations;
    private final int Maxlocations=12;


    ArrayList<String> locationDateStrings=new  ArrayList<String>();

    private ArrayList<LatLng> locations;
    ArrayList<List<Calendar>> AllLocationDates=new ArrayList<List<Calendar>>();

    int AUTOCOMPLETE_REQUEST_CODE = 1;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        containerThis = container;
        savedInstance = savedInstanceState;
        View view = inflater.inflate(R.layout.fragment_report_location, container, false);

        btnUpdateMyLocations = (Button) view.findViewById(R.id.btnUpdateMyLocations);
        btnUpdateMyLocations.setOnClickListener(this);
        btnAddLocation = (ImageView) view.findViewById(R.id.btnAddLocation);
        btnAddLocation.setOnClickListener(this);

        locations = new ArrayList<LatLng>();
        if(((MainActivity)getActivity()).datahandler.Userlocations!=null){
            userLocations=((MainActivity)getActivity()).datahandler.Userlocations;
        }

        // SET ALL LOCATION MAP IMAGE BUTTONS
        setLocation1 = (ImageButton) view.findViewById(R.id.setLocation1);
        setLocation1.setOnClickListener(this);
        setLocation2 = (ImageButton) view.findViewById(R.id.setLocation2);
        setLocation2.setOnClickListener(this);
        setLocation3 = (ImageButton) view.findViewById(R.id.setLocation3);
        setLocation3.setOnClickListener(this);
        setLocation4 = (ImageButton) view.findViewById(R.id.setLocation4);
        setLocation4.setOnClickListener(this);
        setLocation5 = (ImageButton) view.findViewById(R.id.setLocation5);
        setLocation5.setOnClickListener(this);
        setLocation6 = (ImageButton) view.findViewById(R.id.setLocation6);
        setLocation6.setOnClickListener(this);
        setLocation7 = (ImageButton) view.findViewById(R.id.setLocation7);
        setLocation7.setOnClickListener(this);
        setLocation8 = (ImageButton) view.findViewById(R.id.setLocation8);
        setLocation8.setOnClickListener(this);
        setLocation9 = (ImageButton) view.findViewById(R.id.setLocation9);
        setLocation9.setOnClickListener(this);
        setLocation10 = (ImageButton) view.findViewById(R.id.setLocation10);
        setLocation10.setOnClickListener(this);
        setLocation11 = (ImageButton) view.findViewById(R.id.setLocation11);
        setLocation11.setOnClickListener(this);
        setLocation12 = (ImageButton) view.findViewById(R.id.setLocation12);
        setLocation12.setOnClickListener(this);

        //SET ALL CALENDAR LOCATION IMAGE BUTTONS
        setCalendarLocation1 = (ImageButton) view.findViewById(R.id.setCalendarLocation1);
        setCalendarLocation1.setOnClickListener(this);
        setCalendarLocation2 = (ImageButton) view.findViewById(R.id.setCalendarLocation2);
        setCalendarLocation2.setOnClickListener(this);
        setCalendarLocation3 = (ImageButton) view.findViewById(R.id.setCalendarLocation3);
        setCalendarLocation3.setOnClickListener(this);
        setCalendarLocation4 = (ImageButton) view.findViewById(R.id.setCalendarLocation4);
        setCalendarLocation4.setOnClickListener(this);
        setCalendarLocation5 = (ImageButton) view.findViewById(R.id.setCalendarLocation5);
        setCalendarLocation5.setOnClickListener(this);
        setCalendarLocation6 = (ImageButton) view.findViewById(R.id.setCalendarLocation6);
        setCalendarLocation6.setOnClickListener(this);
        setCalendarLocation7 = (ImageButton) view.findViewById(R.id.setCalendarLocation7);
        setCalendarLocation7.setOnClickListener(this);
        setCalendarLocation8 = (ImageButton) view.findViewById(R.id.setCalendarLocation8);
        setCalendarLocation8.setOnClickListener(this);
        setCalendarLocation9 = (ImageButton) view.findViewById(R.id.setCalendarLocation9);
        setCalendarLocation9.setOnClickListener(this);
        setCalendarLocation10 = (ImageButton) view.findViewById(R.id.setCalendarLocation10);
        setCalendarLocation10.setOnClickListener(this);
        setCalendarLocation11 = (ImageButton) view.findViewById(R.id.setCalendarLocation11);
        setCalendarLocation11.setOnClickListener(this);
        setCalendarLocation12 = (ImageButton) view.findViewById(R.id.setCalendarLocation12);
        setCalendarLocation12.setOnClickListener(this);

        // SET ALL REMOVE LOCATION BUTTONS
        btnRemoveLocation1 = (ImageButton) view.findViewById(R.id.btnRemoveLocation1);
        btnRemoveLocation1.setOnClickListener(this);
        btnRemoveLocation2 = (ImageButton) view.findViewById(R.id.btnRemoveLocation2);
        btnRemoveLocation2.setOnClickListener(this);
        btnRemoveLocation3 = (ImageButton) view.findViewById(R.id.btnRemoveLocation3);
        btnRemoveLocation3.setOnClickListener(this);
        btnRemoveLocation4 = (ImageButton) view.findViewById(R.id.btnRemoveLocation4);
        btnRemoveLocation4.setOnClickListener(this);
        btnRemoveLocation5 = (ImageButton) view.findViewById(R.id.btnRemoveLocation5);
        btnRemoveLocation5.setOnClickListener(this);
        btnRemoveLocation6 = (ImageButton) view.findViewById(R.id.btnRemoveLocation6);
        btnRemoveLocation6.setOnClickListener(this);
        btnRemoveLocation7 = (ImageButton) view.findViewById(R.id.btnRemoveLocation7);
        btnRemoveLocation7.setOnClickListener(this);
        btnRemoveLocation8 = (ImageButton) view.findViewById(R.id.btnRemoveLocation8);
        btnRemoveLocation8.setOnClickListener(this);
        btnRemoveLocation9 = (ImageButton) view.findViewById(R.id.btnRemoveLocation9);
        btnRemoveLocation9.setOnClickListener(this);
        btnRemoveLocation10 = (ImageButton) view.findViewById(R.id.btnRemoveLocation10);
        btnRemoveLocation10.setOnClickListener(this);
        btnRemoveLocation11 = (ImageButton) view.findViewById(R.id.btnRemoveLocation11);
        btnRemoveLocation11.setOnClickListener(this);
        btnRemoveLocation12 = (ImageButton) view.findViewById(R.id.btnRemoveLocation12);
        btnRemoveLocation12.setOnClickListener(this);

        // SET ALL TEXT VIEWS FOR LOCATIONS
        textViewLocation1 = (TextView) view.findViewById(R.id.textViewLocation1);
        textViewLocation2 = (TextView) view.findViewById(R.id.textViewLocation2);
        textViewLocation3 = (TextView) view.findViewById(R.id.textViewLocation3);
        textViewLocation4 = (TextView) view.findViewById(R.id.textViewLocation4);
        textViewLocation5 = (TextView) view.findViewById(R.id.textViewLocation5);
        textViewLocation6 = (TextView) view.findViewById(R.id.textViewLocation6);
        textViewLocation7 = (TextView) view.findViewById(R.id.textViewLocation7);
        textViewLocation8 = (TextView) view.findViewById(R.id.textViewLocation8);
        textViewLocation9 = (TextView) view.findViewById(R.id.textViewLocation9);
        textViewLocation10 = (TextView) view.findViewById(R.id.textViewLocation10);
        textViewLocation11 = (TextView) view.findViewById(R.id.textViewLocation11);
        textViewLocation12 = (TextView) view.findViewById(R.id.textViewLocation12);
        // SET ALL SCROLL'S FOR THE TEXT VIEWS
        textViewLocation1.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation2.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation3.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation4.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation5.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation6.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation7.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation8.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation9.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation10.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation11.setMovementMethod(new ScrollingMovementMethod());
        textViewLocation12.setMovementMethod(new ScrollingMovementMethod());



        minDate.setTimeInMillis(minDateTime);
        Creatlists();
        hideAllButFirstLocationFragment(getView());

        if(userLocations!=null){
            SetUppPage();
        }

        if (!Places.isInitialized()) {
            Places.initialize(getContext(), "AIzaSyAdNZnteknM0VlU416q-b8ZEqRBjiFOiPA");
        }

        return view;
    }

    public void setReportLocationBottomNav(){
        ((MainActivity) requireActivity()).bottomNav = (BottomNavigationView) getView().findViewById(R.id.bottom_navigation);
        ((MainActivity) requireActivity()).bottomNav.setOnNavigationItemSelectedListener(((MainActivity) getActivity()).navListener);
        ((MainActivity) requireActivity()).bottomNav.getMenu().findItem(R.id.nav_report_symptoms).setChecked(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAddLocation:
                showNextLocationFragment(currentLocationReport, getView());
                break;
            case R.id.btnUpdateMyLocations:

                userLocations = creatuserlocations(((MainActivity) getActivity()).reportSymptomsFragment.getratings());

                if (userLocations.size() == 0) {
                    Toast.makeText(getContext(), "no locations to add", Toast.LENGTH_LONG).show();
                    break;
                }


                Call<Boolean> removelocations = ((MainActivity) getActivity()).datahandler.clientAPI.removeUserlocations(
                        ((MainActivity) getActivity()).datahandler.credentials.encrypt, ((MainActivity) getActivity()).datahandler.credentials.Email);
                Call<Boolean> creatuserlocations = ((MainActivity) getActivity()).datahandler.clientAPI.createuserlocations(
                        ((MainActivity) getActivity()).datahandler.credentials.encrypt, ((MainActivity) getActivity()).datahandler.credentials.Email, userLocations);

                if (((MainActivity) getActivity()).datahandler.Userlocations.size()==0) {

                    creatuserlocations.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getContext(), getString(R.string.fail_add_user_locations), Toast.LENGTH_LONG).show();
                                System.out.println(response.toString());
                            } else {
                                Toast.makeText(getContext(), getString(R.string.success_add_user_locations), Toast.LENGTH_LONG).show();
                                ((MainActivity) getActivity()).datahandler.Userlocations = userLocations;
                                ((MainActivity) getActivity()).setViewPager(1);


                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            System.out.println(t);
                            Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

                        }
                    });


                } else {
                    removelocations.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (!response.isSuccessful() || !response.body()) {
                                Toast.makeText(getContext(), getString(R.string.fail_add_user_locations), Toast.LENGTH_LONG).show();
                                System.out.println(response.toString());
                                System.out.println("lllllllll");

                            } else {

                                creatuserlocations.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                        if (!response.isSuccessful()) {
                                            Toast.makeText(getContext(), getString(R.string.fail_add_user_locations), Toast.LENGTH_LONG).show();
                                            System.out.println(response.toString());
                                        } else {
                                            Toast.makeText(getContext(), getString(R.string.success_add_user_locations), Toast.LENGTH_LONG).show();

                                            System.out.println("sdfdsfds");
                                            System.out.println(AlllocationDecided.get(2));
                                            System.out.println(AlllocationVisible.get(2));
                                            System.out.println(textViewLocation2.getText());
                                            System.out.println(textViewLocation2.getVisibility());
                                            ((MainActivity) getActivity()).datahandler.Userlocations = userLocations;
                                            ((MainActivity) getActivity()).setViewPager(1);
                                            System.out.println();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        System.out.println(t);
                                        Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

                                    }
                                });


                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            System.out.println(t);
                            Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

                        }
                    });
                }
        }

            for(int i=0 ; i<setlocations.size() ;  i++ ){
                System.out.println("setlcoatioN"+setlocations.get(i).getId()+"button"+ v.getId());

                if(setlocations.get(i).getId()==v.getId()){

                    currentLocationReport=i+1;
                    startReportLocationMap();
                    break;
                }
            }

            for(int i=0 ; i<setCalandarlocations.size() ;  i++){

                if(setCalandarlocations.get(i).getId()==v.getId()){
                    try {
                        getCalendarView(i+1);
                    } catch (OutOfDateRangeException e) {
                        e.printStackTrace();
                    }

                    break;
                }

            }

            for (int i=0; i<btnRemoveLocations.size();i++){
                if(btnRemoveLocations.get(i).getId()==v.getId()){
                    currentLocationReport=i+1;
                    removelocation();
                    break;
                }
            }







    }

    void removelocation(){
        int decided=Collections.frequency(AlllocationDecided,true)-1;

        System.out.println("gdgdsfdfdfszsjhdsf");


        if(decided==0 && Collections.frequency(AlllocationVisible,true)-1<=1){
            System.out.println();
            textViewLocations.get(decided).setText("location "+(decided+1));
            AlllocationDecided.set(decided,false);
            locations.remove(currentLocationReport-1);
            locations.add(null);
            YourlocationsStrings.remove(currentLocationReport-1);
            YourlocationsStrings.add("");
            locationDateStrings.remove(currentLocationReport-1);
            locationDateStrings.add(null);
            AllLocationDates.remove(currentLocationReport-1);
            locationDateStrings.add("");
            return;}

        if(decided==0 && Collections.frequency(AlllocationVisible,true)-1<=0){
            System.out.println();
            System.out.println("gdgdsfgdsfgdsfgdsf");
            textViewLocations.get(decided).setText("location "+(decided+1));
            AlllocationDecided.set(decided,false);
            locations.remove(currentLocationReport-1);
            locations.add(null);
            YourlocationsStrings.remove(currentLocationReport-1);
            YourlocationsStrings.add("");
            locationDateStrings.remove(currentLocationReport-1);
            locationDateStrings.add(null);
            AllLocationDates.remove(currentLocationReport-1);
            locationDateStrings.add("");
            return;

        }
        if(decided==-1 && Collections.frequency(AlllocationVisible,true)-1<=0){
            System.out.println("gdgdsfdfdfszsjhdsf");
            textViewLocations.get(decided+1).setText("location "+(decided+2));
            AlllocationDecided.set(decided+1,false);
            locations.remove(currentLocationReport-1);
            locations.add(null);
            YourlocationsStrings.remove(currentLocationReport-1);
            YourlocationsStrings.add("");
            locationDateStrings.remove(currentLocationReport-1);
            locationDateStrings.add(null);
            AllLocationDates.remove(currentLocationReport-1);
            locationDateStrings.add("");
            return;

        }
        textViewLocations.get(decided).setVisibility(getView().GONE);
        setlocations.get(decided).setVisibility(getView().GONE);
        setCalandarlocations.get(decided).setVisibility(getView().GONE);
        btnRemoveLocations.get(decided).setVisibility(getView().GONE);
        AlllocationVisible.set(decided,false);
        AlllocationDecided.set(decided,false);
        textViewLocations.get(decided).setText("location "+(decided+1));


        locations.remove(currentLocationReport-1);
        locations.add(null);
        YourlocationsStrings.remove(currentLocationReport-1);
        YourlocationsStrings.add("");
        locationDateStrings.remove(currentLocationReport-1);
        locationDateStrings.add(null);
        AllLocationDates.remove(currentLocationReport-1);
        locationDateStrings.add("");
        for(int i=0; i<decided; i++){
            textViewLocations.get(i).setText(YourlocationsStrings.get(i));
        }




    }

    public void addLocationToLocations(LatLng location){
        locations.add(location);
    }

    public void setLocationInLocations(int placeInList, LatLng location){
        locations.set(placeInList-1, location);
    }

    public void startReportLocationMap(){
        System.out.println("fdsfsdfdsf");
        if(((MainActivity) requireActivity()).getCurrentLocation() != null){
            yourLocation = ((MainActivity) requireActivity()).getCurrentLocation();
        }

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mapV = inflater.inflate(R.layout.report_location_map, null);

        mMapView = (MapView) mapV.findViewById(R.id.mapViewReport);
        mMapView.onCreate(savedInstance);




        mMapView.getMapAsync(this);
        mapWindow = new PopupWindow(mapV, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mapWindow.showAtLocation(mapV, Gravity.CENTER, 0, 0);

        mapWindow.setOutsideTouchable(true);
        mapWindow.setFocusable(true);
        mapWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mMapView.onResume();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);



        if (locations.get(currentLocationReport-1)!= null) {
            reportedLocation = googleMap.addMarker(new MarkerOptions()
                    .position(locations.get(currentLocationReport-1))
                    .title(YourlocationsStrings.get(currentLocationReport-1))
                    .draggable(true));
            googleMap.setOnMapClickListener(this);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations.get(currentLocationReport-1), 15));
            return;
        }

        reportedLocation = googleMap.addMarker(new MarkerOptions()
                .position(yourLocation)
                .title(getString(R.string.your_location))
                .draggable(true));
        googleMap.setOnMapClickListener(this);
        System.out.println(locations.get(currentLocationReport));

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
        reportedLocation.setPosition(yourLocation);
    }



    public void getCalendarView(final Integer location) throws OutOfDateRangeException {
        OnSelectDateListener listener = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                String tempLocationString = "";

                for (Calendar day: calendars){
                    tempLocationString += day.get(Calendar.DAY_OF_MONTH)+"-"+(day.get(Calendar.MONTH)+1)+"-"+day.get(Calendar.YEAR)+"\n"; // Toast Print
                }
                        AllLocationDates.set(location-1,calendars);
                        locationDateStrings.set(location-1,tempLocationString);
                        Toast.makeText(getContext(),getString(R.string.selected_dates)+ "\n" + tempLocationString, Toast.LENGTH_LONG).show();
            }
        };

        DatePickerBuilder builder = new DatePickerBuilder(getContext(), listener)
                .pickerType(cal.MANY_DAYS_PICKER).setMaximumDate(maxDate).setMinimumDate(minDate);



                    builder.setSelectedDays(AllLocationDates.get(location-1));


        DatePicker datePicker = builder.build();
        datePicker.show();

    }

    public void hideAllButFirstLocationFragment(View v){
        textViewLocation2.setVisibility(v.GONE);
        setLocation2.setVisibility(v.GONE);
        setCalendarLocation2.setVisibility(v.GONE);
        btnRemoveLocation2.setVisibility(v.GONE);
        AlllocationVisible.set(0,true);

        textViewLocation3.setVisibility(v.GONE);
        setLocation3.setVisibility(v.GONE);
        setCalendarLocation3.setVisibility(v.GONE);
        btnRemoveLocation3.setVisibility(v.GONE);

        textViewLocation4.setVisibility(v.GONE);
        setLocation4.setVisibility(v.GONE);
        setCalendarLocation4.setVisibility(v.GONE);
        btnRemoveLocation4.setVisibility(v.GONE);

        textViewLocation5.setVisibility(v.GONE);
        setLocation5.setVisibility(v.GONE);
        setCalendarLocation5.setVisibility(v.GONE);
        btnRemoveLocation5.setVisibility(v.GONE);


        textViewLocation6.setVisibility(v.GONE);
        setLocation6.setVisibility(v.GONE);
        setCalendarLocation6.setVisibility(v.GONE);
        btnRemoveLocation6.setVisibility(v.GONE);

        textViewLocation7.setVisibility(v.GONE);
        setLocation7.setVisibility(v.GONE);
        setCalendarLocation7.setVisibility(v.GONE);
        btnRemoveLocation7.setVisibility(v.GONE);

        textViewLocation8.setVisibility(v.GONE);
        setLocation8.setVisibility(v.GONE);
        setCalendarLocation8.setVisibility(v.GONE);
        btnRemoveLocation8.setVisibility(v.GONE);


        textViewLocation9.setVisibility(v.GONE);
        setLocation9.setVisibility(v.GONE);
        setCalendarLocation9.setVisibility(v.GONE);
        btnRemoveLocation9.setVisibility(v.GONE);

        textViewLocation10.setVisibility(v.GONE);
        setLocation10.setVisibility(v.GONE);
        setCalendarLocation10.setVisibility(v.GONE);
        btnRemoveLocation10.setVisibility(v.GONE);

        textViewLocation11.setVisibility(v.GONE);
        setLocation11.setVisibility(v.GONE);
        setCalendarLocation11.setVisibility(v.GONE);
        btnRemoveLocation11.setVisibility(v.GONE);


        textViewLocation12.setVisibility(v.GONE);
        setLocation12.setVisibility(v.GONE);
        setCalendarLocation12.setVisibility(v.GONE);
        btnRemoveLocation12.setVisibility(v.GONE);
    }

    public int numberOfLocationsSet(){
        return Collections.frequency(AlllocationDecided,true);
    }
    public int numberOfVisibleLocations(){
        return Collections.frequency(AlllocationVisible,true);
    }

    public void showNextLocationFragment(int currentLocationReport, View v){
        Boolean hasEmptyLocation = false;
        int locationsvisible=numberOfVisibleLocations();
        if (currentLocationReport==0){
            Toast.makeText(getContext(),getString(R.string.no_location_added), Toast.LENGTH_LONG).show();
            return;

        }
        if(locationsvisible==Maxlocations){
            Toast.makeText(getContext(),getString(R.string.max_number_of_locations_reached), Toast.LENGTH_LONG).show();
            return;
        }
        if(currentLocationReport>0&&currentLocationReport<Maxlocations){
            if(AlllocationVisible.get(currentLocationReport)) {
                hasEmptyLocation = true;
            }
            System.out.println(numberOfLocationsSet()+"nummber of locations set");
            System.out.println(currentLocationReport + "currentlcoation report");
            System.out.println(!AlllocationDecided.get(currentLocationReport));
            if(numberOfLocationsSet() >=locationsvisible ) {
                textViewLocations.get(locationsvisible).setVisibility(v.VISIBLE);
                setlocations.get(locationsvisible).setVisibility(v.VISIBLE);
                setCalandarlocations.get(locationsvisible).setVisibility(v.VISIBLE);
                btnRemoveLocations.get(locationsvisible).setVisibility(v.VISIBLE);
                AlllocationVisible.set(locationsvisible,true);
            }
            else{
                Toast.makeText(getContext(), getString(R.string.already_empty_location), Toast.LENGTH_LONG).show();
            }

        }



    }

    @Override
    public void onMapClick(LatLng latLng) {
        reportedLocation.setPosition(latLng);
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());;
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    YourlocationsStrings.set(currentLocationReport-1,addresses.get(0).getAddressLine(0));
                    reportedLocation.setTitle(YourlocationsStrings.get(currentLocationReport-1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dismissPopup(Integer location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());;
        List<Address> addresses;
        try {
            System.out.println();
            addresses = geocoder.getFromLocation(yourLocation.latitude, yourLocation.longitude, 1);
            yourLocationString = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
                AlllocationDecided.set(location-1,true);
                textViewLocations.get(location-1).setText(yourLocationString);

                if (YourlocationsStrings.get(location-1) != "") {
                    textViewLocations.get(location-1).setText(YourlocationsStrings.get(location-1));
                }
                System.out.println((AlllocationDecided.get(location-1)));


                    locations.set(location-1,reportedLocation.getPosition());



        cancelMapPopup();
    }

    public void cancelMapPopup(){
        super.onDestroy();
        mMapView.onDestroy();
        mapWindow.dismiss();
    }

    public int getCurrentLocationReport(){
        return currentLocationReport;
    }

    public ArrayList<Location> creatuserlocations(int[] ratings){
        ArrayList<Location> returnlocations= new ArrayList<Location>();
        Date date = new Date();
        Double lat;
        String latstring;
        Double lot;
        String lotstring;
        String tempday;
        int counter=0;
        for (LatLng thelocation:locations){
            if(AllLocationDates.get(counter)!=null && thelocation!=null){
                System.out.println("fdsfsdf");

                for(Calendar day:AllLocationDates.get(counter)){


                    lat=thelocation.latitude;
                    System.out.println(thelocation.latitude+"latidude");
                    latstring=lat.toString();
                    lot=thelocation.longitude;
                    lotstring=lot.toString();
                    tempday=day.get(Calendar.DAY_OF_MONTH)+"-"+(day.get(Calendar.MONTH)-
                            +1)+"-"+day.get(Calendar.YEAR);

                    returnlocations.add(new Location(latstring,lotstring,tempday,
                            ratings[0],ratings[1],ratings[2],ratings[3],ratings[4],ratings[5],ratings[6],ratings[7],ratings[8],date.toString()));

                }}
            counter++;
        }

        return returnlocations;


    }


    void SetUppPage(){
        System.out.println("yjyjyyjyjy");
        System.out.println(userLocations.size());
        ArrayList<Location> uniclocations=getunicLocation(userLocations);
        int Counter1=0;
        int Counter2=0;

        LatLng templatlong;
        Calendar tempCalender=Calendar.getInstance(TimeZone.getDefault());
        String[] theday;
        Geocoder geocoder = new Geocoder(getContext(), getResources().getConfiguration().locale);;
        List<Address> addresses;

        for(Location Alocation:uniclocations){

            templatlong=new LatLng(Double.valueOf(Alocation.latitude), Double.valueOf(Alocation.longitude));
            locations.set(Counter1,templatlong);
            try {
                addresses = geocoder.getFromLocation(templatlong.latitude, templatlong.longitude, 1);
                if(addresses.size()>0){
                textViewLocations.get(Counter1).setText(addresses.get(0).getAddressLine(0));}
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Location datlocation:userLocations){


                if(Alocation.longitude.equals(datlocation.longitude)&& Alocation.latitude.equals(datlocation.latitude)){
                    theday=datlocation.getDate().split("-");

                    tempCalender.set((int)Integer.parseInt(theday[2]),(int)Integer.parseInt(theday[1])+1,(int)Integer.parseInt(theday[0]));

                    AllLocationDates.get(Counter1).add(Calendar.getInstance(TimeZone.getDefault()));
                    AllLocationDates.get(Counter1).get(Counter2).set((int)Integer.parseInt(theday[2]),(int)Integer.parseInt(theday[1])+1,(int)Integer.parseInt(theday[0]));
                    Counter2++;

                }

            }


            textViewLocations.get(Counter1).setVisibility(getView().VISIBLE);
            setCalandarlocations.get(Counter1).setVisibility(getView().VISIBLE);
            setlocations.get(Counter1).setVisibility(getView().VISIBLE);
            btnRemoveLocations.get(Counter1).setVisibility(getView().VISIBLE);
            AlllocationDecided.set(Counter1,true);
            AlllocationVisible.set(Counter1,true);
            Counter1++;
            currentLocationReport++;
            Counter2=0;


        }
        minDateTime=getoldestdate(AllLocationDates)-dateNumberOfDaysAgo;




    }

    public long getoldestdate( ArrayList<List<Calendar>> AllLocationDates){
        long MinDate=Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis();

        for(List<Calendar> Cal:AllLocationDates){
            for( Calendar co:Cal){
                if(co.getTimeInMillis()<MinDate){
                    MinDate=co.getTimeInMillis();
                }

            }
        }

        return MinDate;

    }


    public ArrayList<Location> getunicLocation( ArrayList<Location> thelocations){
        ArrayList<String> uniclocationsString=new ArrayList<String>();
        ArrayList<Location> uniclocations=new ArrayList<Location>();
        String tempString="";
        for(Location thelocation:thelocations){
            tempString=thelocation.latitude+thelocation.longitude;
            if(!uniclocationsString.contains(tempString)){
                uniclocationsString.add(tempString);
                uniclocations.add(thelocation);
            }
        }
        return uniclocations;
    }






    boolean ratingsequel(Location orloc,Location newloc){
        if(orloc.breathingRatingBar==newloc.breathingRatingBar &&
                orloc.coughRatingBar==newloc.coughRatingBar &&
                orloc.diarrheaRatingBar==newloc.diarrheaRatingBar &&
                orloc.breathingRatingBar==newloc.breathingRatingBar &&
                orloc.feverRatingBar==newloc.feverRatingBar &&
                orloc.headacheRatingBar==newloc.headacheRatingBar &&
                orloc.tirednessRatingBar==newloc.tirednessRatingBar&&
                orloc.throatRatingBar==newloc.throatRatingBar &&
                orloc.runnyNoseRatingBar==newloc.runnyNoseRatingBar &&
                orloc.nasalCongestionRatingBar==newloc.nasalCongestionRatingBar){
            return true;
        }
        return false;
    }




    void Creatlists(){
        setlocations.add(setLocation1);
        setlocations.add(setLocation2);
        setlocations.add(setLocation3);
        setlocations.add(setLocation4);
        setlocations.add(setLocation5);
        setlocations.add(setLocation6);
        setlocations.add(setLocation7);
        setlocations.add(setLocation8);
        setlocations.add(setLocation9);
        setlocations.add(setLocation10);
        setlocations.add(setLocation11);
        setlocations.add(setLocation12);

        setCalandarlocations.add(setCalendarLocation1);
        setCalandarlocations.add(setCalendarLocation2);
        setCalandarlocations.add(setCalendarLocation3);
        setCalandarlocations.add(setCalendarLocation4);
        setCalandarlocations.add(setCalendarLocation5);
        setCalandarlocations.add(setCalendarLocation6);
        setCalandarlocations.add(setCalendarLocation7);
        setCalandarlocations.add(setCalendarLocation8);
        setCalandarlocations.add(setCalendarLocation9);
        setCalandarlocations.add(setCalendarLocation10);
        setCalandarlocations.add(setCalendarLocation11);
        setCalandarlocations.add(setCalendarLocation12);

        btnRemoveLocations.add(btnRemoveLocation1);
        btnRemoveLocations.add(btnRemoveLocation2);
        btnRemoveLocations.add(btnRemoveLocation3);
        btnRemoveLocations.add(btnRemoveLocation4);
        btnRemoveLocations.add(btnRemoveLocation5);
        btnRemoveLocations.add(btnRemoveLocation6);
        btnRemoveLocations.add(btnRemoveLocation7);
        btnRemoveLocations.add(btnRemoveLocation8);
        btnRemoveLocations.add(btnRemoveLocation9);
        btnRemoveLocations.add(btnRemoveLocation10);
        btnRemoveLocations.add(btnRemoveLocation11);
        btnRemoveLocations.add(btnRemoveLocation12);


        textViewLocations.add(textViewLocation1);
        textViewLocations.add(textViewLocation2);
        textViewLocations.add(textViewLocation3);
        textViewLocations.add(textViewLocation4);
        textViewLocations.add(textViewLocation5);
        textViewLocations.add(textViewLocation6);
        textViewLocations.add(textViewLocation7);
        textViewLocations.add(textViewLocation8);
        textViewLocations.add(textViewLocation9);
        textViewLocations.add(textViewLocation10);
        textViewLocations.add(textViewLocation11);
        textViewLocations.add(textViewLocation12);

        for(int i=1; i<=Maxlocations;i++){
            AlllocationDecided.add(false);
            AlllocationVisible.add(false);
            locationDateStrings.add("");
            locations.add(null);
            AllLocationDates.add(new ArrayList<Calendar>());
            YourlocationsStrings.add("");
        }


    }

    public void removealllocation(){

        for (int i=1; i<=numberOfLocationsSet();i++){
            currentLocationReport=numberOfLocationsSet();
            removelocation();
        }
        currentLocationReport=0;

    }
}

