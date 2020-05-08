package com.example.coronakanditaten2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.example.coronakanditaten2020.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportLocationFragment extends Fragment  implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {
    private static final String TAG = "Fragment Statistics";
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
    private String yourLocation1String = "", yourLocation2String = "";
    private String yourLocation3String = "";
    private String yourLocation4String = "";
    private String yourLocation5String = "";
    private String yourLocation6String = "";
    private String yourLocation7String = "";
    private String yourLocation8String = "";
    private String yourLocation9String = "";
    private String yourLocation10String = "";
    private String yourLocation11String = "";
    private String yourLocation12String = "";
    ArrayList<String> YourlocationsStrings=new ArrayList<String>();

    private Bundle savedInstance;

    private Button btnRlToStart;
    private Button btnRlToRs;
    private Button btnUpdateMyLocations;
    private Button btnAddLocation;

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

    private Boolean location1Decided = false, location2Decided = false, location3Decided = false, location4Decided = false, location5Decided = false, location6Decided = false,
            location7Decided = false, location8Decided = false, location9Decided = false, location10Decided = false, location11Decided = false, location12Decided = false;
    private Boolean location1Visible = false, location2Visible = false, location3Visible = false, location4Visible = false, location5Visible = false, location6Visible = false,
            location7Visible = false, location8Visible = false, location9Visible = false, location10Visible = false, location11Visible = false, location12Visible = false;
    ArrayList<Boolean>AlllocationDecided =new ArrayList<Boolean>();
    ArrayList<Boolean>AlllocationVisible =new ArrayList<Boolean>();

    private int currentLocationReport = 0;
    private LatLng yourLocation = new LatLng(59.8, 17.63);
    private String yourLocationString;
    ArrayList<Location> userLocations;

    private List<Calendar> location1Dates=new ArrayList<Calendar>(), location2Dates=new ArrayList<Calendar>(), location3Dates=new ArrayList<Calendar>(),location4Dates=new ArrayList<Calendar>(), location5Dates=new ArrayList<Calendar>(), location6Dates=new ArrayList<Calendar>(),location7Dates=new ArrayList<Calendar>(),
            location8Dates=new ArrayList<Calendar>(), location9Dates=new ArrayList<Calendar>(),location10Dates=new ArrayList<Calendar>(), location11Dates=new ArrayList<Calendar>(), location12Dates=new ArrayList<Calendar>();
    private String location1DateString = "", location2DateString = "", location3DateString = "",location4DateString = "", location5DateString = "", location6DateString = "",
            location7DateString = "", location8DateString = "", location9DateString = "",location10DateString = "", location11DateString = "", location12DateString = "";
    ArrayList<String> locationDateStrings=new  ArrayList<String>();
    private LatLng location1 = null, location2 = null, location3 = null,location4 = null, location5 = null, location6 = null,
            location7 = null, location8 = null, location9 = null,location10 = null, location11 = null, location12 = null;
    private ArrayList<LatLng> locations;
    ArrayList<List<Calendar>> AllLocationDates=new ArrayList<List<Calendar>>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        containerThis = container;
        savedInstance = savedInstanceState;
        View view = inflater.inflate(R.layout.fragment_report_location, container, false);
        btnRlToStart = (Button) view.findViewById(R.id.btnRlToStart);
        btnRlToStart.setOnClickListener(this);
        btnRlToRs = (Button) view.findViewById(R.id.btnRlToRs);
        btnRlToRs.setOnClickListener(this);
        btnUpdateMyLocations = (Button) view.findViewById(R.id.btnUpdateMyLocations);
        btnUpdateMyLocations.setOnClickListener(this);
        btnAddLocation = (Button) view.findViewById(R.id.btnAddLocation);
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
        hideAllButFirstLocationFragment(getView());
        Creatlists();
        if(userLocations!=null){
            SetUppPage();
        }


        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRlToRs:
                ((MainActivity) getActivity()).setViewPager(4);
                break;
            case R.id.btnRlToStart:
                ((MainActivity) getActivity()).setViewPager(1);
                break;
            case R.id.btnAddLocation:
                showNextLocationFragment(currentLocationReport, getView());
                break;
            case R.id.btnUpdateMyLocations:

                ArrayList<Location> userLocations=creatuserlocations(((MainActivity)getActivity()).reportSymptomsFragment.getratings());

                if(userLocations.size()==0){
                    Toast.makeText(getContext(), "no locations to add", Toast.LENGTH_LONG).show();
                    System.out.println("sadsa");
                    break;
                }
                Call<Boolean> creatuserlocations=((MainActivity) getActivity()).datahandler.clientAPI.createuserlocations(
                        ((MainActivity) getActivity()).datahandler.credentials.encrypt,((MainActivity) getActivity()).datahandler.credentials.Email,userLocations);
                creatuserlocations.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        System.out.println("tja");
                        if(!response.isSuccessful()){
                            System.out.println(userLocations.get(0).latitude+"latitude");System.out.println(userLocations.get(0).longitude+"long");
                            Toast.makeText(getContext(), "failed to add user try again later", Toast.LENGTH_LONG).show();
                            System.out.println("tja");
                        }
                        else{
                            Toast.makeText(getContext(), "sucesfully added user", Toast.LENGTH_LONG).show();
                            System.out.println("yja");
                            ((MainActivity) getActivity()).setViewPager(1);

                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        System.out.println(t);
                        //Toast.makeText(getContext(), "failed to connect to server", Toast.LENGTH_LONG).show();

                    }
                });


                break;
            case R.id.setLocation1:
                currentLocationReport = 1;
                startReportLocationMap();
                break;
            case R.id.setLocation2:
                currentLocationReport = 2;
                startReportLocationMap();
                break;
            case R.id.setLocation3:
                currentLocationReport = 3;
                startReportLocationMap();
                break;
            case R.id.setLocation4:
                currentLocationReport = 4;
                startReportLocationMap();
                break;
            case R.id.setLocation5:
                currentLocationReport = 5;
                startReportLocationMap();
                break;
            case R.id.setLocation6:
                currentLocationReport = 6;
                startReportLocationMap();
                break;
            case R.id.setLocation7:
                currentLocationReport = 7;
                startReportLocationMap();
                break;
            case R.id.setLocation8:
                currentLocationReport = 8;
                startReportLocationMap();
                break;
            case R.id.setLocation9:
                currentLocationReport = 9;
                startReportLocationMap();
                break;
            case R.id.setLocation10:
                currentLocationReport = 10;
                startReportLocationMap();
                break;
            case R.id.setLocation11:
                currentLocationReport = 11;
                startReportLocationMap();
                break;
            case R.id.setLocation12:
                currentLocationReport = 12;
                startReportLocationMap();
                break;
            case R.id.setCalendarLocation1:
                try {
                    getCalendarView(1);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation2:
                try {
                    getCalendarView(2);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation3:
                try {
                    getCalendarView(3);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation4:
                try {
                    getCalendarView(4);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation5:
                try {
                    getCalendarView(5);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation6:
                try {
                    getCalendarView(6);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation7:
                try {
                    getCalendarView(7);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation8:
                try {
                    getCalendarView(8);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation9:
                try {
                    getCalendarView(9);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation10:
                try {
                    getCalendarView(10);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation11:
                try {
                    getCalendarView(11);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setCalendarLocation12:
                try {
                    getCalendarView(12);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void addLocationToLocations(LatLng location){
        locations.add(location);
    }

    public void setLocationInLocations(int placeInList, LatLng location){
        locations.set(placeInList-1, location);
    }

    public void startReportLocationMap(){
        yourLocation = ((MainActivity) getActivity()).getCurrentLocation();
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
        reportedLocation = googleMap.addMarker(new MarkerOptions()
                .position(yourLocation)
                .title("Your Location")
                .draggable(true));
        googleMap.setOnMapClickListener(this);

        switch (currentLocationReport) {
            case 1:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);

                if (locations.get(0) != null) {
                    reportedLocation.setPosition(locations.get(0));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations.get(0), 15));
                }
                break;
            case 2:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location2 != null) {
                    reportedLocation.setPosition(location2);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 15));
                }
                break;
            case 3:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location3 != null) {
                    reportedLocation.setPosition(location3);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location3, 15));
                }
                break;
            case 4:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location4 != null) {
                    reportedLocation.setPosition(location4);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location4, 15));
                }
                break;
            case 5:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location5 != null) {
                    reportedLocation.setPosition(location5);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location5, 15));
                }
                break;
            case 6:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location6 != null) {
                    reportedLocation.setPosition(location6);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location6, 15));
                }
                break;
            case 7:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location7 != null) {
                    reportedLocation.setPosition(location7);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location7, 15));
                }
                break;
            case 8:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location8 != null) {
                    reportedLocation.setPosition(location8);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location8, 15));
                }
                break;
            case 9:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location9 != null) {
                    reportedLocation.setPosition(location9);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location9, 15));
                }
                break;
            case 10:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location10 != null) {
                    reportedLocation.setPosition(location10);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location10, 15));
                }
                break;
            case 11:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location11 != null) {
                    reportedLocation.setPosition(location11);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location11, 15));
                }
                break;
            case 12:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
                reportedLocation.setPosition(yourLocation);
                if (location12 != null) {
                    reportedLocation.setPosition(location12);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location12, 15));
                }
                break;
            default:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15));
        }
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
                        Toast.makeText(getContext(),"Selected Dates\n" + tempLocationString, Toast.LENGTH_LONG).show();
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
        location1Visible = true;

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

    public void showNextLocationFragment(int currentLocationReport, View v){
        Boolean hasEmptyLocation = false;
        System.out.println("3232");
        System.out.println(currentLocationReport+"cyrr");
        if (currentLocationReport==0){
            Toast.makeText(getContext(),"Fill in Location above before adding another one", Toast.LENGTH_LONG).show();

        }
        if(currentLocationReport==12){
            Toast.makeText(getContext(),"Maximum number of locations added", Toast.LENGTH_LONG).show();
        }
        if(currentLocationReport>0&&currentLocationReport<12){
            if(AlllocationVisible.get(currentLocationReport)) {
                hasEmptyLocation = true;
            }
            System.out.println(numberOfLocationsSet()+"nummber of locations set");
            if(numberOfLocationsSet() == currentLocationReport && !AlllocationDecided.get(currentLocationReport)) {
                textViewLocations.get(currentLocationReport).setVisibility(v.VISIBLE);
                setlocations.get(currentLocationReport).setVisibility(v.VISIBLE);
                setCalandarlocations.get(currentLocationReport).setVisibility(v.VISIBLE);
                btnRemoveLocations.get(currentLocationReport).setVisibility(v.VISIBLE);
                AlllocationVisible.set(currentLocationReport,true);
            }

        }

        if (hasEmptyLocation) {
            Toast.makeText(getContext(), "Choose location and date for previous empty location first", Toast.LENGTH_LONG).show();
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
                    textViewLocation1.setText(YourlocationsStrings.get(location-1));
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
        System.out.println(locations.size()+"jhkhjkjh");
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
        ArrayList<Location> uniclocations=getunicLocation(userLocations);
        int Counter1=0;
        int Counter2=0;

        LatLng templatlong;
        Calendar tempCalender=Calendar.getInstance(TimeZone.getDefault());
        String[] theday;
        Geocoder geocoder = new Geocoder(getContext(), getResources().getConfiguration().locale);;
        List<Address> addresses;

        for(Location Alocation:uniclocations){
            System.out.println(Double.valueOf(Alocation.latitude));
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
                System.out.println("tja");

                if(Alocation.longitude.equals(datlocation.longitude)&& Alocation.latitude.equals(datlocation.latitude)){
                    theday=datlocation.getDate().split("-");
                    System.out.println(theday[0]);
                    System.out.println(theday[1]);
                    System.out.println(theday[2]);
                    tempCalender.set((int)Integer.parseInt(theday[2]),(int)Integer.parseInt(theday[1])+1,(int)Integer.parseInt(theday[0]));

                    AllLocationDates.get(Counter1).add(Calendar.getInstance(TimeZone.getDefault()));
                    AllLocationDates.get(Counter1).get(Counter2).set((int)Integer.parseInt(theday[2]),(int)Integer.parseInt(theday[1])+1,(int)Integer.parseInt(theday[0]));
                    Counter2++;

                }

            }

            System.out.println(templatlong.latitude);
            textViewLocations.get(Counter1).setVisibility(getView().VISIBLE);
            setCalandarlocations.get(Counter1).setVisibility(getView().VISIBLE);
            setlocations.get(Counter1).setVisibility(getView().VISIBLE);
            btnRemoveLocations.get(Counter1).setVisibility(getView().VISIBLE);
            AlllocationDecided.set(Counter1,true);
            AlllocationVisible.set(Counter1,true);
            Counter1++;
            Counter2=0;

        }



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

        AlllocationDecided.add(location1Decided);
        AlllocationDecided.add(location2Decided);
        AlllocationDecided.add(location3Decided);
        AlllocationDecided.add(location4Decided);
        AlllocationDecided.add(location5Decided);
        AlllocationDecided.add(location6Decided);
        AlllocationDecided.add(location7Decided);
        AlllocationDecided.add(location8Decided);
        AlllocationDecided.add(location9Decided);
        AlllocationDecided.add(location10Decided);
        AlllocationDecided.add(location11Decided);
        AlllocationDecided.add(location12Decided);


        AlllocationVisible.add(location1Visible);
        AlllocationVisible.add(location2Visible);
        AlllocationVisible.add(location3Visible);
        AlllocationVisible.add(location4Visible);
        AlllocationVisible.add(location5Visible);
        AlllocationVisible.add(location6Visible);
        AlllocationVisible.add(location7Visible);
        AlllocationVisible.add(location8Visible);
        AlllocationVisible.add(location9Visible);
        AlllocationVisible.add(location10Visible);
        AlllocationVisible.add(location11Visible);
        AlllocationVisible.add(location12Visible);

        locationDateStrings.add(location1DateString);
        locationDateStrings.add(location2DateString);
        locationDateStrings.add(location3DateString);
        locationDateStrings.add(location4DateString);
        locationDateStrings.add(location5DateString);
        locationDateStrings.add(location6DateString);
        locationDateStrings.add(location7DateString);
        locationDateStrings.add(location8DateString);
        locationDateStrings.add(location9DateString);
        locationDateStrings.add(location10DateString);
        locationDateStrings.add(location11DateString);
        locationDateStrings.add(location12DateString);

        locations.add(location1);
        locations.add(location2);
        locations.add(location3);
        locations.add(location4);
        locations.add(location5);
        locations.add(location6);
        locations.add(location7);
        locations.add(location8);
        locations.add(location9);
        locations.add(location10);
        locations.add(location11);
        locations.add(location12);

        AllLocationDates.add(location1Dates);
        AllLocationDates.add(location2Dates);
        AllLocationDates.add(location3Dates);
        AllLocationDates.add(location4Dates);
        AllLocationDates.add(location5Dates);
        AllLocationDates.add(location6Dates);
        AllLocationDates.add(location7Dates);
        AllLocationDates.add(location8Dates);
        AllLocationDates.add(location9Dates);
        AllLocationDates.add(location10Dates);
        AllLocationDates.add(location11Dates);
        AllLocationDates.add(location12Dates);

        YourlocationsStrings.add(yourLocation1String);
        YourlocationsStrings.add(yourLocation2String);
        YourlocationsStrings.add(yourLocation3String);
        YourlocationsStrings.add(yourLocation4String);
        YourlocationsStrings.add(yourLocation5String);
        YourlocationsStrings.add(yourLocation6String);
        YourlocationsStrings.add(yourLocation7String);
        YourlocationsStrings.add(yourLocation8String);
        YourlocationsStrings.add(yourLocation9String);
        YourlocationsStrings.add(yourLocation10String);
        YourlocationsStrings.add(yourLocation11String);
        YourlocationsStrings.add(yourLocation12String);
    }



}

