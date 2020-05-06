package com.example.coronakanditaten2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.security.KeyStore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "StartPageActivity";
    Datahandler datahandler = new Datahandler();

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
        datahandler.credentials=new Credentials("tjtjata","reresresres");
        datahandler.getserveruser();
        datahandler.getalllserverocations("");
        datahandler.getuserserverlocations();

        setContentView(R.layout.activity_main);


        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
    }



    private void setupViewPager(ViewPager viewPager){
        adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(startpageFragment, "Start Page");             // 0
        adapter.addFragment(statisticsFragment, "Statistics");            // 1
        adapter.addFragment(heatmapFragment, "Heatmap");                  // 2
        adapter.addFragment(reportSymptomsFragment, "Report Symptoms");   // 3
        adapter.addFragment(reportLocationFragment, "Report Location");   // 4
        adapter.addFragment(forumFragment, "Forum");                      // 5
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        //System.out.println(checked);

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

    public void dismissPopupForLocation1(View view){
        reportLocationFragment.dismissPopup(reportLocationFragment.getCurrentLocationReport());
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if(mViewPager.getCurrentItem()==1){
            setViewPager(2);
        }
        else{
            setViewPager(0);
            
        }

        Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}
