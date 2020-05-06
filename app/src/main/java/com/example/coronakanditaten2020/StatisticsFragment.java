package com.example.coronakanditaten2020;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.google.android.gms.maps.model.LatLng;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class StatisticsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Fragment Statistics";

    private Button btnStatisticsToStart;
    private Button btnStatisticsToHeatmap;
    private Location[] Locations = new Location[12];
    public CheckBox diarrheaBox, runnyNoseBox, nasalConBox, headacheBox, throatBox, breathingDiffBox, tirednessBox, coughBox, feverBox;


    private GraphView graph;
    private LineGraphSeries series;
    private LineGraphSeries series2;
    private LineGraphSeries series3;
    private LineGraphSeries series4;
    private LineGraphSeries series5;
    private LineGraphSeries series6;
    private LineGraphSeries series7;
    private LineGraphSeries series8;
    private LineGraphSeries series9;
    private com.applandeo.materialcalendarview.CalendarView cal;
    private List<Calendar> location1Dates;
    private String location1DateString = "";
    private Calendar maxDate = Calendar.getInstance(TimeZone.getDefault());
    Calendar firstSelectedDate;
    Calendar lastSelectedDate;
    private ImageButton setCalendarLocation1;

    //ALLA SYMPTOM
    int diarrhea29 = 0;
    int diarrhea30 = 0;
    int diarrhea31 = 0;
    int diarrhea32 = 0;
    int runnyNose29 = 0;
    int runnyNose30 = 0;
    int runnyNose31 = 0;
    int runnyNose32 = 0;
    int nasalCongestion29 = 0;
    int nasalCongestion30 = 0;
    int nasalCongestion31 = 0;
    int nasalCongestion32 = 0;
    int headache29 = 0;
    int headache30 = 0;
    int headache31 = 0;
    int headache32 = 0;
    int throat29 = 0;
    int throat30 = 0;
    int throat31 = 0;
    int throat32 = 0;
    int breathing29 = 0;
    int breathing30 = 0;
    int breathing31 = 0;
    int breathing32 = 0;
    int tiredness29 = 0;
    int tiredness30 = 0;
    int tiredness31 = 0;
    int tiredness32 = 0;
    int cough29 = 0;
    int cough30 = 0;
    int cough31 = 0;
    int cough32 = 0;
    int fever29 = 0;
    int fever30 = 0;
    int fever31 = 0;
    int fever32 = 0;

    private Boolean noSelectedDates = true;
    private Calendar calendarStat;
    private Date d1, d2, d3, d4;
    private int largest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        btnStatisticsToStart = (Button) view.findViewById(R.id.btnStatisticsToStart);
        btnStatisticsToStart.setOnClickListener(this);
        btnStatisticsToHeatmap = (Button) view.findViewById(R.id.btnStatisticsToHeatmap);
        btnStatisticsToHeatmap.setOnClickListener(this);

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


        Locations[0] = new Location("59.858562", "17.638927", "2020-04-29", 0, 0, 2, 0, 3, 0, 1, 1, 0, 2, "2020-04-31");
        Locations[1] = new Location("59.858562", "17.638927", "2020-05-01", 1, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-04-30");
        Locations[2] = new Location("59.858562", "17.638927", "2020-05-01", 2, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-04-30");
        Locations[3] = new Location("59.858562", "17.638927", "2020-04-29", 3, 3, 2, 0, 1, 3, 1, 1, 0, 3, "2020-04-31");
        Locations[4] = new Location("59.858562", "17.638927", "2020-04-30", 4, 0, 0, 3, 3, 2, 1, 2, 0, 2, "2020-04-30");
        Locations[5] = new Location("59.858562", "17.638927", "2020-04-30", 5, 0, 3, 0, 2, 2, 1, 3, 1, 1, "2020-04-29");
        Locations[6] = new Location("59.858562", "17.638927", "2020-05-01", 6, 2, 0, 1, 3, 1, 1, 1, 0, 0, "2020-04-29");
        Locations[7] = new Location("59.858562", "17.638927", "2020-04-30", 7, 3, 0, 2, 1, 2, 1, 2, 0, 3, "2020-04-30");
        Locations[8] = new Location("59.858562", "17.638927", "2020-04-29", 8, 0, 0, 2, 3, 1, 1, 3, 3, 2, "2020-04-29");
        Locations[9] = new Location("59.858562", "17.638927", "2020-05-01", 9, 1, 2, 0, 0, 0, 1, 1, 0, 1, "2020-04-29");
        Locations[10] = new Location("59.858562", "17.638927", "2020-04-29", 10, 2, 1, 0, 3, 3, 1, 1, 0, 2, "2020-04-30");
        Locations[11] = new Location("59.858562", "17.638927", "2020-05-01", 11, 3, 2, 0, 1, 0, 1, 1, 1, 3, "2020-04-29");

        countAllSymptoms();

        if(noSelectedDates == true) {
            calendarStat = Calendar.getInstance();
            calendarStat.set(Calendar.DAY_OF_YEAR, 119);
            d1 = calendarStat.getTime();
            calendarStat.add(Calendar.DATE, 1);
            d2 = calendarStat.getTime();
            calendarStat.add(Calendar.DATE, 1);
            d3 = calendarStat.getTime();
            calendarStat.add(Calendar.DATE, 1);
            d4 = calendarStat.getTime();
            calendarStat.add(Calendar.DATE, 1);
        }

        makeGraphLines();
        calculateHighestVal();
        designGraph();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStatisticsToHeatmap:
                Toast.makeText(getActivity(), "Going to Heatmap", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(2);
                break;
            case R.id.btnStatisticsToStart:
                Toast.makeText(getActivity(), "Going to Start Page", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).setViewPager(0);
                break;
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
    public void hideDiarrheaSeries () {
        System.out.println(graph);
        graph.removeSeries(series);
    }
    public void showRunnyNoseSeries() {
        graph.addSeries(series2);
    }
    public void hideRunnyNoseSeries () {
        graph.removeSeries(series2);
    }
    public void showTirednessSeries() {
        graph.addSeries(series3);
    }
    public void hideTirednessSeries() {
        graph.removeSeries(series3);
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

    public void getCalendarView(final Integer location) throws OutOfDateRangeException {
        OnSelectDateListener listener = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                noSelectedDates = false;
                clearGraph();
                location1Dates = calendars;
                d1 = location1Dates.get(0).getTime();
                d2 = location1Dates.get(1).getTime();
                d3 = location1Dates.get(2).getTime();
                d4 = location1Dates.get(3).getTime();
                addAllSeries();
                designGraph();

                System.out.println("hejsan" + location1Dates);

            }
        };

        DatePickerBuilder builder = new DatePickerBuilder(getContext(), listener)
                .pickerType(cal.RANGE_PICKER).setMaximumDate(maxDate);
        if (location1Dates != null)
            builder.setSelectedDays(location1Dates);

        DatePicker datePicker = builder.build();
        datePicker.show();
    }

    public void addAllSeries(){
        graph.addSeries(series);
        graph.addSeries(series2);
        graph.addSeries(series3);
        graph.addSeries(series4);
        graph.addSeries(series5);
        graph.addSeries(series6);
        graph.addSeries(series7);
        graph.addSeries(series8);
        graph.addSeries(series9);
    }

    public void clearGraph(){
        graph.removeAllSeries();
    }

    public void countAllSymptoms(){
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getDiarrheaRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    diarrhea29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    diarrhea30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    diarrhea31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    diarrhea32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getRunnyNoseRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    runnyNose29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    runnyNose30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    runnyNose31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    runnyNose32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getNasalCongestionRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    nasalCongestion29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    nasalCongestion30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    nasalCongestion31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    nasalCongestion32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getHeadacheRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    headache29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    headache30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    headache31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    headache32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getThroatRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    throat29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    throat30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    throat31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    throat32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getBreathingRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    breathing29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    breathing30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    breathing31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    breathing32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getTirednessRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    tiredness29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    tiredness30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    tiredness31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    tiredness32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getCoughRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    cough29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    cough30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    cough31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    cough32 += 1;
                }
            }
        }
        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getFeverRatingBar() > 0) {
                if(Locations[i].getDate() == "2020-04-29") {
                    fever29 += 1;
                }
                if(Locations[i].getDate() == "2020-04-30") {
                    fever30 += 1;
                }
                if(Locations[i].getDate() == "2020-05-01") {
                    fever31 += 1;
                }
                if(Locations[i].getDate() == "2020-05-02") {
                    fever32 += 1;
                }
            }
        }
    }

    public void makeGraphLines(){
        series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, diarrhea29),
                new DataPoint(d2, diarrhea30),
                new DataPoint(d3, diarrhea31),
                new DataPoint(d4, diarrhea32)
        });
        series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, runnyNose29),
                new DataPoint(d2, runnyNose30),
                new DataPoint(d3, runnyNose31),
                new DataPoint(d4, runnyNose32)
        });
        series3 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, nasalCongestion29),
                new DataPoint(d2, nasalCongestion30),
                new DataPoint(d3, nasalCongestion31),
                new DataPoint(d4, nasalCongestion32)
        });
        series4 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, headache29),
                new DataPoint(d2, headache30),
                new DataPoint(d3, headache31),
                new DataPoint(d4, headache32)
        });
        series5 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, throat29),
                new DataPoint(d2, throat30),
                new DataPoint(d3, throat31),
                new DataPoint(d4, throat32)
        });
        series6 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, breathing29),
                new DataPoint(d2, breathing30),
                new DataPoint(d3, breathing31),
                new DataPoint(d4, breathing32)
        });
        series7 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, tiredness29),
                new DataPoint(d2, tiredness30),
                new DataPoint(d3, tiredness31),
                new DataPoint(d4, tiredness32)
        });
        series8 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, cough29),
                new DataPoint(d2, cough30),
                new DataPoint(d3, cough31),
                new DataPoint(d4, cough32)
        });
        series9 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(d1, fever29),
                new DataPoint(d2, fever30),
                new DataPoint(d3, fever31),
                new DataPoint(d4, fever32)
        });
        graph.addSeries(series);
        graph.addSeries(series2);
        graph.addSeries(series3);
        graph.addSeries(series4);
        graph.addSeries(series5);
        graph.addSeries(series6);
        graph.addSeries(series7);
        graph.addSeries(series8);
        graph.addSeries(series9);
    }

    public void calculateHighestVal(){
        List<Integer> Numbers = new ArrayList<>();
        Numbers.add(diarrhea29);
        Numbers.add(diarrhea30);
        Numbers.add(diarrhea31);
        Numbers.add(diarrhea32);
        Numbers.add(runnyNose29);
        Numbers.add(runnyNose30);
        Numbers.add(runnyNose31);
        Numbers.add(runnyNose32);
        Numbers.add(nasalCongestion29);
        Numbers.add(nasalCongestion30);
        Numbers.add(nasalCongestion31);
        Numbers.add(nasalCongestion32);
        Numbers.add(breathing29);
        Numbers.add(breathing30);
        Numbers.add(breathing31);
        Numbers.add(breathing32);
        Numbers.add(tiredness29);
        Numbers.add(tiredness30);
        Numbers.add(tiredness31);
        Numbers.add(tiredness32);
        Numbers.add(throat29);
        Numbers.add(throat30);
        Numbers.add(throat31);
        Numbers.add(throat32);
        Numbers.add(cough29);
        Numbers.add(cough30);
        Numbers.add(cough31);
        Numbers.add(cough32);
        Numbers.add(fever29);
        Numbers.add(fever30);
        Numbers.add(fever31);
        Numbers.add(fever32);
        Numbers.add(headache29);
        Numbers.add(headache30);
        Numbers.add(headache31);
        Numbers.add(headache32);
        largest = 0;
        for (int i = 0; i < Numbers.size(); i++) {
            if (Numbers.get(i) > largest) {
                largest = Numbers.get(i);
            }
        }
    }
    public void designGraph(){
        graph.getViewport().setScrollable(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(largest);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(d4.getTime());
        //graph.getViewport().setMinX(d1.getTime());
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));



        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setNumVerticalLabels(largest + 1);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.setTitle("All symptoms");
        graph.setTitleTextSize(80);

        series.setTitle("Diarrhea");
        series2.setTitle("Runny Nose");
        series3.setTitle("Tiredness");
        series4.setTitle("Fever");
        series5.setTitle("Sore Throat");
        series6.setTitle("Nasal Congestion");
        series7.setTitle("Cough");
        series8.setTitle("Headache");
        series9.setTitle("Breathing difficulties");
        series.setColor(Color.BLACK);
        series2.setColor(Color.GREEN);
        series3.setColor(Color.YELLOW);
        series4.setColor(Color.RED);
        series5.setColor(Color.BLUE);
        series6.setColor(Color.MAGENTA);
        series7.setColor(Color.DKGRAY);
        series8.setColor(Color.CYAN);
        series9.setColor(Color.GRAY);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.MIDDLE);
        graph.getLegendRenderer().setTextSize(35f);
    }
}