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
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
    private String yourLocation1String;
    private String yourLocation2String;
    private String yourLocation3String;

    private Bundle savedInstance;

    private Button btnRlToStart;
    private Button btnRlToRs;

    private ImageButton setLocation1;
    private ImageButton setLocation2;
    private ImageButton setLocation3;

    private ImageButton setCalendarLocation1;
    private ImageButton setCalendarLocation2;
    private ImageButton setCalendarLocation3;

    private TextView textViewLocation1;
    private TextView textViewLocation2;
    private TextView textViewLocation3;

    private int currentLocationReport = 0;
    private final LatLng yourLocation = new LatLng(59.8, 17.63);
    private String yourLocationString;

    private List<Calendar> location1Dates;
    private String location1DateString = "";
    private List<Calendar> location2Dates;
    private String location2DateString = "";
    private List<Calendar> location3Dates;
    private String location3DateString = "";

    private LatLng location1 = null;
    private LatLng location2 = null;
    private LatLng location3 = null;

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

        setLocation1 = (ImageButton) view.findViewById(R.id.setLocation1);
        setLocation1.setOnClickListener(this);
        setLocation2 = (ImageButton) view.findViewById(R.id.setLocation2);
        setLocation2.setOnClickListener(this);
        setLocation3 = (ImageButton) view.findViewById(R.id.setLocation3);
        setLocation3.setOnClickListener(this);

        setCalendarLocation1 = (ImageButton) view.findViewById(R.id.setCalendarLocation1);
        setCalendarLocation1.setOnClickListener(this);
        setCalendarLocation2 = (ImageButton) view.findViewById(R.id.setCalendarLocation2);
        setCalendarLocation2.setOnClickListener(this);
        setCalendarLocation3 = (ImageButton) view.findViewById(R.id.setCalendarLocation3);
        setCalendarLocation3.setOnClickListener(this);

        textViewLocation1 = (TextView) view.findViewById(R.id.textViewLocation1);
        textViewLocation2 = (TextView) view.findViewById(R.id.textViewLocation2);
        textViewLocation3 = (TextView) view.findViewById(R.id.textViewLocation3);

        minDate.setTimeInMillis(minDateTime);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnRlToRs:
                ((MainActivity) getActivity()).setViewPager(3);
                break;
            case R.id.btnRlToStart:
                ((MainActivity) getActivity()).setViewPager(0);
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
        }
    }

    public void startReportLocationMap(){
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
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 10));
                if (location1 != null) {
                    reportedLocation.setPosition(location1);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 10));
                }
                break;
            case 2:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 10));
                if (location2 != null) {
                    reportedLocation.setPosition(location2);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 10));
                }
                break;
            case 3:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 10));
                if (location3 != null) {
                    reportedLocation.setPosition(location3);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location3, 10));
                }
                break;
            default:
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 10));
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
            default:

        }
        DatePicker datePicker = builder.build();
        datePicker.show();

    }

    @Override
    public void onMapClick(LatLng latLng) {
        reportedLocation.setPosition(latLng);
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());;
        List<Address> addresses = null;
        System.out.println(reportedLocation.getPosition());
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
                default:
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dismissPopup(Integer location) {

        switch (location){
            case 1:
                location1 = reportedLocation.getPosition();
                textViewLocation1.setText(yourLocation1String);
                break;
            case 2:
                location2 = reportedLocation.getPosition();
                textViewLocation2.setText(yourLocation2String);
                break;
            case 3:
                location3 = reportedLocation.getPosition();
                textViewLocation3.setText(yourLocation3String);
                break;
            default:
        }
        System.out.println("1: "+location1+ "\n2: "+ location2+ "\n3: "+location3);
        super.onDestroy();
        mMapView.onDestroy();
        mapWindow.dismiss();
    }

    public void cancelMapPopup(){
        super.onDestroy();
        mMapView.onDestroy();
        mapWindow.dismiss();
    }



    public int getCurrentLocationReport(){


        return currentLocationReport;
    }
}
