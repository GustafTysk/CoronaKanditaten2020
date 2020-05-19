package com.example.coronakanditaten2020;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class StatisticsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "Fragment Statistics";

    private Button btnStatisticsToStart;
    private Button btnStatisticsToHeatmap;
    private ArrayList<Location> Locations = new ArrayList<>();
    private ArrayList<User> Users = new ArrayList<>();
    private ArrayList<String> Dates = new ArrayList<>();
    private HashSet<String> uniqueDates;
    private ArrayList<String> uniqueArrayListDates;
    private ArrayList<Integer> numberDates;
    private ArrayList<Integer> allRatingsDiarrhea, allRatingsRunnyNose, allRatingsNasalCon, allRatingsBreathing,
            allRatingsTiredness, allRatingsHeadache, allRatingsFever, allRatingsCough, allRatingsThroat;


    public CheckBox diarrheaBox, runnyNoseBox, nasalConBox, headacheBox, throatBox, breathingDiffBox, tirednessBox, coughBox, feverBox;

    //GRAPH AND SERIES
    private GraphView graph;
    private LineGraphSeries series, series2, series3, series4, series5, series6, series7, series8, series9, seriesb, series2b, series3b, series4b, series5b, series6b, series7b, series8b, series9b;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private boolean series1exist = false;
    private boolean notCounted = true;
    //String getDateForCountAllSymptoms;

    //ALLA SYMPTOM
    private int diarrhea29, diarrhea30, diarrhea31, diarrhea32, runnyNose29, runnyNose30, runnyNose31, runnyNose32, nasalCongestion29, nasalCongestion30, nasalCongestion31,
            nasalCongestion32, headache29, headache30, headache31, headache32, throat29, throat30, throat31, throat32, breathing29, breathing30, breathing31, breathing32,
            tiredness29, tiredness30, tiredness31, tiredness32, cough29, cough30, cough31, cough32, fever29, fever30, fever31, fever32, largest, largest2, diarrhea, nasalCongestion,
            breathing, headache, fever, cough, tiredness, runnyNose, throat, maleAge0To18, maleAge19To40, maleAge41To64, maleAge65Plus, femaleAge0To18, femaleAge19To40, femaleAge41To64,
            femaleAge65Plus, otherAge0To18, otherAge19To40, otherAge41To64, otherAge65Plus;

    //CALENDAR
    private Boolean noSelectedDates = true;
    private GregorianCalendar calendarStat;
    private Date d1, d2, d3, d4;
    private ImageButton setCalendarLocation1;
    private com.applandeo.materialcalendarview.CalendarView cal;
    private List<Calendar> calendars;
    private Calendar maxDate = Calendar.getInstance(TimeZone.getDefault());
    private Date minCalendarValue, maxCalendarValue;
    Date dateOfInterest;
    SimpleDateFormat sdf;
    String sdfDate;

    //TABLE VIEW
    TableLayout tableLayout;
    TableRow tableRow1, tableRow2, tableRow3, tableRow4, tableRow5;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10,
    textView11, textView12, textView13, textView14, textView15, textView16, textView17, textView18, textView19, textView20;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //((MainActivity)getActivity()).datahandler.heatlocations;
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        /*btnStatisticsToStart = (Button) view.findViewById(R.id.btnStatisticsToStart);
        btnStatisticsToStart.setOnClickListener(this);
        btnStatisticsToHeatmap = (Button) view.findViewById(R.id.btnStatisticsToHeatmap);
        btnStatisticsToHeatmap.setOnClickListener(this);*/

        diarrheaBox = (CheckBox) view.findViewById(R.id.diarrheaBox);
        runnyNoseBox = (CheckBox) view.findViewById(R.id.runnyNoseBox);
        tirednessBox = (CheckBox) view.findViewById(R.id.tirednessBox);
        feverBox = (CheckBox) view.findViewById(R.id.feverBox);
        throatBox = (CheckBox) view.findViewById(R.id.throatBox);
        nasalConBox = (CheckBox) view.findViewById(R.id.nasalConBox);
        coughBox = (CheckBox) view.findViewById(R.id.coughBox);
        headacheBox = (CheckBox) view.findViewById(R.id.headacheBox);
        breathingDiffBox = (CheckBox) view.findViewById(R.id.breathingDiffBox);

        graph = (GraphView) view.findViewById(R.id.graph1);

        setCalendarLocation1 = (ImageButton) view.findViewById(R.id.setCalendarLocation1);
        setCalendarLocation1.setOnClickListener(this);

        spinner = view.findViewById(R.id.spinner1);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        textView9 = (TextView) view.findViewById(R.id.textView9);
        textView10 = (TextView) view.findViewById(R.id.textView10);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        textView12 = (TextView) view.findViewById(R.id.textView12);
        textView13 = (TextView) view.findViewById(R.id.textView13);
        textView14 = (TextView) view.findViewById(R.id.textView14);
        textView15 = (TextView) view.findViewById(R.id.textView15);
        textView16 = (TextView) view.findViewById(R.id.textView16);
        textView17 = (TextView) view.findViewById(R.id.textView17);
        textView18 = (TextView) view.findViewById(R.id.textView18);
        textView19 = (TextView) view.findViewById(R.id.textView19);
        textView20 = (TextView) view.findViewById(R.id.textView20);


        User user = new User("Tjalexander1", "alewik97@gmail.com", 15, "male", "IDFJJKdjk", "2020-04-29");
        Users.add(user);
        User user2 = new User("Tjalexander1", "alewik97@gmail.com", 25, "female", "IDFJJKdjk", "2020-04-29");
        Users.add(user2);
        User user3 = new User("Tjalexander1", "alewik97@gmail.com", 9, "other", "IDFJJKdjk", "2020-04-29");
        Users.add(user3);
        User user4 = new User("Tjalexander1", "alewik97@gmail.com", 69, "male", "IDFJJKdjk", "2020-04-29");
        Users.add(user4);
        User user5 = new User("Tjalexander1", "alewik97@gmail.com", 54, "other", "IDFJJKdjk", "2020-04-29");
        Users.add(user5);
        User user6 = new User("Tjalexander1", "alewik97@gmail.com", 30, "female", "IDFJJKdjk", "2020-04-29");
        Users.add(user6);
        User user7 = new User("Tjalexander1", "alewik97@gmail.com", 37, "female", "IDFJJKdjk", "2020-04-29");
        Users.add(user7);
        User user8 = new User("Tjalexander1", "alewik97@gmail.com", 82, "male", "IDFJJKdjk", "2020-04-29");
        Users.add(user8);
        User user9 = new User("Tjalexander1", "alewik97@gmail.com", 91, "other", "IDFJJKdjk", "2020-04-29");
        Users.add(user9);
        User user10 = new User("Tjalexander1", "alewik97@gmail.com", 23, "female", "IDFJJKdjk", "2020-04-29");
        Users.add(user10);
        User user11 = new User("Tjalexander1", "alewik97@gmail.com", 1, "male", "IDFJJKdjk", "2020-04-29");
        Users.add(user11);
        User user12 = new User("Tjalexander1", "alewik97@gmail.com", 65, "female", "IDFJJKdjk", "2020-04-29");
        Users.add(user12);


        Location location = new Location("59.858562", "17.638927", "2020-04-29", 0, 0, 2, 0, 3, 0, 1, 1, 0, 2, "2020-04-31");
        Locations.add(location);
        Location location2 = new Location("59.858562", "17.638927", "2020-05-01", 1, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-04-30");
        Locations.add(location2);
        Location location3 = new Location("59.858562", "17.638927", "2020-05-02", 2, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-04-30");
        Locations.add(location3);
        Location location4 = new Location("59.858562", "17.638927", "2020-04-29", 3, 3, 2, 0, 1, 3, 1, 1, 0, 3, "2020-04-31");
        Locations.add(location4);
        Location location5 = new Location("59.858562", "17.638927", "2020-04-30", 4, 0, 0, 3, 3, 2, 1, 2, 0, 2, "2020-04-30");
        Locations.add(location5);
        Location location6 = new Location("59.858562", "17.638927", "2020-04-30", 5, 0, 3, 0, 2, 2, 1, 3, 1, 1, "2020-04-29");
        Locations.add(location6);
        Location location7 = new Location("59.858562", "17.638927", "2020-05-01", 6, 2, 0, 1, 3, 1, 1, 1, 0, 0, "2020-04-29");
        Locations.add(location7);
        Location location8 = new Location("59.858562", "17.638927", "2020-04-30", 7, 3, 0, 2, 1, 2, 1, 2, 0, 3, "2020-04-30");
        Locations.add(location8);
        Location location9 = new Location("59.858562", "17.638927", "2020-04-29", 8, 0, 0, 2, 3, 1, 1, 3, 3, 2, "2020-04-29");
        Locations.add(location9);
        Location location10 = new Location("59.858562", "17.638927", "2020-05-01", 9, 1, 2, 0, 0, 0, 1, 1, 0, 1, "2020-04-29");
        Locations.add(location10);
        Location location11 = new Location("59.858562", "17.638927", "2020-04-29", 10, 2, 1, 0, 3, 3, 1, 1, 0, 2, "2020-04-30");
        Locations.add(location11);
        Location location12 = new Location("59.858562", "17.638927", "2020-05-01", 11, 3, 2, 0, 1, 0, 1, 1, 1, 3, "2020-04-29");
        Locations.add(location12);
        getSymptomValuesLast60DaysDiarrhea();
        getSymptomValuesLast60DaysBreathing();
        getSymptomValuesLast60DaysCough();
        getSymptomValuesLast60DaysHeadache();
        getSymptomValuesLast60DaysFever();
        getSymptomValuesLast60DaysNasalCon();
        getSymptomValuesLast60DaysRunnyNose();
        getSymptomValuesLast60DaysThroat();
        getSymptomValuesLast60DaysTiredness();

        if(noSelectedDates == true) {
            createCalendar(60);
        }
        if(series1exist == false) {
            makeGraphLines1();
            addAllSeries1();
            designSeriesA();
        }
        else {
            makeGraphLines2();
            addAllSeries2();
            designSeriesb();
        }

        countTable();
        fillTable();
        return view;
    }

    public void setStatisticsBottomNav(){
        ((MainActivity) requireActivity()).bottomNav = (BottomNavigationView) getView().findViewById(R.id.bottom_navigation);
        ((MainActivity) requireActivity()).bottomNav.setOnNavigationItemSelectedListener(((MainActivity) getActivity()).navListener);
        ((MainActivity) requireActivity()).bottomNav.getMenu().findItem(R.id.nav_statistics).setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
/*            case R.id.btnStatisticsToHeatmap:
                Toast.makeText(getActivity(), "Going to Heatmap", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(3);
                break;
            case R.id.btnStatisticsToStart:
                Toast.makeText(getActivity(), "Going to Start Page", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(1);
                break;*/
            case R.id.setCalendarLocation1:
                try {
                    getCalendarView(1);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void showDiarrheaSeries() {
        graph.addSeries(series);
    }

    public void showDiarrheaSeries2() {
        graph.addSeries(seriesb);
    }

    public void hideDiarrheaSeries () {
        graph.removeSeries(series);
    }

    public void hideDiarrheaSeries2() {
        graph.removeSeries(seriesb);
    }

    public void showRunnyNoseSeries() {
        graph.addSeries(series2);
    }
    public void showRunnyNoseSeries2() {
        graph.addSeries(series2b);
    }

    public void hideRunnyNoseSeries () {
        graph.removeSeries(series2);
    }
    public void hideRunnyNoseSeries2() {
        graph.removeSeries(series2b);
    }

    public void showTirednessSeries() {
        graph.addSeries(series3);
    }
    public void showTirednessSeries2() {
        graph.addSeries(series3b);
    }

    public void hideTirednessSeries() {
        graph.removeSeries(series3);
    }

    public void hideTirednessSeries2() {
        graph.removeSeries(series3b);
    }
    public void showFeverSeries() {
        graph.addSeries(series4);
    }
    public void hideFeverSeries() {
        graph.removeSeries(series4);
    }
    public void showThroatSeries() {
        graph.addSeries(series5);
    }
    public void hideThroatSeries() {
        graph.removeSeries(series5);
    }
    public void showNasalConSeries() {
        graph.addSeries(series6);
    }
    public void hideNasalConSeries() {
        graph.removeSeries(series6);
    }
    public void showCoughSeries() {
        graph.addSeries(series7);
    }
    public void hideCoughSeries() {
        graph.removeSeries(series7);
    }
    public void showHeadacheSeries() {
        graph.addSeries(series8);
    }
    public void hideHeadacheSeries() {
        graph.removeSeries(series8);
    }
    public void showBreathingDiffSeries() {
        graph.addSeries(series9);
    }
    public void hideBreathingDiffSeries() {
        graph.removeSeries(series9);
    }
    public void showFeverSeries2() {
        graph.addSeries(series4b);
    }
    public void hideFeverSeries2() {
        graph.removeSeries(series4b);
    }
    public void showThroatSeries2() {
        graph.addSeries(series5b);
    }
    public void hideThroatSeries2() {
        graph.removeSeries(series5b);
    }
    public void showNasalConSeries2() {
        graph.addSeries(series6b);
    }
    public void hideNasalConSeries2() {
        graph.removeSeries(series6b);
    }
    public void showCoughSeries2() {
        graph.addSeries(series7b);
    }
    public void hideCoughSeries2() {
        graph.removeSeries(series7b);
    }
    public void showHeadacheSeries2() {
        graph.addSeries(series8b);
    }
    public void hideHeadacheSeries2() {
        graph.removeSeries(series8b);
    }
    public void showBreathingDiffSeries2() {
        graph.addSeries(series9b);
    }
    public void hideBreathingDiffSeries2() {
        graph.removeSeries(series9b);
    }

    public Date createCalendar(int dayOfInterestLast60Days) {
        Date today = new Date();
        calendarStat = (GregorianCalendar) Calendar.getInstance();
        calendarStat.setTime(today);
        calendarStat.add(Calendar.DAY_OF_MONTH, -dayOfInterestLast60Days);
        Date dateOfInterest = calendarStat.getTime();
        return dateOfInterest;
    }

    public String createCalendarSdf(int dayOfInterestLast60Days) {
        Date today = new Date();
        calendarStat = (GregorianCalendar) Calendar.getInstance();
        calendarStat.setTime(today);
        calendarStat.add(Calendar.DAY_OF_MONTH, -dayOfInterestLast60Days);
        dateOfInterest = calendarStat.getTime();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdfDate = sdf.format(dateOfInterest);
        return sdfDate;
    }

    public void getCalendarView(final Integer location) throws OutOfDateRangeException {
        OnSelectDateListener listener = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                noSelectedDates = false;
                clearGraph();
                minCalendarValue = calendars.get(0).getTime();
                maxCalendarValue = calendars.get(calendars.size()-1).getTime();

                if(series1exist == true){
                    addAllSeries1();
                    designSeriesA();
                }
                else{
                    addAllSeries2();
                    designSeriesb();
                }
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(minCalendarValue.getTime());
                graph.getViewport().setMaxX(maxCalendarValue.getTime());

            }
        };

        DatePickerBuilder builder = new DatePickerBuilder(getContext(), listener)
                .pickerType(cal.RANGE_PICKER).setMaximumDate(maxDate);
        if (calendars != null)
            builder.setSelectedDays(calendars);

        DatePicker datePicker = builder.build();
        datePicker.show();
    }

    public void addAllSeries1(){
        graph.addSeries(series);
        graph.addSeries(series2);
        graph.addSeries(series3);
        graph.addSeries(series4);
        graph.addSeries(series5);
        graph.addSeries(series6);
        graph.addSeries(series7);
        graph.addSeries(series8);
        graph.addSeries(series9);
        series1exist = true;
    }

    public void addAllSeries2(){
        graph.addSeries(seriesb);
        graph.addSeries(series2b);
        graph.addSeries(series3b);
        graph.addSeries(series4b);
        graph.addSeries(series5b);
        graph.addSeries(series6b);
        graph.addSeries(series7b);
        graph.addSeries(series8b);
        graph.addSeries(series9b);
        series1exist = false;
    }

    public void clearGraph(){
        graph.removeAllSeries();
    }


    public ArrayList<Integer> getSymptomValuesLast60DaysDiarrhea(){
        allRatingsDiarrhea = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getDiarrheaRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsDiarrhea.add(symptomCounter);
            symptomCounter = 0;
        }
        System.out.println("allratingsdiarrhea" + allRatingsDiarrhea);
        return allRatingsDiarrhea;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysRunnyNose(){
        allRatingsRunnyNose = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getRunnyNoseRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsRunnyNose.add(symptomCounter);
            symptomCounter = 0;
        }
        System.out.println("allratingsrunny" + allRatingsRunnyNose);
        return allRatingsRunnyNose;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysNasalCon(){
        allRatingsNasalCon = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getNasalCongestionRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsNasalCon.add(symptomCounter);
            symptomCounter = 0;
        }
        return allRatingsNasalCon;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysBreathing(){
        allRatingsBreathing = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getBreathingRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsBreathing.add(symptomCounter);
            symptomCounter = 0;
        }
        return allRatingsBreathing;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysTiredness(){
        allRatingsTiredness = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getTirednessRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsTiredness.add(symptomCounter);
            symptomCounter = 0;
            System.out.println("trött" + allRatingsTiredness);
        }
        return allRatingsTiredness;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysThroat(){
        allRatingsThroat = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getThroatRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsThroat.add(symptomCounter);
            symptomCounter = 0;
        }
        return allRatingsThroat;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysFever(){
        allRatingsFever = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getFeverRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsFever.add(symptomCounter);
            symptomCounter = 0;
        }
        return allRatingsFever;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysCough(){
        allRatingsCough = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getCoughRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsCough.add(symptomCounter);
            symptomCounter = 0;
        }
        return allRatingsCough;
    }

    public ArrayList<Integer> getSymptomValuesLast60DaysHeadache(){
        allRatingsHeadache = new ArrayList<>();
        String tempString="";
        int symptomCounter = 0;
        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                if(Locations.get(i).getDate().equals(tempString)){
                    if(Locations.get(i).getHeadacheRatingBar() > 0){
                        symptomCounter++;
                    }
                }
            }
            allRatingsHeadache.add(symptomCounter);
            symptomCounter = 0;
        }
        return allRatingsHeadache;
    }

    public Integer countForGraph2(int countForGraph2, ArrayList<Integer> symptomList) {
        int countForCertainDay = 0;
        for(int i = 59; i>countForGraph2; i--){
            countForCertainDay = countForCertainDay + symptomList.get(i);
        }
        return countForCertainDay;
    }



    public void makeGraphLines1(){
        System.out.println("halloj");
        ArrayList<DataPoint> dataPointsDiarrhea = new ArrayList<>();
        ArrayList<DataPoint> dataPointsRunnyNose = new ArrayList<>();
        ArrayList<DataPoint> dataPointsNasalCon= new ArrayList<>();
        ArrayList<DataPoint> dataPointsBreathing = new ArrayList<>();
        ArrayList<DataPoint> dataPointsTiredness = new ArrayList<>();
        ArrayList<DataPoint> dataPointsThroat = new ArrayList<>();
        ArrayList<DataPoint> dataPointsFever = new ArrayList<>();
        ArrayList<DataPoint> dataPointsCough = new ArrayList<>();
        ArrayList<DataPoint> dataPointsHeadache = new ArrayList<>();
        for(int i=59; i>=0; i--){
            dataPointsDiarrhea.add(new DataPoint(createCalendar(i), allRatingsDiarrhea.get(i)));
            dataPointsRunnyNose.add(new DataPoint(createCalendar(i), allRatingsRunnyNose.get(i)));
            dataPointsNasalCon.add(new DataPoint(createCalendar(i), allRatingsNasalCon.get(i)));
            dataPointsBreathing.add(new DataPoint(createCalendar(i), allRatingsBreathing.get(i)));
            dataPointsTiredness.add(new DataPoint(createCalendar(i), allRatingsTiredness.get(i)));
            dataPointsThroat.add(new DataPoint(createCalendar(i), allRatingsThroat.get(i)));
            dataPointsFever.add(new DataPoint(createCalendar(i), allRatingsFever.get(i)));
            dataPointsCough.add(new DataPoint(createCalendar(i), allRatingsCough.get(i)));
            dataPointsHeadache.add(new DataPoint(createCalendar(i), allRatingsHeadache.get(i)));
        }
        DataPoint[] dpDiarrhea = new DataPoint[60];
        DataPoint[] dpRunnyNose = new DataPoint[60];
        DataPoint[] dpNasalCon = new DataPoint[60];
        DataPoint[] dpBreathing = new DataPoint[60];
        DataPoint[] dpTiredness = new DataPoint[60];
        DataPoint[] dpThroat = new DataPoint[60];
        DataPoint[] dpFever = new DataPoint[60];
        DataPoint[] dpCough = new DataPoint[60];
        DataPoint[] dpHeadache = new DataPoint[60];
        series = new LineGraphSeries<DataPoint>(dataPointsDiarrhea.toArray(dpDiarrhea));
        series2 = new LineGraphSeries<DataPoint>(dataPointsRunnyNose.toArray(dpRunnyNose));
        series3 = new LineGraphSeries<DataPoint>(dataPointsNasalCon.toArray(dpNasalCon));
        series4 = new LineGraphSeries<DataPoint>(dataPointsBreathing.toArray(dpBreathing));
        series5 = new LineGraphSeries<DataPoint>(dataPointsTiredness.toArray(dpTiredness));
        series6 = new LineGraphSeries<DataPoint>(dataPointsThroat.toArray(dpThroat));
        series7 = new LineGraphSeries<DataPoint>(dataPointsFever.toArray(dpFever));
        series8 = new LineGraphSeries<DataPoint>(dataPointsCough.toArray(dpCough));
        series9 = new LineGraphSeries<DataPoint>(dataPointsHeadache.toArray(dpHeadache));
    }

    public void makeGraphLines2(){
        ArrayList<DataPoint> dataPointsDiarrhea = new ArrayList<>();
        ArrayList<DataPoint> dataPointsRunnyNose = new ArrayList<>();
        ArrayList<DataPoint> dataPointsNasalCon= new ArrayList<>();
        ArrayList<DataPoint> dataPointsBreathing = new ArrayList<>();
        ArrayList<DataPoint> dataPointsTiredness = new ArrayList<>();
        ArrayList<DataPoint> dataPointsThroat = new ArrayList<>();
        ArrayList<DataPoint> dataPointsFever = new ArrayList<>();
        ArrayList<DataPoint> dataPointsCough = new ArrayList<>();
        ArrayList<DataPoint> dataPointsHeadache = new ArrayList<>();
        for(int i=59; i>=0; i--){
            dataPointsDiarrhea.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsDiarrhea)));
            System.out.println("datapointdiarrhea" + countForGraph2(i,allRatingsDiarrhea));
            dataPointsRunnyNose.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsRunnyNose)));
            System.out.println("datapointsrunnynose" + countForGraph2(i,allRatingsRunnyNose));
            dataPointsNasalCon.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsNasalCon)));
            dataPointsBreathing.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsBreathing)));
            dataPointsTiredness.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsTiredness)));
            dataPointsThroat.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsThroat)));
            dataPointsFever.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsFever)));
            dataPointsCough.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsCough)));
            dataPointsHeadache.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsHeadache)));
        }
        DataPoint[] dpDiarrhea = new DataPoint[60];
        DataPoint[] dpRunnyNose = new DataPoint[60];
        DataPoint[] dpNasalCon = new DataPoint[60];
        DataPoint[] dpBreathing = new DataPoint[60];
        DataPoint[] dpTiredness = new DataPoint[60];
        DataPoint[] dpThroat = new DataPoint[60];
        DataPoint[] dpFever = new DataPoint[60];
        DataPoint[] dpCough = new DataPoint[60];
        DataPoint[] dpHeadache = new DataPoint[60];
        seriesb = new LineGraphSeries<DataPoint>(dataPointsDiarrhea.toArray(dpDiarrhea));
        series2b = new LineGraphSeries<DataPoint>(dataPointsRunnyNose.toArray(dpRunnyNose));
        series3b = new LineGraphSeries<DataPoint>(dataPointsNasalCon.toArray(dpNasalCon));
        series4b = new LineGraphSeries<DataPoint>(dataPointsBreathing.toArray(dpBreathing));
        series5b = new LineGraphSeries<DataPoint>(dataPointsTiredness.toArray(dpTiredness));
        series6b = new LineGraphSeries<DataPoint>(dataPointsThroat.toArray(dpThroat));
        series7b = new LineGraphSeries<DataPoint>(dataPointsFever.toArray(dpFever));
        series8b = new LineGraphSeries<DataPoint>(dataPointsCough.toArray(dpCough));
        series9b = new LineGraphSeries<DataPoint>(dataPointsHeadache.toArray(dpHeadache));
    }

    public void designSeriesA() {
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        //graph.getViewport().setScrollable(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(calculateHighestValA());

        graph.getGridLabelRenderer().setNumVerticalLabels(calculateHighestValA() + 1);

        graph.setTitle(getString(R.string.per_day_symptoms));
        graph.setTitleTextSize(80);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.MIDDLE);
        graph.getLegendRenderer().setTextSize(35f);

        series.setTitle(getString(R.string.symptom_diarrhea));
        series2.setTitle(getString(R.string.symptom_runny_nose));
        series3.setTitle(getString(R.string.symptom_tiredness));
        series4.setTitle(getString(R.string.symptom_fever));
        series5.setTitle(getString(R.string.symptom_sore_throat));
        series6.setTitle(getString(R.string.symptom_nasal_congestion));
        series7.setTitle(getString(R.string.symptom_cough));
        series8.setTitle(getString(R.string.symptom_headache));
        series9.setTitle(getString(R.string.symptom_breathing));
        series.setColor(Color.BLACK);
        series2.setColor(Color.GREEN);
        series3.setColor(Color.YELLOW);
        series4.setColor(Color.RED);
        series5.setColor(Color.BLUE);
        series6.setColor(Color.MAGENTA);
        series7.setColor(Color.DKGRAY);
        series8.setColor(Color.CYAN);
        series9.setColor(Color.GRAY);
        series.setDrawDataPoints(true);
        series2.setDrawDataPoints(true);
        series3.setDrawDataPoints(true);
        series4.setDrawDataPoints(true);
        series5.setDrawDataPoints(true);
        series6.setDrawDataPoints(true);
        series7.setDrawDataPoints(true);
        series8.setDrawDataPoints(true);
        series9.setDrawDataPoints(true);

    }

    public void designSeriesb() {
        //graph.getViewport().setScrollable(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(calculateHighestValB());

        graph.getGridLabelRenderer().setNumVerticalLabels(calculateHighestValB() + 1);
        graph.setTitle(getString(R.string.total_symptoms));
        graph.setTitleTextSize(80);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.MIDDLE);
        graph.getLegendRenderer().setTextSize(35f);

        seriesb.setTitle(getString(R.string.symptom_diarrhea));
        series2b.setTitle(getString(R.string.symptom_runny_nose));
        series3b.setTitle(getString(R.string.symptom_tiredness));
        series4b.setTitle(getString(R.string.symptom_fever));
        series5b.setTitle(getString(R.string.symptom_sore_throat));
        series6b.setTitle(getString(R.string.symptom_nasal_congestion));
        series7b.setTitle(getString(R.string.symptom_cough));
        series8b.setTitle(getString(R.string.symptom_headache));
        series9b.setTitle(getString(R.string.symptom_breathing));
        seriesb.setColor(Color.BLACK);
        series2b.setColor(Color.GREEN);
        series3b.setColor(Color.YELLOW);
        series4b.setColor(Color.RED);
        series5b.setColor(Color.BLUE);
        series6b.setColor(Color.MAGENTA);
        series7b.setColor(Color.DKGRAY);
        series8b.setColor(Color.CYAN);
        series9b.setColor(Color.GRAY);
        seriesb.setDrawDataPoints(true);
        series2b.setDrawDataPoints(true);
        series3b.setDrawDataPoints(true);
        series4b.setDrawDataPoints(true);
        series5b.setDrawDataPoints(true);
        series6b.setDrawDataPoints(true);
        series7b.setDrawDataPoints(true);
        series8b.setDrawDataPoints(true);
        series9b.setDrawDataPoints(true);
    }

    public Integer calculateHighestValA() {
        largest = 0;
        if(Collections.max(allRatingsDiarrhea) > largest) {
            largest = Collections.max(allRatingsDiarrhea);
        }
        if(Collections.max(allRatingsRunnyNose) > largest) {
            largest = Collections.max(allRatingsRunnyNose);
        }
        if(Collections.max(allRatingsNasalCon) > largest) {
            largest = Collections.max(allRatingsNasalCon);
        }
        if(Collections.max(allRatingsBreathing) > largest) {
            largest = Collections.max(allRatingsBreathing);
        }
        if(Collections.max(allRatingsTiredness) > largest) {
            largest = Collections.max(allRatingsTiredness);
        }
        if(Collections.max(allRatingsThroat) > largest) {
            largest = Collections.max(allRatingsThroat);
        }
        if(Collections.max(allRatingsFever) > largest) {
            largest = Collections.max(allRatingsFever);
        }
        if(Collections.max(allRatingsCough) > largest) {
            largest = Collections.max(allRatingsCough);
        }
        if(Collections.max(allRatingsHeadache) > largest) {
            largest = Collections.max(allRatingsHeadache);
        }
        return largest;
    }

    public Integer calculateHighestValB() {
        largest2 = 0;
        if(countForGraph2(0,allRatingsDiarrhea) > largest) {
            largest2 = countForGraph2(0,allRatingsDiarrhea);
        }
        if(countForGraph2(0,allRatingsRunnyNose) > largest) {
            largest2 = countForGraph2(0,allRatingsRunnyNose);
        }
        if(countForGraph2(0,allRatingsNasalCon) > largest) {
            largest2 = countForGraph2(0,allRatingsNasalCon);
        }
        if(countForGraph2(0,allRatingsBreathing) > largest) {
            largest2 = countForGraph2(0,allRatingsBreathing);
        }
        if(countForGraph2(0,allRatingsTiredness) > largest) {
            largest2 = countForGraph2(0,allRatingsTiredness);
        }
        if(countForGraph2(0,allRatingsThroat) > largest) {
            largest2 = countForGraph2(0,allRatingsThroat);
        }
        if(countForGraph2(0,allRatingsFever) > largest) {
            largest2 = countForGraph2(0,allRatingsFever);
        }
        if(countForGraph2(0,allRatingsCough) > largest) {
            largest2 = countForGraph2(0,allRatingsCough);
        }
        if(countForGraph2(0,allRatingsHeadache) > largest) {
            largest2 = countForGraph2(0,allRatingsHeadache);
        }
        return largest2;
    }

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
        noSelectedDates = true;

        if(text.equals(getString(R.string.per_day_symptoms))){
            createCalendar(60);
            clearGraph();
            makeGraphLines1();
            addAllSeries1();
            designSeriesA();
        }
        else{
            createCalendar(60);
            clearGraph();
            makeGraphLines2();
            addAllSeries2();
            designSeriesb();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void countTable() {
        for(User user: Users){
            if(user.gender.equals("male")){
                if(user.age >= 0 && user.age < 19){
                    maleAge0To18 += 1;
                }
                if(user.age > 18 && user.age < 41){
                    maleAge19To40 += 1;
                }
                if(user.age > 40 && user.age < 65){
                    maleAge41To64 += 1;
                }
                if(user.age > 64){
                    maleAge65Plus += 1;
                }
            }
            if(user.gender.equals("female")){
                if(user.age >= 0 && user.age < 19){
                    femaleAge0To18 += 1;
                }
                if(user.age > 18 && user.age < 41){
                    femaleAge19To40 += 1;
                }
                if(user.age > 40 && user.age < 65){
                    femaleAge41To64 += 1;
                }
                if(user.age > 64){
                    femaleAge65Plus += 1;
                }
            }
            if(user.gender.equals("other")){
                if(user.age >= 0 && user.age < 19){
                    otherAge0To18 += 1;
                }
                if(user.age > 18 && user.age < 41){
                    otherAge19To40 += 1;
                }
                if(user.age > 40 && user.age < 65){
                    otherAge41To64 += 1;
                }
                if(user.age > 64){
                    otherAge65Plus += 1;
                }
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

}