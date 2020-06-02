package com.example.coronakanditaten2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class StatisticsFragment extends Fragment implements  AdapterView.OnItemSelectedListener {
    private static final String TAG = "Fragment Statistics";

    private ArrayList<Location> Locations = new ArrayList<>();
    private ArrayList<User> Users = new ArrayList<>();
    private ArrayList<Integer> allRatingsDiarrhea1=new ArrayList<Integer>(), allRatingsRunnyNose1=new ArrayList<Integer>(),
            allRatingsNasalCon1=new ArrayList<Integer>(), allRatingsBreathing1=new ArrayList<Integer>(),
            allRatingsTiredness1=new ArrayList<Integer>(), allRatingsHeadache1=new ArrayList<Integer>(),
            allRatingsFever1=new ArrayList<Integer>(), allRatingsCough1=new ArrayList<Integer>(), allRatingsThroat1=new ArrayList<Integer>(), allRatingsSick1=new ArrayList<Integer>();
    private ArrayList<Integer> allRatingsDiarrhea2=new ArrayList<Integer>(), allRatingsRunnyNose2=new ArrayList<Integer>(),
            allRatingsNasalCon2=new ArrayList<Integer>(), allRatingsBreathing2=new ArrayList<Integer>(),
            allRatingsTiredness2=new ArrayList<Integer>(), allRatingsHeadache2=new ArrayList<Integer>(),
            allRatingsFever2=new ArrayList<Integer>(), allRatingsCough2=new ArrayList<Integer>(), allRatingsThroat2=new ArrayList<Integer>(), allRatingsSick2=new ArrayList<Integer>();
    private DataPoint[] dpDiarrhea, dpRunnyNose, dpNasalCon, dpBreathing, dpTiredness, dpThroat, dpFever, dpCough, dpHeadache;
    private ArrayList<ArrayList<String>> userInfo;
    int daystocheck=60;
    private boolean setuppdone=false;


    public CheckBox diarrheaBox, runnyNoseBox, nasalConBox, headacheBox, throatBox, breathingDiffBox, tirednessBox, coughBox, feverBox, allSickBox;

    //GRAPH AND SERIES
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;



    //ALLA SYMPTOM
    private int largest, largest2, maleAge0To18, maleAge19To40, maleAge41To64, maleAge65Plus, femaleAge0To18, femaleAge19To40, femaleAge41To64,
            femaleAge65Plus, otherAge0To18, otherAge19To40, otherAge41To64, otherAge65Plus;

    private String tempString;
    private ArrayList<Integer> Symtomcounter=new ArrayList<Integer>();
    private boolean IsTotalSymtoms=true;

    //CALENDAR
    private boolean noSelectedDates = true;
    private GregorianCalendar calendarStat;
    private ImageButton setCalendarLocation1;
    private com.applandeo.materialcalendarview.CalendarView cal;
    private List<Calendar> calendars;
    private Calendar maxDate = Calendar.getInstance(TimeZone.getDefault());
    private Date minDate;
    private Date minCalendarValue, maxCalendarValue;
    Date dateOfInterest, today;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdp=new SimpleDateFormat("MM/dd");
    String sdfDate;
    View view;
    LineChart lineChart;
    LineDataSet DataPointsDiarrhea1, DataPointsRunnyNose1, DataPointsNasalCon1, DataPointsBreathing1, DataPointsTiredness1, DataPointsThroat1, DataPointsFever1, DataPointsCough1, DataPointsHeadache1, DataPoinsSick1,
            DataPointsDiarrhea2, DataPointsRunnyNose2, DataPointsNasalCon2, DataPointsBreathing2, DataPointsTiredness2, DataPointsThroat2, DataPointsFever2, DataPointsCough2, DataPointsHeadache2, DataPoinsSick2;
    List<Entry> vals11 = new ArrayList<Entry>(),vals12 = new ArrayList<Entry>(),vals13 = new ArrayList<Entry>(),vals14 = new ArrayList<Entry>(),vals15 = new ArrayList<Entry>(),
                vals16 = new ArrayList<Entry>(),vals17 = new ArrayList<Entry>(),vals18 = new ArrayList<Entry>(),vals19 = new ArrayList<Entry>(),vals1S = new ArrayList<Entry>(),
                vals21 = new ArrayList<Entry>(),vals22 = new ArrayList<Entry>(),vals23 = new ArrayList<Entry>(),vals24 = new ArrayList<Entry>(),vals25 = new ArrayList<Entry>(),
                vals26 = new ArrayList<Entry>(),vals27 = new ArrayList<Entry>(),vals28 = new ArrayList<Entry>(),vals29 = new ArrayList<Entry>(),vals2s = new ArrayList<Entry>();
    List<ILineDataSet> DataSymtomPerDaySets = new ArrayList<ILineDataSet>();
    List<ILineDataSet> DataSymtomTotalSets = new ArrayList<ILineDataSet>();
    List<ILineDataSet> DataSickPerDaySets = new ArrayList<ILineDataSet>();
    List<ILineDataSet> DataSickTotalSets = new ArrayList<ILineDataSet>();
    List<ILineDataSet> Datadefault = new ArrayList<ILineDataSet>();
    LineData data;
    private String[] xValue;



    //TABLE VIEW
    private TextView textView6, textView7, textView8, textView10, textView11, textView12,
            textView14, textView15, textView16, textView18, textView19, textView20;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        diarrheaBox = (CheckBox) view.findViewById(R.id.diarrheaBox);
        runnyNoseBox = (CheckBox) view.findViewById(R.id.runnyNoseBox);
        tirednessBox = (CheckBox) view.findViewById(R.id.tirednessBox);
        feverBox = (CheckBox) view.findViewById(R.id.feverBox);
        throatBox = (CheckBox) view.findViewById(R.id.throatBox);
        nasalConBox = (CheckBox) view.findViewById(R.id.nasalConBox);
        coughBox = (CheckBox) view.findViewById(R.id.coughBox);
        headacheBox = (CheckBox) view.findViewById(R.id.headacheBox);
        breathingDiffBox = (CheckBox) view.findViewById(R.id.breathingDiffBox);
        allSickBox = (CheckBox) view.findViewById(R.id.allSickBox);
        spinner = view.findViewById(R.id.spinner1);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        textView10 = (TextView) view.findViewById(R.id.textView10);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        textView12 = (TextView) view.findViewById(R.id.textView12);
        textView14 = (TextView) view.findViewById(R.id.textView14);
        textView15 = (TextView) view.findViewById(R.id.textView15);
        textView16 = (TextView) view.findViewById(R.id.textView16);
        textView18 = (TextView) view.findViewById(R.id.textView18);
        textView19 = (TextView) view.findViewById(R.id.textView19);
        textView20 = (TextView) view.findViewById(R.id.textView20);
        createCalendarSdf();





        return view;
    }

    public void setStatisticsBottomNav(){
        ((MainActivity) requireActivity()).bottomNav = (BottomNavigationView) getView().findViewById(R.id.bottom_navigation);
        ((MainActivity) requireActivity()).bottomNav.setOnNavigationItemSelectedListener(((MainActivity) getActivity()).navListener);
        ((MainActivity) requireActivity()).bottomNav.getMenu().findItem(R.id.nav_statistics).setChecked(true);

    }

    public void setupstatistik(){
        userInfo=((MainActivity)getActivity()).datahandler.userinfo;
        Locations=((MainActivity)getActivity()).datahandler.getHeatmaplocations();
        createLineDataSet();
        lineChart=view.findViewById(R.id.linechart);
        lineChart.getLegend().setEnabled(false);

        DataSymtomTotalSets.add(DataPointsDiarrhea2);
        DataSymtomTotalSets.add(DataPointsTiredness2);
        DataSymtomTotalSets.add(DataPointsNasalCon2);
        DataSymtomTotalSets.add(DataPointsFever2);
        DataSymtomTotalSets.add(DataPointsCough2);
        DataSymtomTotalSets.add(DataPointsThroat2);
        DataSymtomTotalSets.add(DataPointsHeadache2);
        DataSymtomTotalSets.add(DataPointsBreathing2);
        DataSymtomTotalSets.add(DataPointsRunnyNose2);

        DataSymtomPerDaySets.add(DataPointsDiarrhea1);
        DataSymtomPerDaySets.add(DataPointsTiredness1);
        DataSymtomPerDaySets.add(DataPointsNasalCon1);
        DataSymtomPerDaySets.add(DataPointsFever1);
        DataSymtomPerDaySets.add(DataPointsCough1);
        DataSymtomPerDaySets.add(DataPointsThroat1);
        DataSymtomPerDaySets.add(DataPointsHeadache1);
        DataSymtomPerDaySets.add(DataPointsBreathing1);
        DataSymtomPerDaySets.add(DataPointsRunnyNose1);

        DataSickTotalSets.add(DataPoinsSick2);
        DataSickPerDaySets.add(DataPoinsSick1);


        data= new LineData(DataSymtomTotalSets);
        lineChart.setData(data);
        lineChart.invalidate();

        countTable();
        fillTable();
        setuppdone=true;
        xValue=new String[daystocheck];
        for(int j = 0; j<daystocheck; j++){
            xValue[daystocheck-j-1] =getdisplaydate(j);
        }
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xValue[(int) value];
            }
        };
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getXAxis().setValueFormatter(formatter);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        Description description=new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
    }


    public void showDiarrheaSeries() {

        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsDiarrhea2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsDiarrhea1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }

    }

    public void hideDiarrheaSeries () {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsDiarrhea2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsDiarrhea1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }


    }



    public void showRunnyNoseSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsRunnyNose2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsRunnyNose1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }


    public void hideRunnyNoseSeries () {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsRunnyNose2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsRunnyNose1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }

    public void showTirednessSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsTiredness2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsTiredness1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }

    }

    public void hideTirednessSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsTiredness2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsTiredness1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }

    public void showFeverSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsFever2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsFever1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void hideFeverSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsFever2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsFever1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void showThroatSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsThroat2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsThroat1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void hideThroatSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsThroat2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsThroat1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void showNasalConSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsNasalCon2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsNasalCon1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void hideNasalConSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsNasalCon2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsNasalCon1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void showCoughSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsCough2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsCough1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void hideCoughSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsCough2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsCough2);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void showHeadacheSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsHeadache2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsHeadache1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void hideHeadacheSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsHeadache2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomTotalSets.remove(DataPointsHeadache1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }
    public void showBreathingDiffSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.add(DataPointsBreathing2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.add(DataPointsBreathing1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }

    }
    public void hideBreathingDiffSeries() {
        if(IsTotalSymtoms==true){
            DataSymtomTotalSets.remove(DataPointsBreathing2);
            data= new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
            DataSymtomPerDaySets.remove(DataPointsBreathing1);
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
    }

    public void showAllSickSeries() {
        if(IsTotalSymtoms){
            data=new LineData(DataSickTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
        else{
                data=new LineData(DataSickPerDaySets);
                lineChart.setData(data);
                lineChart.invalidate();


        }


    }
    public void hideAllSickSeries() {
        DataSymtomTotalSets.clear();
        DataSymtomTotalSets.add(DataPointsDiarrhea2);
        DataSymtomTotalSets.add(DataPointsTiredness2);
        DataSymtomTotalSets.add(DataPointsNasalCon2);
        DataSymtomTotalSets.add(DataPointsFever2);
        DataSymtomTotalSets.add(DataPointsCough2);
        DataSymtomTotalSets.add(DataPointsThroat2);
        DataSymtomTotalSets.add(DataPointsHeadache2);
        DataSymtomTotalSets.add(DataPointsBreathing2);
        DataSymtomTotalSets.add(DataPointsRunnyNose2);
        DataSymtomPerDaySets.clear();
        DataSymtomPerDaySets.add(DataPointsDiarrhea1);
        DataSymtomPerDaySets.add(DataPointsTiredness1);
        DataSymtomPerDaySets.add(DataPointsNasalCon1);
        DataSymtomPerDaySets.add(DataPointsFever1);
        DataSymtomPerDaySets.add(DataPointsCough1);
        DataSymtomPerDaySets.add(DataPointsThroat1);
        DataSymtomPerDaySets.add(DataPointsHeadache1);
        DataSymtomPerDaySets.add(DataPointsBreathing1);
        DataSymtomPerDaySets.add(DataPointsRunnyNose1);
        if (IsTotalSymtoms){
            data=new LineData(DataSymtomTotalSets);
            lineChart.setData(data);
            lineChart.invalidate();

        }
        else{
            data=new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();


        }
        allSickBox.setChecked(false);

    }



    public void createCalendarSdf() {
        today = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdp= new SimpleDateFormat("MM-dd");
    }
    public void createLineDataSet(){
        int day;
        daystocheck=0;
        for(Location loc:Locations){
            day=daysago(loc.getDate());
            if(day>daystocheck){
                daystocheck=day;

            }


        }
        daystocheck++;

        setuppalldata();
        for(Location loc:Locations){
            day=daysago(loc.getDate());
            if (loc.getDiarrheaRatingBar()>0){
                allRatingsDiarrhea1.set(day,(allRatingsDiarrhea1.get(day)+1));
            }
            if (loc.getHeadacheRatingBar()>0){
                allRatingsHeadache1.set(day,(allRatingsHeadache1.get(day)+1));
            }
            if (loc.getCoughRatingBar()>0){
                allRatingsCough1.set(day,(allRatingsCough1.get(day)+1));
            }
            if (loc.getFeverRatingBar()>0){
                allRatingsFever1.set(day,(allRatingsFever1.get(day)+1));
            }
            if (loc.getThroatRatingBar()>0){
                allRatingsThroat1.set(day,(allRatingsThroat1.get(day)+1));
            }
            if (loc.getRunnyNoseRatingBar()>0){
                allRatingsRunnyNose1.set(day,(allRatingsRunnyNose1.get(day)+1));
            }
            if (loc.getNasalCongestionRatingBar()>0){
                allRatingsNasalCon1.set(day,(allRatingsNasalCon1.get(day)+1));
            }
            if (loc.getTirednessRatingBar()>0){
                allRatingsTiredness1.set(day,(allRatingsTiredness1.get(day)+1));
            }
            if (loc.getBreathingRatingBar()>0){
                allRatingsBreathing1.set(day,(allRatingsBreathing1.get(day)+1));
            }
            allRatingsSick1.set(day,allRatingsSick1.get(day)+1);

        }
        allRatingsSick2.set(daystocheck-1,(allRatingsSick1.get(daystocheck-1)));
        allRatingsDiarrhea2.set(daystocheck-1,(allRatingsDiarrhea1.get(daystocheck-1)));
        allRatingsHeadache2.set(daystocheck-1,(allRatingsHeadache1.get(daystocheck-1)));
        allRatingsFever2.set(daystocheck-1, allRatingsFever1.get(daystocheck-1));
        allRatingsCough2.set(daystocheck-1,(allRatingsCough1.get(daystocheck-1)));
        allRatingsThroat2.set(daystocheck-1,(allRatingsThroat1.get(daystocheck-1)));
        allRatingsRunnyNose2.set(daystocheck-1,(allRatingsRunnyNose1.get(daystocheck-1)));
        allRatingsNasalCon2.set(daystocheck-1,(allRatingsNasalCon1.get(daystocheck-1)));
        allRatingsTiredness2.set(daystocheck-1,(allRatingsTiredness1.get(daystocheck-1)));
        allRatingsBreathing2.set(daystocheck-1,(allRatingsBreathing1.get(daystocheck-1)));

        for(int i=daystocheck-2; i>=0;i=i-1){

            allRatingsSick2.set(i,(allRatingsSick2.get(i+1)+allRatingsSick1.get(i)));
            allRatingsDiarrhea2.set(i,(allRatingsDiarrhea2.get(i+1)+allRatingsDiarrhea1.get(i)));
            allRatingsHeadache2.set(i,(allRatingsHeadache2.get(i+1)+allRatingsHeadache1.get(i)));
            allRatingsFever2.set(i,( allRatingsFever2.get(i+1)+ allRatingsFever1.get(i)));
            allRatingsCough2.set(i,(allRatingsCough2.get(i+1)+allRatingsCough1.get(i)));
            allRatingsThroat2.set(i,(allRatingsThroat2.get(i+1)+allRatingsThroat1.get(i)));
            allRatingsRunnyNose2.set(i,(allRatingsRunnyNose2.get(i+1)+allRatingsRunnyNose1.get(i)));
            allRatingsNasalCon2.set(i,(allRatingsNasalCon2.get(i+1)+allRatingsNasalCon1.get(i)));
            allRatingsTiredness2.set(i,(allRatingsTiredness2.get(i+1)+allRatingsTiredness1.get(i)));
            allRatingsBreathing2.set(i,(allRatingsBreathing2.get(i+1)+allRatingsBreathing1.get(i)));
        }


        for (int i=0; i<daystocheck;i++){

            vals1S.add(new Entry((float)daystocheck-1-i,(float)allRatingsSick1.get(i)));
            vals11.add(new Entry((float)daystocheck-1-i,(float)allRatingsDiarrhea1.get(i)));
            vals12.add(new Entry((float)daystocheck-1-i,(float)allRatingsHeadache1.get(i)));
            vals13.add(new Entry((float)daystocheck-1-i,(float)allRatingsFever1.get(i)));
            vals14.add(new Entry((float)daystocheck-1-i,(float)allRatingsCough1.get(i)));
            vals15.add(new Entry((float)daystocheck-1-i,(float)allRatingsThroat1.get(i)));
            vals16.add(new Entry((float)daystocheck-1-i,(float)allRatingsRunnyNose1.get(i)));
            vals17.add(new Entry((float)daystocheck-1-i,(float)allRatingsNasalCon1.get(i)));
            vals18.add(new Entry((float)daystocheck-1-i,(float)allRatingsTiredness1.get(i)));
            vals19.add(new Entry((float)daystocheck-1-i,(float)allRatingsBreathing1.get(i)));
            vals2s.add(new Entry((float)daystocheck-1-i,(float)allRatingsSick2.get(i)));
            vals21.add(new Entry((float)daystocheck-1-i,(float)allRatingsDiarrhea2.get(i)));
            vals22.add(new Entry((float)daystocheck-1-i,(float)allRatingsHeadache2.get(i)));
            vals23.add(new Entry((float)daystocheck-1-i,(float)allRatingsFever2.get(i)));
            vals24.add(new Entry((float)daystocheck-1-i,(float)allRatingsCough2.get(i)));
            vals25.add(new Entry((float)daystocheck-1-i,(float)allRatingsThroat2.get(i)));
            vals26.add(new Entry((float)daystocheck-1-i,(float)allRatingsRunnyNose2.get(i)));
            vals27.add(new Entry((float)daystocheck-1-i,(float)allRatingsNasalCon2.get(i)));
            vals28.add(new Entry((float)daystocheck-1-i,(float)allRatingsTiredness2.get(i)));
            vals29.add(new Entry((float)daystocheck-1-i,(float)allRatingsBreathing2.get(i)));
        }

        Collections.sort(vals1S,new EntryXComparator());
        Collections.sort(vals11,new EntryXComparator());
        Collections.sort(vals12,new EntryXComparator());
        Collections.sort(vals13,new EntryXComparator());
        Collections.sort(vals14,new EntryXComparator());
        Collections.sort(vals15,new EntryXComparator());
        Collections.sort(vals16,new EntryXComparator());
        Collections.sort(vals17,new EntryXComparator());
        Collections.sort(vals18,new EntryXComparator());
        Collections.sort(vals19,new EntryXComparator());
        Collections.sort(vals2s,new EntryXComparator());
        Collections.sort(vals21,new EntryXComparator());
        Collections.sort(vals22,new EntryXComparator());
        Collections.sort(vals23,new EntryXComparator());
        Collections.sort(vals24,new EntryXComparator());
        Collections.sort(vals25,new EntryXComparator());
        Collections.sort(vals26,new EntryXComparator());
        Collections.sort(vals27,new EntryXComparator());
        Collections.sort(vals28,new EntryXComparator());
        Collections.sort(vals29,new EntryXComparator());

        DataPointsDiarrhea1=new LineDataSet(vals11,"diarrea per day");
        DataPointsDiarrhea1.setColor(getContext().getResources().getColor(R.color.graph_orange));
        DataPointsDiarrhea1.setDrawValues(false);
        DataPointsDiarrhea1.setDrawCircles(false);
        DataPointsRunnyNose1=new LineDataSet(vals16,"Runnt nose per day");
        DataPointsRunnyNose1.setColor(getContext().getResources().getColor(R.color.graph_dark_purple));
        DataPointsRunnyNose1.setDrawValues(false);
        DataPointsRunnyNose1.setDrawCircles(false);
        DataPointsNasalCon1=new LineDataSet(vals17,"nasal con per day");
        DataPointsNasalCon1.setColor(getContext().getResources().getColor(R.color.graph_turquoise));
        DataPointsNasalCon1.setDrawValues(false);
        DataPointsNasalCon1.setDrawCircles(false);
        DataPointsBreathing1=new LineDataSet(vals19,"Breathing per day");
        DataPointsBreathing1.setColor(getContext().getResources().getColor(R.color.graph_pink));
        DataPointsBreathing1.setDrawValues(false);
        DataPointsBreathing1.setDrawCircles(false);
        DataPointsTiredness1=new LineDataSet(vals18,"Tiredness per day");
        DataPointsTiredness1.setColor(getContext().getResources().getColor(R.color.graph_brown));
        DataPointsTiredness1.setDrawValues(false);
        DataPointsTiredness1.setDrawCircles(false);
        DataPointsThroat1=new LineDataSet(vals15,"throat per day");
        DataPointsThroat1.setColor(getContext().getResources().getColor(R.color.graph_blue));
        DataPointsThroat1.setDrawValues(false);
        DataPointsThroat1.setDrawCircles(false);
        DataPointsFever1=new LineDataSet(vals13,"feverper day");
        DataPointsFever1.setColor(getContext().getResources().getColor(R.color.graph_red));
        DataPointsFever1.setDrawValues(false);
        DataPointsFever1.setDrawCircles(false);
        DataPointsCough1=new LineDataSet(vals14,"caugh per day");
        DataPointsCough1.setColor(getContext().getResources().getColor(R.color.graph_pink));
        DataPointsCough1.setDrawCircles(false);
        DataPointsCough1.setDrawValues(false);
        DataPointsHeadache1=new LineDataSet(vals12,"head per day");
        DataPointsHeadache1.setColor(getContext().getResources().getColor(R.color.graph_light_blue));
        DataPointsHeadache1.setDrawValues(false);
        DataPointsHeadache1.setDrawCircles(false);
        DataPoinsSick1=new LineDataSet(vals1S,"Sick per day");
        DataPoinsSick1.setColor(getContext().getResources().getColor(android.R.color.black));
        DataPointsDiarrhea2=new LineDataSet(vals21,"diarrea total");
        DataPointsDiarrhea2.setColor(getContext().getResources().getColor(R.color.graph_orange));
        DataPointsDiarrhea2.setDrawValues(false);
        DataPointsDiarrhea2.setDrawCircles(false);
        DataPointsRunnyNose2=new LineDataSet(vals26,"runny nose total");
        DataPointsRunnyNose2.setColor(getContext().getResources().getColor(R.color.graph_dark_purple));
        DataPointsRunnyNose2.setDrawValues(false);
        DataPointsRunnyNose2.setDrawCircles(false);
        DataPointsNasalCon2=new LineDataSet(vals27,"nasal con total");
        DataPointsNasalCon2.setColor(getContext().getResources().getColor(R.color.graph_turquoise));
        DataPointsNasalCon2.setDrawValues(false);
        DataPointsNasalCon2.setDrawCircles(false);
        DataPointsBreathing2=new LineDataSet(vals29,"breathing total");
        DataPointsBreathing2.setColor(getContext().getResources().getColor(R.color.graph_pink));
        DataPointsBreathing2.setDrawValues(false);
        DataPointsBreathing2.setDrawCircles(false);
        DataPointsTiredness2=new LineDataSet(vals28,"Tierdness total");
        DataPointsTiredness2.setColor(getContext().getResources().getColor(R.color.graph_brown));
        DataPointsTiredness2.setDrawValues(false);
        DataPointsTiredness2.setDrawCircles(false);
        DataPointsThroat2=new LineDataSet(vals25,"throat total");
        DataPointsThroat2.setColor(getContext().getResources().getColor(R.color.graph_blue));
        DataPointsThroat2.setDrawValues(false);
        DataPointsThroat2.setDrawCircles(false);
        DataPointsFever2=new LineDataSet(vals23,"fever total");
        DataPointsFever2.setColor(getContext().getResources().getColor(R.color.graph_red));
        DataPointsFever2.setDrawValues(false);
        DataPointsFever2.setDrawCircles(false);
        DataPointsCough2=new LineDataSet(vals24,"cough total");
        DataPointsCough2.setColor(getContext().getResources().getColor(R.color.graph_pink));
        DataPointsCough2.setDrawValues(false);
        DataPointsCough2.setDrawCircles(false);
        DataPointsHeadache2=new LineDataSet(vals22,"headeache total");
        DataPointsHeadache2.setColor(getContext().getResources().getColor(R.color.graph_light_blue));
        DataPointsHeadache2.setDrawValues(false);
        DataPointsHeadache2.setDrawCircles(false);
        DataPoinsSick2=new LineDataSet(vals2s,"diarrea total sick");
        DataPoinsSick2.setColor(getContext().getResources().getColor(android.R.color.black));
    }


    public void setuppalldata(){
        for (int i=0; i<daystocheck;i++){
            allRatingsBreathing1.add(0);
            allRatingsCough1.add(0);
            allRatingsFever1.add(0);
            allRatingsHeadache1.add(0);
            allRatingsNasalCon1.add(0);
            allRatingsRunnyNose1.add(0);
            allRatingsTiredness1.add(0);
            allRatingsThroat1.add(0);
            allRatingsSick1.add(0);
            allRatingsDiarrhea1.add(0);
            allRatingsBreathing2.add(0);
            allRatingsCough2.add(0);
            allRatingsFever2.add(0);
            allRatingsHeadache2.add(0);
            allRatingsNasalCon2.add(0);
            allRatingsRunnyNose2.add(0);
            allRatingsTiredness2.add(0);
            allRatingsThroat2.add(0);
            allRatingsDiarrhea2.add(0);
            allRatingsSick2.add(0);



    }}



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        diarrheaBox.setChecked(true);
        nasalConBox.setChecked(true);
        runnyNoseBox.setChecked(true);
        feverBox.setChecked(true);
        coughBox.setChecked(true);
        tirednessBox.setChecked(true);
        throatBox.setChecked(true);
        breathingDiffBox.setChecked(true);
        headacheBox.setChecked(true);
        allSickBox.setChecked(false);
        noSelectedDates = true;
        if (setuppdone) {


        if(text.equals((getString(R.string.per_day_symptoms)))){
            data= new LineData(DataSymtomPerDaySets);
            lineChart.setData(data);
            lineChart.invalidate();
            IsTotalSymtoms=false;
        }
        else
        {
        IsTotalSymtoms=true;
        data= new LineData(DataSymtomTotalSets);
        lineChart.setData(data);
        lineChart.invalidate();}
    }
}


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void countTable() {
        for(ArrayList user: userInfo){
            int age=Integer.parseInt((String) user.get(0));
            String gender=(String)user.get(1);

            if(gender.equals("male")){
                if(age >= 0 && age < 19) maleAge0To18 += 1;
                if(age > 18 && age < 41) maleAge19To40 += 1;
                if(age > 40 && age < 65) maleAge41To64 += 1;
                if(age > 64) maleAge65Plus += 1;
            }
            if(gender.equals("female")){
                if(age >= 0 && age < 19)femaleAge0To18 += 1;
                if(age > 18 && age < 41)femaleAge19To40 += 1;
                if(age > 40 && age < 65)femaleAge41To64 += 1;
                if(age > 64)femaleAge65Plus += 1;
            }
            if(gender.equals("other")){
                if(age >= 0 && age < 19)otherAge0To18 += 1;
                if(age > 18 && age < 41)otherAge19To40 += 1;
                if(age > 40 && age < 65)otherAge41To64 += 1;
                if(age > 64) otherAge65Plus += 1;


        }
    }
    }
    public void fillTable() {
        textView6.setText(String.valueOf(femaleAge0To18));
        textView7.setText(String.valueOf(maleAge0To18));
        textView8.setText(String.valueOf(otherAge0To18));
        textView10.setText(String.valueOf(femaleAge19To40));
        textView11.setText(String.valueOf(maleAge19To40));
        textView12.setText(String.valueOf(otherAge19To40));
        textView14.setText(String.valueOf(femaleAge41To64));
        textView15.setText(String.valueOf(maleAge41To64));
        textView16.setText(String.valueOf(otherAge41To64));
        textView18.setText(String.valueOf(femaleAge65Plus));
        textView19.setText(String.valueOf(maleAge65Plus));
        textView20.setText(String.valueOf(otherAge65Plus));
    }
    public String convertStringPrintToDataFormat(String string){
        String[] parts = string.split("-");
        if((Integer.parseInt(parts[1])+1)<10){
        return (Integer.parseInt(parts[2]))+"-"+"0"+(Integer.parseInt(parts[1]))+"-"+parts[0];}
        return (Integer.parseInt(parts[2]))+"-"+(Integer.parseInt(parts[1]))+"-"+parts[0];
    }


    public int daysago(String day){

        try {
            Date d1=sdf.parse(convertStringPrintToDataFormat(day));
            return (int)( (today.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getdisplaydate(int dayOfInterestLast60Days) {
        today = new Date();
        calendarStat = (GregorianCalendar) Calendar.getInstance();
        calendarStat.setTime(today);
        calendarStat.add(Calendar.DAY_OF_MONTH, -dayOfInterestLast60Days);
        dateOfInterest = calendarStat.getTime();
        sdfDate = sdp.format(dateOfInterest);
        return sdfDate;
    }

}