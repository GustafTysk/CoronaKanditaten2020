package com.example.coronakanditaten2020;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
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

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReportLocationFragment extends Fragment  implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {
    private static final String TAG = "Fragment Statistics";
    private ViewGroup containerThis;


    private GoogleMap mGoogleMap;
    private MapView mMapView;
    public PopupWindow mapWindow;

    private Marker reportedLocation;
    private String yourLocationString;

    private Bundle savedInstance;

    private Button btnRlToStart;
    private Button btnRlToRs;

    private ImageButton setLocation1;
    private ImageButton setLocation2;

    private TextView textViewLocation1;
    private TextView textViewLocation2;

    private int currentLocationReport;
    private LatLng location1;
    private LatLng location2;

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

        textViewLocation1 = (TextView) view.findViewById(R.id.textViewLocation1);
        textViewLocation2 = (TextView) view.findViewById(R.id.textViewLocation2);



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
        final LatLng yourLocation = new LatLng(59.8, 17.63);
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.8, 17.63), 10));

        reportedLocation = googleMap.addMarker(new MarkerOptions()
                .position(yourLocation)
                .title("yourLocation")
                .draggable(true));
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        reportedLocation.setPosition(latLng);

        System.out.println(reportedLocation.getPosition());
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
            yourLocationString = addresses.get(0).getAddressLine(0);
            reportedLocation.setTitle(yourLocationString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dismissPopup(Integer location){

        switch (location){
            case 1:
                location1 = reportedLocation.getPosition();
                textViewLocation1.setText(yourLocationString);
                break;
            case 2:
                location2 = reportedLocation.getPosition();
                textViewLocation2.setText(yourLocationString);
                break;
        }


        super.onDestroy();
        mMapView.onDestroy();
        mapWindow.dismiss();
        System.out.println("Location 1: " + location1);
        System.out.println("Location 2: " + location2);
    }

    public int getCurrentLocationReport(){
        return currentLocationReport;
    }


}
