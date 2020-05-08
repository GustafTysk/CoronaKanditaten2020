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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

    private Bundle savedInstance;

    private Button btnRlToStart;
    private Button btnRlToRs;
    private Button btnUpdateMyLocations;
    private Button btnAddLocation;

    private ImageButton setLocation1,setLocation2, setLocation3, setLocation4, setLocation5, setLocation6,
            setLocation7, setLocation8, setLocation9, setLocation10, setLocation11, setLocation12;
    private ImageButton setCalendarLocation1, setCalendarLocation2, setCalendarLocation3, setCalendarLocation4, setCalendarLocation5, setCalendarLocation6,
            setCalendarLocation7, setCalendarLocation8, setCalendarLocation9, setCalendarLocation10, setCalendarLocation11, setCalendarLocation12;
    private ImageButton btnRemoveLocation1, btnRemoveLocation2, btnRemoveLocation3, btnRemoveLocation4, btnRemoveLocation5, btnRemoveLocation6,
            btnRemoveLocation7, btnRemoveLocation8, btnRemoveLocation9, btnRemoveLocation10, btnRemoveLocation11, btnRemoveLocation12;
    private TextView textViewLocation1, textViewLocation2, textViewLocation3, textViewLocation4, textViewLocation5, textViewLocation6,
            textViewLocation7, textViewLocation8, textViewLocation9,textViewLocation10, textViewLocation11, textViewLocation12;

    private Boolean location1Decided = false, location2Decided = false, location3Decided = false, location4Decided = false, location5Decided = false, location6Decided = false,
            location7Decided = false, location8Decided = false, location9Decided = false, location10Decided = false, location11Decided = false, location12Decided = false;
    private Boolean location1Visible = false, location2Visible = false, location3Visible = false, location4Visible = false, location5Visible = false, location6Visible = false,
            location7Visible = false, location8Visible = false, location9Visible = false, location10Visible = false, location11Visible = false, location12Visible = false;

    private int currentLocationReport = 0;
    private LatLng yourLocation = new LatLng(59.8, 17.63);
    private String yourLocationString;

    private List<Calendar> location1Dates, location2Dates, location3Dates,location4Dates, location5Dates, location6Dates,location7Dates,
            location8Dates, location9Dates,location10Dates, location11Dates, location12Dates;
    private String location1DateString = "", location2DateString = "", location3DateString = "",location4DateString = "", location5DateString = "", location6DateString = "",
            location7DateString = "", location8DateString = "", location9DateString = "",location10DateString = "", location11DateString = "", location12DateString = "";

    private LatLng location1 = null, location2 = null, location3 = null,location4 = null, location5 = null, location6 = null,
            location7 = null, location8 = null, location9 = null,location10 = null, location11 = null, location12 = null;
    private ArrayList<LatLng> locations;

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
                if (location1 != null) {
                    reportedLocation.setPosition(location1);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 15));
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

                switch (location){
                    case 1:
                        location1Dates = calendars;
                        location1DateString = tempLocationString;

                        Toast.makeText(getContext(),"Selected Dates\n" + location1DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        location2Dates = calendars;
                        location2DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location2DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        location3Dates = calendars;
                        location3DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location3DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        location4Dates = calendars;
                        location4DateString = tempLocationString;

                        Toast.makeText(getContext(),"Selected Dates\n" + location4DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        location5Dates = calendars;
                        location5DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location5DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 6:
                        location6Dates = calendars;
                        location6DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location6DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 7:
                        location7Dates = calendars;
                        location7DateString = tempLocationString;

                        Toast.makeText(getContext(),"Selected Dates\n" + location7DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 8:
                        location8Dates = calendars;
                        location8DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location8DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 9:
                        location9Dates = calendars;
                        location9DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location9DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 10:
                        location10Dates = calendars;
                        location10DateString = tempLocationString;

                        Toast.makeText(getContext(),"Selected Dates\n" + location10DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 11:
                        location11Dates = calendars;
                        location11DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location11DateString, Toast.LENGTH_LONG).show();
                        break;
                    case 12:
                        location12Dates = calendars;
                        location12DateString = tempLocationString;
                        Toast.makeText(getContext(),"Selected Dates\n" + location12DateString, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        DatePickerBuilder builder = new DatePickerBuilder(getContext(), listener)
                .pickerType(cal.MANY_DAYS_PICKER).setMaximumDate(maxDate).setMinimumDate(minDate);

        switch (location){
            case 1:
                if (location1Dates != null)
                builder.setSelectedDays(location1Dates);
                break;
            case 2:
                if (location2Dates != null)
                builder.setSelectedDays(location2Dates);
                break;
            case 3:
                if (location3Dates != null)
                    builder.setSelectedDays(location3Dates);
                break;
            case 4:
                if (location4Dates != null)
                    builder.setSelectedDays(location4Dates);
                break;
            case 5:
                if (location5Dates != null)
                    builder.setSelectedDays(location5Dates);
                break;
            case 6:
                if (location6Dates != null)
                    builder.setSelectedDays(location6Dates);
                break;
            case 7:
                if (location7Dates != null)
                    builder.setSelectedDays(location7Dates);
                break;
            case 8:
                if (location8Dates != null)
                    builder.setSelectedDays(location8Dates);
                break;
            case 9:
                if (location9Dates != null)
                    builder.setSelectedDays(location9Dates);
                break;
            case 10:
                if (location10Dates != null)
                    builder.setSelectedDays(location10Dates);
                break;
            case 11:
                if (location11Dates != null)
                    builder.setSelectedDays(location11Dates);
                break;
            case 12:
                if (location12Dates != null)
                    builder.setSelectedDays(location12Dates);
                break;
            default:

        }
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
        return locations.size();
    }

    public void showNextLocationFragment(int currentLocationReport, View v){
        Boolean hasEmptyLocation = false;
        switch (currentLocationReport){
            case 0:
                Toast.makeText(getContext(),"Fill in Location above before adding another one", Toast.LENGTH_LONG).show();
                break;
            case 1:
                if(location2Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location2Decided) {
                    textViewLocation2.setVisibility(v.VISIBLE);
                    setLocation2.setVisibility(v.VISIBLE);
                    setCalendarLocation2.setVisibility(v.VISIBLE);
                    btnRemoveLocation2.setVisibility(v.VISIBLE);
                    location2Visible = true;
                }
                break;
            case 2:
                if(location3Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location3Decided) {
                    textViewLocation3.setVisibility(v.VISIBLE);
                    setLocation3.setVisibility(v.VISIBLE);
                    setCalendarLocation3.setVisibility(v.VISIBLE);
                    btnRemoveLocation3.setVisibility(v.VISIBLE);
                    location3Visible = true;
                }
                break;
            case 3:
                if(location4Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location4Decided) {
                    textViewLocation4.setVisibility(v.VISIBLE);
                    setLocation4.setVisibility(v.VISIBLE);
                    setCalendarLocation4.setVisibility(v.VISIBLE);
                    btnRemoveLocation4.setVisibility(v.VISIBLE);
                    location4Visible = true;
                }
                break;

            case 4:
                if(location5Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location5Decided) {
                    textViewLocation5.setVisibility(v.VISIBLE);
                    setLocation5.setVisibility(v.VISIBLE);
                    setCalendarLocation5.setVisibility(v.VISIBLE);
                    btnRemoveLocation5.setVisibility(v.VISIBLE);
                    location5Visible = true;
                }
                break;
            case 5:
                if(location6Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location6Decided) {
                    textViewLocation6.setVisibility(v.VISIBLE);
                    setLocation6.setVisibility(v.VISIBLE);
                    setCalendarLocation6.setVisibility(v.VISIBLE);
                    btnRemoveLocation6.setVisibility(v.VISIBLE);
                    location6Visible = true;
                }
                break;

            case 6:
                if(location7Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location7Decided) {
                    textViewLocation7.setVisibility(v.VISIBLE);
                    setLocation7.setVisibility(v.VISIBLE);
                    setCalendarLocation7.setVisibility(v.VISIBLE);
                    btnRemoveLocation7.setVisibility(v.VISIBLE);
                    location7Visible = true;
                }
                break;
            case 7:
                if(location8Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location8Decided) {
                    textViewLocation8.setVisibility(v.VISIBLE);
                    setLocation8.setVisibility(v.VISIBLE);
                    setCalendarLocation8.setVisibility(v.VISIBLE);
                    btnRemoveLocation8.setVisibility(v.VISIBLE);
                    location8Visible = true;
                }
                break;

            case 8:
                if(location9Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location9Decided) {
                    textViewLocation9.setVisibility(v.VISIBLE);
                    setLocation9.setVisibility(v.VISIBLE);
                    setCalendarLocation9.setVisibility(v.VISIBLE);
                    btnRemoveLocation9.setVisibility(v.VISIBLE);
                    location9Visible = true;
                }
                break;
            case 9:
                if(location10Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location10Decided) {
                    textViewLocation10.setVisibility(v.VISIBLE);
                    setLocation10.setVisibility(v.VISIBLE);
                    setCalendarLocation10.setVisibility(v.VISIBLE);
                    btnRemoveLocation10.setVisibility(v.VISIBLE);
                    location10Visible = true;
                }
                break;

            case 10:
                if(location11Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location11Decided) {
                    textViewLocation11.setVisibility(v.VISIBLE);
                    setLocation11.setVisibility(v.VISIBLE);
                    setCalendarLocation11.setVisibility(v.VISIBLE);
                    btnRemoveLocation11.setVisibility(v.VISIBLE);
                    location11Visible = true;
                }
                break;
            case 11:
                if(location12Visible) {
                    hasEmptyLocation = true;
                }
                if(numberOfLocationsSet() == currentLocationReport && !location12Decided) {
                    textViewLocation12.setVisibility(v.VISIBLE);
                    setLocation12.setVisibility(v.VISIBLE);
                    setCalendarLocation12.setVisibility(v.VISIBLE);
                    btnRemoveLocation12.setVisibility(v.VISIBLE);
                    location12Visible = true;
                }
                break;


            default:
                Toast.makeText(getContext(),"Maximum number of locations added", Toast.LENGTH_LONG).show();
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
            switch (currentLocationReport) {
                case 1:
                    yourLocation1String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation1String);
                    break;
                case 2:
                    yourLocation2String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation2String);
                    break;
                case 3:
                    yourLocation3String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation3String);
                    break;
                case 4:
                    yourLocation4String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation4String);
                    break;
                case 5:
                    yourLocation5String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation5String);
                    break;
                case 6:
                    yourLocation6String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation6String);
                    break;
                case 7:
                    yourLocation7String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation7String);
                    break;
                case 8:
                    yourLocation8String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation8String);
                    break;
                case 9:
                    yourLocation9String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation9String);
                    break;
                case 10:
                    yourLocation10String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation10String);
                    break;
                case 11:
                    yourLocation11String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation11String);
                    break;
                case 12:
                    yourLocation12String = addresses.get(0).getAddressLine(0);
                    reportedLocation.setTitle(yourLocation12String);
                    break;
                default:
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dismissPopup(Integer location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());;
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(yourLocation.latitude, yourLocation.longitude, 1);
            yourLocationString = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (location){
            case 1:
                location1 = reportedLocation.getPosition();
                textViewLocation1.setText(yourLocationString);
                if (yourLocation1String != "") {
                    textViewLocation1.setText(yourLocation1String);
                }
                if (location1Decided){
                    this.setLocationInLocations(location, location1);
                }
                else{
                    this.addLocationToLocations(location1);
                    location1Decided = true;
                }
                break;
            case 2:
                location2 = reportedLocation.getPosition();
                textViewLocation2.setText(yourLocationString);
                if (yourLocation2String != "") {
                    textViewLocation2.setText(yourLocation2String);
                }
                if (location2Decided){
                    this.setLocationInLocations(location, location2);
                }
                else{
                    this.addLocationToLocations(location2);
                    location2Decided = true;
                }
                break;
            case 3:
                location3 = reportedLocation.getPosition();
                textViewLocation3.setText(yourLocationString);
                if (yourLocation3String != "") {
                    textViewLocation3.setText(yourLocation3String);
                }
                if (location3Decided){
                    this.setLocationInLocations(location, location3);
                }
                else{
                    this.addLocationToLocations(location3);
                    location3Decided = true;
                }
                break;
            case 4:
                location4 = reportedLocation.getPosition();
                textViewLocation4.setText(yourLocationString);
                if (yourLocation4String != "") {
                    textViewLocation4.setText(yourLocation4String);
                }
                if (location4Decided){
                    this.setLocationInLocations(location, location4);
                }
                else{
                    this.addLocationToLocations(location4);
                    location4Decided = true;
                }

                break;
            case 5:
                location5 = reportedLocation.getPosition();
                textViewLocation5.setText(yourLocationString);
                if (yourLocation5String != "") {
                    textViewLocation5.setText(yourLocation5String);
                }
                if (location5Decided){
                    this.setLocationInLocations(location, location5);
                }
                else{
                    this.addLocationToLocations(location5);
                    location5Decided = true;
                }

                break;
            case 6:
                location6 = reportedLocation.getPosition();
                textViewLocation6.setText(yourLocationString);
                if (yourLocation6String != "") {
                    textViewLocation6.setText(yourLocation6String);
                }
                if (location6Decided){
                    this.setLocationInLocations(location, location6);
                }
                else{
                    this.addLocationToLocations(location6);
                    location6Decided = true;
                }

                break;
            case 7:
                location7 = reportedLocation.getPosition();
                textViewLocation7.setText(yourLocationString);
                if (yourLocation7String != "") {
                    textViewLocation7.setText(yourLocation7String);
                }
                if (location7Decided){
                    this.setLocationInLocations(location, location7);
                }
                else{
                    this.addLocationToLocations(location7);
                    location7Decided = true;
                }

                break;
            case 8:
                location8 = reportedLocation.getPosition();
                textViewLocation8.setText(yourLocationString);
                if (yourLocation8String != "") {
                    textViewLocation8.setText(yourLocation8String);
                }
                if (location8Decided){
                    this.setLocationInLocations(location, location8);
                }
                else{
                    this.addLocationToLocations(location8);
                    location8Decided = true;
                }
                break;
            case 9:
                location9 = reportedLocation.getPosition();
                textViewLocation9.setText(yourLocationString);
                if (yourLocation9String != "") {
                    textViewLocation9.setText(yourLocation9String);
                }
                if (location9Decided){
                    this.setLocationInLocations(location, location9);
                }
                else{
                    this.addLocationToLocations(location9);
                    location9Decided = true;
                }

                break;
            case 10:
                location10 = reportedLocation.getPosition();
                textViewLocation10.setText(yourLocationString);
                if (yourLocation10String != "") {
                    textViewLocation10.setText(yourLocation10String);
                }
                if (location10Decided){
                    this.setLocationInLocations(location, location10);
                }
                else{
                    this.addLocationToLocations(location10);
                    location10Decided = true;
                }

                break;
            case 11:
                location11 = reportedLocation.getPosition();
                textViewLocation11.setText(yourLocationString);
                if (yourLocation11String != "") {
                    textViewLocation11.setText(yourLocation11String);
                }
                if (location11Decided){
                    this.setLocationInLocations(location, location11);
                }
                else{
                    this.addLocationToLocations(location11);
                    location11Decided = true;
                }

                break;
            case 12:
                location12 = reportedLocation.getPosition();
                textViewLocation12.setText(yourLocationString);
                if (yourLocation12String != "") {
                    textViewLocation12.setText(yourLocation12String);
                }
                if (location12Decided){
                    this.setLocationInLocations(location, location12);
                }
                else{
                    this.addLocationToLocations(location12);
                    location12Decided = true;
                }

                break;
            default:
        }
        System.out.println("1: "+location1+ "\n2: "+ location2+ "\n3: "+location3);
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
        if(location1Dates!=null&& location1!=null){
        for(Calendar day:location1Dates){

            lat=location1.latitude;
            latstring=lat.toString();
            lot=location1.longitude;
            lotstring=lot.toString();
            tempday=day.get(Calendar.DAY_OF_MONTH)+"-"+(day.get(Calendar.MONTH)+1)+"-"+day.get(Calendar.YEAR);

            returnlocations.add(new Location(latstring,lotstring,tempday,
                    ratings[0],ratings[1],ratings[2],ratings[3],ratings[4],ratings[5],ratings[6],ratings[7],ratings[8],date.toString()));

        }}
        if(location2Dates!=null && location1!=null){
            for(Calendar day:location2Dates){

                lat=location2.latitude;
                latstring=lat.toString();
                lot=location2.longitude;
                lotstring=lot.toString();
                tempday=day.get(Calendar.DAY_OF_MONTH)+"-"+(day.get(Calendar.MONTH)+1)+"-"+day.get(Calendar.YEAR);
                returnlocations.add(new Location(latstring,lotstring,tempday,
                        ratings[0],ratings[1],ratings[2],ratings[3],ratings[4],ratings[5],ratings[6],ratings[7],ratings[8],date.toString()));


            }}
        if(location3Dates!=null && location3!=null){
            for(Calendar day:location3Dates){

                lat=location3.latitude;
                latstring=lat.toString();
                lot=location3.longitude;
                lotstring=lot.toString();
                tempday=day.get(Calendar.DAY_OF_MONTH)+"-"+(day.get(Calendar.MONTH)+1)+"-"+day.get(Calendar.YEAR);
                returnlocations.add(new Location(latstring,lotstring,tempday,
                        ratings[0],ratings[1],ratings[2],ratings[3],ratings[4],ratings[5],ratings[6],ratings[7],ratings[8],date.toString()));

            }}
        return returnlocations;


    }
}
