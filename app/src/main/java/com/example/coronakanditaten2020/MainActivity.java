package com.example.coronakanditaten2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final String TAG = "StartPageActivity";
    protected LocationManager locationManager;
    protected LocationListener locationListener;



    protected Context context;
    private LatLng currentLocation;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 0;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1;

    public Datahandler datahandler = new Datahandler();
    ArrayList<Location> test;

    MySettingsFragment mySettingsFragment = new MySettingsFragment();

    StartPageFragment startpageFragment = new StartPageFragment();
    StatisticsFragment statisticsFragment = new StatisticsFragment();
    HeatmapFragment heatmapFragment = new HeatmapFragment();
    ReportSymptomsFragment reportSymptomsFragment = new ReportSymptomsFragment();
    ReportLocationFragment reportLocationFragment = new ReportLocationFragment();
    ForumFragment forumFragment = new ForumFragment();
    PlacesClient placesClient;

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private NonSwipeableViewPager mViewPager;

    private SectionsStatePagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);
        Intent intent=getIntent();




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            handleRequestPermission();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        datahandler.credentials=new Credentials("5fgg","frff");

       // datahandler.credentials=new Credentials(intent.getStringExtra("ema"),intent.getStringExtra("pass"));
        //System.out.println(datahandler.credentials.Email);
       // System.out.println(datahandler.credentials.Password);
        datahandler.getserveruser();
        datahandler.getalllserverocations("");
        datahandler.getuserserverlocations();
        setContentView(R.layout.activity_main);




        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        Places.initialize(getApplicationContext(), "AIzaSyAdNZnteknM0VlU416q-b8ZEqRBjiFOiPA");
    }



    public void handleRequestPermission(){
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mySettingsFragment, "My Settings");           // 0
        adapter.addFragment(startpageFragment, "Start Page");             // 1
        adapter.addFragment(statisticsFragment, "Statistics");            // 2
        adapter.addFragment(heatmapFragment, "Heatmap");                  // 3
        adapter.addFragment(reportSymptomsFragment, "Report Symptoms");   // 4
        adapter.addFragment(reportLocationFragment, "Report Location");   // 5
        adapter.addFragment(forumFragment, "Forum");                      // 6
        viewPager.setAdapter(adapter);

        setViewPager(1);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.diarrheaBox:
                if (checked) {
                    statisticsFragment.showDiarrheaSeries();
                }
                else{
                    statisticsFragment.hideDiarrheaSeries();
                }
                break;
            case R.id.runnyNoseBox:
                if (checked) {
                    statisticsFragment.showRunnyNoseSeries();
                }
                else {
                    statisticsFragment.hideRunnyNoseSeries();
                }
                break;
            case R.id.tirednessBox:
                if (checked) {
                    statisticsFragment.showTirednessSeries();
                }
                else {
                    statisticsFragment.hideTirednessSeries();
                }
                break;
            case R.id.feverBox:
                if (checked) {
                    statisticsFragment.showFeverSeries();
                }
                else {
                    statisticsFragment.hideFeverSeries();
                }
                break;
            case R.id.throatBox:
                if (checked) {
                    statisticsFragment.showThroatSeries();
                }
                else {
                    statisticsFragment.hideThroatSeries();
                }
                break;
            case R.id.nasalConBox:
                if (checked) {
                    statisticsFragment.showNasalConSeries();
                }
                else {
                    statisticsFragment.hideNasalConSeries();
                }
                break;
            case R.id.coughBox:
                if (checked) {
                    statisticsFragment.showCoughSeries();
                }
                else {
                    statisticsFragment.hideCoughSeries();
                }
                break;
            case R.id.headacheBox:
                if (checked) {
                    statisticsFragment.showHeadacheSeries();
                }
                else {
                    statisticsFragment.hideHeadacheSeries();
                }
                break;
            case R.id.breathingDiffBox:
                if (checked) {
                    statisticsFragment.showBreathingDiffSeries();
                }
                else {
                    statisticsFragment.hideBreathingDiffSeries();
                }
                break;

        }
    }
    @Override
    public void onClick(View v) {

    }

    public void dismissPopupForLocation(View view){
        reportLocationFragment.dismissPopup(reportLocationFragment.getCurrentLocationReport());
    }

    public void cancelPopupForLocation(View view){
        reportLocationFragment.cancelMapPopup();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Boolean noClosedMap = true;
        switch (mViewPager.getCurrentItem()){
            case 1:
                Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
                break;
            case 5:
                try {
                    noClosedMap = false;
                    reportLocationFragment.cancelMapPopup();
                } catch (Exception e) {
                    noClosedMap = true;
                    e.printStackTrace();
                }
                if(noClosedMap){
                    setViewPager(4);
                }

                break;
            default:
                setViewPager(1);
        }
        return;
    }




    @Override
    public void onLocationChanged(android.location.Location location) {
        currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
        System.out.println(currentLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    public LatLng getCurrentLocation(){
        return currentLocation;
    }

}
