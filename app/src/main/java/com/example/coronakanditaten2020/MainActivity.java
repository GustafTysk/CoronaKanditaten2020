package com.example.coronakanditaten2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final String TAG = "StartPageActivity";
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    public BottomNavigationView bottomNav;


    protected Context context;
    private LatLng currentLocation;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 0;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1;

    public Datahandler datahandler = new Datahandler();

    MySettingsFragment mySettingsFragment;

    StartPageFragment startpageFragment;
    StatisticsFragment statisticsFragment ;
    HeatmapFragment heatmapFragment;
    ReportSymptomsFragment reportSymptomsFragment;
    ReportLocationFragment reportLocationFragment;
    ForumFragment forumFragment;

    PlacesClient placesClient;

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private NonSwipeableViewPager mViewPager;

    private SectionsStatePagerAdapter adapter;

    private Spinner spinner;
    String text;
    boolean checked;


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
        //Put this on to work with database
       datahandler.credentials=new Credentials(intent.getStringExtra("ema"),intent.getStringExtra("pass"));
       System.out.println(datahandler.credentials.Email);
       System.out.println(datahandler.credentials.Password);
        //
        datahandler.getserveruserinfo("");
        datahandler.getTopPost(100);
        datahandler.getserveruser();
        datahandler.getalllserverocations("");
        datahandler.getuserserverlocations();
        datahandler.setupplike();
        datahandler.user = new User ("Testperson", "user@gmail.com", 25, "male", "password123", "25 jan 2019");
        setContentView(R.layout.activity_main);

            mySettingsFragment = new MySettingsFragment();

            startpageFragment = new StartPageFragment();
            statisticsFragment = new StatisticsFragment();
            heatmapFragment = new HeatmapFragment();
            reportSymptomsFragment = new ReportSymptomsFragment();
            reportLocationFragment = new ReportLocationFragment();
            forumFragment = new ForumFragment();

            mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

            mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
            setupViewPager(mViewPager);
            Places.initialize(getApplicationContext(), "AIzaSyAdNZnteknM0VlU416q-b8ZEqRBjiFOiPA");
            setViewPager(1);



    }


    public BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    setViewPager(1);
                    return true;
                case R.id.nav_statistics:
                    setViewPager(2);
                    statisticsFragment.setStatisticsBottomNav();
                    return true;
                case R.id.nav_heatmap:
                    setViewPager(3);
                    heatmapFragment.setHeatmapBottomNav();
                    return true;
                case R.id.nav_report_symptoms:
                    setViewPager(4);
                    reportSymptomsFragment.setReportSymptomsBottomNav();
                    return true;
                case R.id.nav_forum:
                    setViewPager(6);
                    forumFragment.setForumBottomNav();
                    return true;

                default:
            }
            return false;            }
    };


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
        viewPager.setOffscreenPageLimit(7);
    }


    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber,false);
    }


    public void onCheckboxClicked(View view) {
        checked = ((CheckBox) view).isChecked();
        spinner = (Spinner)findViewById(R.id.spinner1);
        text = spinner.getSelectedItem().toString();
        switch (view.getId()) {
            case R.id.diarrheaBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showDiarrheaSeries();
                    } else {
                        statisticsFragment.hideDiarrheaSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showDiarrheaSeries2();
                    } else {
                        statisticsFragment.hideDiarrheaSeries2();
                    }
                }
                break;
            case R.id.runnyNoseBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showRunnyNoseSeries();
                    } else {
                        statisticsFragment.hideRunnyNoseSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showRunnyNoseSeries2();
                    } else {
                        statisticsFragment.hideRunnyNoseSeries2();
                    }
                }
                break;
            case R.id.tirednessBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showTirednessSeries();
                    } else {
                        statisticsFragment.hideTirednessSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showTirednessSeries2();
                    } else {
                        statisticsFragment.hideTirednessSeries2();
                    }
                }
                break;
            case R.id.feverBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showFeverSeries();
                    } else {
                        statisticsFragment.hideFeverSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showFeverSeries2();
                    } else {
                        statisticsFragment.hideFeverSeries2();
                    }
                }
                break;
            case R.id.throatBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showThroatSeries();
                    } else {
                        statisticsFragment.hideThroatSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showThroatSeries2();
                    } else {
                        statisticsFragment.hideThroatSeries2();
                    }
                }
                break;
            case R.id.nasalConBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showNasalConSeries();
                    } else {
                        statisticsFragment.hideNasalConSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showNasalConSeries2();
                    } else {
                        statisticsFragment.hideNasalConSeries2();
                    }
                }
                break;
            case R.id.coughBox:
                if(text.equals(getString(R.string.per_day_symptoms))){

                    if (checked) {
                        statisticsFragment.showCoughSeries();
                    } else {
                        statisticsFragment.hideCoughSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showCoughSeries2();
                    } else {
                        statisticsFragment.hideCoughSeries2();
                    }
                }
                break;
            case R.id.headacheBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showHeadacheSeries();
                    } else {
                        statisticsFragment.hideHeadacheSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showHeadacheSeries2();
                    } else {
                        statisticsFragment.hideHeadacheSeries2();
                    }
                }
                break;
            case R.id.breathingDiffBox:
                if(text.equals(getString(R.string.per_day_symptoms))){
                    if (checked) {
                        statisticsFragment.showBreathingDiffSeries();
                    } else {
                        statisticsFragment.hideBreathingDiffSeries();
                    }
                }
                else{
                    if (checked) {
                        statisticsFragment.showBreathingDiffSeries2();
                    } else {
                        statisticsFragment.hideBreathingDiffSeries2();
                    }
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
                Toast.makeText(MainActivity.this,getString(R.string.no_back_action),Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        System.out.println("BRAPPPPAPAPAPA");
        reportLocationFragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
        //System.out.println(currentLocation);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
