package com.example.coronakanditaten2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "StartPageActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartPageFragment(), "Start Page");             // 0
        adapter.addFragment(new StatisticsFragment(), "Statistics");            // 1
        adapter.addFragment(new HeatmapFragment(), "Heatmap");                  // 2
        adapter.addFragment(new ReportSymptomsFragment(), "Report Symptoms");   // 3
        adapter.addFragment(new ReportLocationFragment(), "Report Location");   // 4
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }
}
