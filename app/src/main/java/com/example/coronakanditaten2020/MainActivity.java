package com.example.coronakanditaten2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "StartPageActivity";
    //private Datahandler datahandler = new Datahandler()
    StatisticsFragment statisticsFragment=new StatisticsFragment();


    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    private SectionsStatePagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(TAG, "onCreate: Started");
        //String tja = new String("tja");
        //datahandler.save(tja, this);


        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
    }



    private void setupViewPager(ViewPager viewPager){
        adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartPageFragment(), "Start Page");             // 0
        adapter.addFragment(statisticsFragment, "Statistics");            // 1
        adapter.addFragment(new HeatmapFragment(), "Heatmap");                  // 2
        adapter.addFragment(new ReportSymptomsFragment(), "Report Symptoms");   // 3
        adapter.addFragment(new ReportLocationFragment(), "Report Location");   // 4
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        System.out.println(checked);

        switch (view.getId()) {
            case R.id.diarrheaBox:
                if (checked) {
                    System.out.println("hej");
                    statisticsFragment.showDiarrheaSeries();
                }
                else{
                    System.out.println("tjtjtja");
                    statisticsFragment.hideDiarrheaSeries();
                }
                break;
        }
    }
    @Override
    public void onClick(View v) {

    }
}
