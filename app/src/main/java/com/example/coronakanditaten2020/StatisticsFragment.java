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
import java.util.Arrays;
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

    private ArrayList<Location> Locations = new ArrayList<>();
    private ArrayList<User> Users = new ArrayList<>();
    private ArrayList<Integer> allRatingsDiarrhea=new ArrayList<Integer>(), allRatingsRunnyNose=new ArrayList<Integer>(),
            allRatingsNasalCon=new ArrayList<Integer>(), allRatingsBreathing=new ArrayList<Integer>(),
            allRatingsTiredness=new ArrayList<Integer>(), allRatingsHeadache=new ArrayList<Integer>(),
            allRatingsFever=new ArrayList<Integer>(), allRatingsCough=new ArrayList<Integer>(), allRatingsThroat=new ArrayList<Integer>(), allRatingsSick=new ArrayList<Integer>();
    private DataPoint[] dpDiarrhea, dpRunnyNose, dpNasalCon, dpBreathing, dpTiredness, dpThroat, dpFever, dpCough, dpHeadache;
    private ArrayList<ArrayList<String>> userInfo;


    public CheckBox diarrheaBox, runnyNoseBox, nasalConBox, headacheBox, throatBox, breathingDiffBox, tirednessBox, coughBox, feverBox, allSickBox;

    //GRAPH AND SERIES
    GraphView graph;
    private LineGraphSeries series, series2, series3, series4, series5, series6, series7, series8, series9, series10,
            seriesb, series2b, series3b, series4b, series5b, series6b, series7b, series8b, series9b, series10b;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private boolean series1exist = false;

    //DATAPOINTS GRAPH
    private ArrayList<DataPoint> dataPointsDiarrhea;
    private ArrayList<DataPoint> dataPointsRunnyNose;
    private ArrayList<DataPoint> dataPointsNasalCon;
    private ArrayList<DataPoint> dataPointsBreathing;
    private ArrayList<DataPoint> dataPointsTiredness;
    private ArrayList<DataPoint> dataPointsThroat;
    private ArrayList<DataPoint> dataPointsFever;
    private ArrayList<DataPoint> dataPointsCough;
    private ArrayList<DataPoint> dataPointsHeadache;

    //ALLA SYMPTOM
    private int largest, largest2, maleAge0To18, maleAge19To40, maleAge41To64, maleAge65Plus, femaleAge0To18, femaleAge19To40, femaleAge41To64,
            femaleAge65Plus, otherAge0To18, otherAge19To40, otherAge41To64, otherAge65Plus;

    private String tempString;
    private ArrayList<Integer> Symtomcounter=new ArrayList<Integer>();

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
    SimpleDateFormat sdf;
    String sdfDate;
    View view;

    //TABLE VIEW
    private TextView textView6, textView7, textView8, textView10, textView11, textView12,
            textView14, textView15, textView16, textView18, textView19, textView20;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        return view;
    }

    public void setStatisticsBottomNav(){
        ((MainActivity) requireActivity()).bottomNav = (BottomNavigationView) getView().findViewById(R.id.bottom_navigation);
        ((MainActivity) requireActivity()).bottomNav.setOnNavigationItemSelectedListener(((MainActivity) getActivity()).navListener);
        ((MainActivity) requireActivity()).bottomNav.getMenu().findItem(R.id.nav_statistics).setChecked(true);

    }

    public void setupstatistik(){
        System.out.println("started setupp");
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

        graph = (GraphView) view.findViewById(R.id.graph1);
        setCalendarLocation1 = (ImageButton) view.findViewById(R.id.setCalendarLocation1);
        setCalendarLocation1.setOnClickListener(this);

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
        userInfo=((MainActivity)getActivity()).datahandler.userinfo;
        Locations=((MainActivity)getActivity()).datahandler.getHeatmaplocations();
        getAllSymptomValuesLast60DaysSick();
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(createCalendar(59).getTime());
        graph.getViewport().setMaxX(createCalendar(0).getTime());

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
        System.out.println("setupp count");
        countTable();
        fillTable();    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
    public void showAllSickSeries() {
        graph.addSeries(series10);
    }
    public void hideAllSickSeries() {
        graph.removeSeries(series10);
        allSickBox.setChecked(false);
    }
    public void showAllSickSeries2() {
        graph.addSeries(series10b);
    }
    public void hideAllSickSeries2() {
        graph.removeSeries(series10b);
        allSickBox.setChecked(false);
    }

    public Date createCalendar(int dayOfInterestLast60Days) {
        today = new Date();
        calendarStat = (GregorianCalendar) Calendar.getInstance();
        calendarStat.setTime(today);
        calendarStat.add(Calendar.DAY_OF_MONTH, -dayOfInterestLast60Days);
        dateOfInterest = calendarStat.getTime();
        return dateOfInterest;
    }

    public String createCalendarSdf(int dayOfInterestLast60Days) {
        today = new Date();
        calendarStat = (GregorianCalendar) Calendar.getInstance();
        calendarStat.setTime(today);
        calendarStat.add(Calendar.DAY_OF_MONTH, -dayOfInterestLast60Days);
        dateOfInterest = calendarStat.getTime();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdfDate = sdf.format(dateOfInterest);
        System.out.println(sdfDate);
        return sdfDate;
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public void getCalendarView(final Integer location) throws OutOfDateRangeException {
        OnSelectDateListener listener = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                minCalendarValue = calendars.get(0).getTime();
                maxCalendarValue = calendars.get(calendars.size()-1).getTime();
                noSelectedDates = false;
                clearGraph();

                if(series1exist){
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
        minDate = createCalendar(60);

        DatePickerBuilder builder = new DatePickerBuilder(getContext(), listener)
                .pickerType(cal.RANGE_PICKER).setMaximumDate(maxDate).setMinimumDate(toCalendar(minDate));
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




    public void getAllSymptomValuesLast60DaysSick(){

        tempString="";
        for(int i=0; i<10;i++){
            Symtomcounter.add(0);
        }

        for(int j = 0; j<60; j++){
            tempString = createCalendarSdf(j);
            for(int i = 0; i<Locations.size(); i++) {
                String convert=convertStringPrintToDataFormat(Locations.get(i).getDate());
                System.out.println(convert+"sdfsdfsdfdsfsdfdsfsdfsdf");
                System.out.println(tempString);
                if(convert.equals(tempString)){
                    System.out.println("ffffffffff");
                    Symtomcounter.set(9,Symtomcounter.get(9)+1);
                    if(Locations.get(i).getHeadacheRatingBar() > 0) Symtomcounter.set(0,Symtomcounter.get(0)+1);
                    if(Locations.get(i).getCoughRatingBar() > 0) Symtomcounter.set(1,Symtomcounter.get(1)+1);
                    if(Locations.get(i).getFeverRatingBar() > 0) Symtomcounter.set(2,Symtomcounter.get(2)+1);
                    if(Locations.get(i).getThroatRatingBar() > 0) Symtomcounter.set(3,Symtomcounter.get(3)+1);
                    if(Locations.get(i).getTirednessRatingBar() > 0) Symtomcounter.set(4,Symtomcounter.get(4)+1);
                    if(Locations.get(i).getBreathingRatingBar() > 0) Symtomcounter.set(5,Symtomcounter.get(5)+1);
                    if(Locations.get(i).getNasalCongestionRatingBar() > 0) Symtomcounter.set(6,Symtomcounter.get(6)+1);
                    if(Locations.get(i).getRunnyNoseRatingBar() > 0) Symtomcounter.set(7,Symtomcounter.get(7)+1);
                    if(Locations.get(i).diarrheaRatingBar > 0) Symtomcounter.set(8,Symtomcounter.get(8)+1);
                }

            }
            allRatingsSick.add( Symtomcounter.get(9));
            allRatingsDiarrhea.add( Symtomcounter.get(8));
            allRatingsRunnyNose.add( Symtomcounter.get(7));
            allRatingsNasalCon.add( Symtomcounter.get(6));
            allRatingsBreathing.add( Symtomcounter.get(5));
            allRatingsTiredness.add( Symtomcounter.get(4));
            allRatingsThroat.add( Symtomcounter.get(3));
            allRatingsFever.add( Symtomcounter.get(2));
            allRatingsCough.add( Symtomcounter.get(1));
            allRatingsHeadache.add( Symtomcounter.get(0));
            for(int i=0; i<10;i++){
                Symtomcounter.set(i,0);
            }
        }

    }

    public Integer countForGraph2(int countForGraph2, ArrayList<Integer> symptomList) {

        int countForCertainDay = 0;
        for(int i = 59; i>countForGraph2; i--){
            countForCertainDay = countForCertainDay + symptomList.get(i);
        }
        return countForCertainDay;
    }



    public void makeGraphLines1(){
        dataPointsDiarrhea = new ArrayList<>();
        dataPointsRunnyNose = new ArrayList<>();
        dataPointsNasalCon= new ArrayList<>();
        dataPointsBreathing = new ArrayList<>();
        dataPointsTiredness = new ArrayList<>();
        dataPointsThroat = new ArrayList<>();
        dataPointsFever = new ArrayList<>();
        dataPointsCough = new ArrayList<>();
        dataPointsHeadache = new ArrayList<>();
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
        dpDiarrhea = new DataPoint[60];
        dpRunnyNose = new DataPoint[60];
        dpNasalCon = new DataPoint[60];
        dpBreathing = new DataPoint[60];
        dpTiredness = new DataPoint[60];
        dpThroat = new DataPoint[60];
        dpFever = new DataPoint[60];
        dpCough = new DataPoint[60];
        dpHeadache = new DataPoint[60];
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

    public void makeGraphLinesAllSick1() {
        ArrayList<DataPoint> dataPointsSick = new ArrayList<>();
        int purple = getContext().getResources().getColor(R.color.graph_purple);
        for(int i=59; i>=0; i--){
            dataPointsSick.add(new DataPoint(createCalendar(i), allRatingsSick.get(i)));
        }
        DataPoint[] dpSick = new DataPoint[60];
        series10 = new LineGraphSeries<DataPoint>(dataPointsSick.toArray(dpSick));
        series10.setColor(purple);
    }

    public void makeGraphLines2(){
        dataPointsDiarrhea = new ArrayList<>();
        dataPointsRunnyNose = new ArrayList<>();
        dataPointsNasalCon= new ArrayList<>();
        dataPointsBreathing = new ArrayList<>();
        dataPointsTiredness = new ArrayList<>();
        dataPointsThroat = new ArrayList<>();
        dataPointsFever = new ArrayList<>();
        dataPointsCough = new ArrayList<>();
        dataPointsHeadache = new ArrayList<>();

        for(int i = 59; i>=0; i--){
            dataPointsDiarrhea.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsDiarrhea)));
            dataPointsRunnyNose.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsRunnyNose)));
            dataPointsNasalCon.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsNasalCon)));
            dataPointsBreathing.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsBreathing)));
            dataPointsTiredness.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsTiredness)));
            dataPointsThroat.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsThroat)));
            dataPointsFever.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsFever)));
            dataPointsCough.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsCough)));
            dataPointsHeadache.add(new DataPoint(createCalendar(i), countForGraph2(i,allRatingsHeadache)));
        }

        dpDiarrhea = new DataPoint[60];
        dpRunnyNose = new DataPoint[60];
        dpNasalCon = new DataPoint[60];
        dpBreathing = new DataPoint[60];
        dpTiredness = new DataPoint[60];
        dpThroat = new DataPoint[60];
        dpFever = new DataPoint[60];
        dpCough = new DataPoint[60];
        dpHeadache = new DataPoint[60];

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

    public void makeGraphLinesAllSick2() {
        ArrayList<DataPoint> dataPointsSick = new ArrayList<>();
        int purple = getContext().getResources().getColor(R.color.graph_purple);
        for(int i = 59; i>=0; i--){
            dataPointsSick.add(new DataPoint(createCalendar(i), countForGraph2(i, allRatingsSick)));
        }
        DataPoint[] dpSick = new DataPoint[60];
        series10b = new LineGraphSeries<DataPoint>(dataPointsSick.toArray(dpSick));
        series10b.setColor(purple);
    }

    public void designSeriesA() {
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(calculateHighestValA());
        graph.getGridLabelRenderer().setNumVerticalLabels(calculateHighestValA() + 1);
        graph.setTitle(getString(R.string.per_day_symptoms));
        graph.setTitleTextSize(80);

        int orange = getContext().getResources().getColor(R.color.graph_orange);
        int darkPurple = getContext().getResources().getColor(R.color.graph_dark_purple);
        int brown = getContext().getResources().getColor(R.color.graph_brown);
        int red = getContext().getResources().getColor(R.color.graph_red);
        int blue = getContext().getResources().getColor(R.color.graph_blue);
        int turquoise = getContext().getResources().getColor(R.color.graph_turquoise);
        int purple = getContext().getResources().getColor(R.color.graph_purple);
        int lightBlue = getContext().getResources().getColor(R.color.graph_light_blue);
        int pink = getContext().getResources().getColor(R.color.graph_pink);

        series.setColor(orange);
        series2.setColor(darkPurple);
        series3.setColor(brown);
        series4.setColor(red);
        series5.setColor(blue);
        series6.setColor(turquoise);
        series7.setColor(purple);
        series8.setColor(lightBlue);
        series9.setColor(pink);
    }

    public void designSeriesb() {
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(calculateHighestValB() + 1);

        graph.getGridLabelRenderer().setNumVerticalLabels(calculateHighestValB() + 1);
        graph.setTitle(getString(R.string.total_symptoms));
        graph.setTitleTextSize(80);

        int orange = getContext().getResources().getColor(R.color.graph_orange);
        int darkPurple = getContext().getResources().getColor(R.color.graph_dark_purple);
        int brown = getContext().getResources().getColor(R.color.graph_brown);
        int red = getContext().getResources().getColor(R.color.graph_red);
        int blue = getContext().getResources().getColor(R.color.graph_blue);
        int turquoise = getContext().getResources().getColor(R.color.graph_turquoise);
        int purple = getContext().getResources().getColor(R.color.graph_purple);
        int lightBlue = getContext().getResources().getColor(R.color.graph_light_blue);
        int pink = getContext().getResources().getColor(R.color.graph_pink);
        seriesb.setColor(orange);
        series2b.setColor(darkPurple);
        series3b.setColor(brown);
        series4b.setColor(red);
        series5b.setColor(blue);
        series6b.setColor(turquoise);
        series7b.setColor(purple);
        series8b.setColor(lightBlue);
        series9b.setColor(pink);
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
        if(Collections.max(allRatingsSick) > largest) {
            largest = Collections.max(allRatingsSick);
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
        if(countForGraph2(0,allRatingsSick) > largest) {
            largest2 = countForGraph2(0,allRatingsSick);
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
        allSickBox.setChecked(false);
        noSelectedDates = true;

        if(text.equals(getString(R.string.per_day_symptoms))){
            createCalendar(60);
            clearGraph();
            makeGraphLines1();
            makeGraphLinesAllSick1();
            addAllSeries1();
            designSeriesA();
        }
        else{
            createCalendar(60);
            System.out.println("sdfsdfsdfssdfsdfrgdthgsethseth");
            clearGraph();
            makeGraphLines2();
            makeGraphLinesAllSick2();
            addAllSeries2();
            designSeriesb();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void countTable() {
        System.out.println(userInfo.size()+"userinfo size");
        for(ArrayList user: userInfo){
            int age=Integer.parseInt((String) user.get(0));
            System.out.println(age+"fdsdfsdfsdf");
            String gender=(String)user.get(1);
            System.out.println(gender+"dfasfasdfhahacv");

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
        System.out.println();
        String[] parts = string.split("-");
        if((Integer.parseInt(parts[1])+1)<10){
        return (Integer.parseInt(parts[2])+1)+"-"+"0"+(Integer.parseInt(parts[1]))+"-"+parts[0];}
        return (Integer.parseInt(parts[2])+1)+"-"+(Integer.parseInt(parts[1])+1)+"-"+parts[0];
    }

}