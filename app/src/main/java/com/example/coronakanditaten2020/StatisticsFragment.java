package com.example.coronakanditaten2020;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class StatisticsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Fragment Statistics";

    private Button btnStatisticsToStart;
    private Button btnStatisticsToHeatmap;
    private Location[] Locations = new Location[12];
    public CheckBox diarrheaBox, runnyNoseBox, nasalConBox, headacheBox, throatBox, breathingDiffBox, tirednessBox, coughBox, feverBox;

    public GraphView graph;
    public LineGraphSeries series;
    public LineGraphSeries series2;
    public LineGraphSeries series3;
    public LineGraphSeries series4;
    public LineGraphSeries series5;
    public LineGraphSeries series6;
    public LineGraphSeries series7;
    public LineGraphSeries series8;
    public LineGraphSeries series9;
//    public BarGraphSeries series;
//    public BarGraphSeries series2;
//    public BarGraphSeries series3;
//    public BarGraphSeries series4;
//    public BarGraphSeries series5;
//    public BarGraphSeries series6;
//    public BarGraphSeries series7;
//    public BarGraphSeries series8;
//    public BarGraphSeries series9;



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


        Locations[0] = new Location("59.858562", "17.638927", "2020-10-15", 0, 0, 2, 0, 3, 0, 1, 1, 0, 2, "2020-10-17 10:30");
        Locations[1] = new Location("59.858562", "17.638927", "2020-10-15", 1, 2, 3, 0, 2, 1, 1, 2, 1, 1, "2020-10-17 10:30");
        Locations[2] = new Location("59.858562", "17.638927", "2020-10-15", 2, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-10-17 10:30");
        Locations[3] = new Location("59.858562", "17.638927", "2020-10-15", 3, 3, 2, 0, 1, 3, 1, 1, 0, 3, "2020-10-17 10:30");
        Locations[4] = new Location("59.858562", "17.638927", "2020-10-15", 4, 0, 0, 3, 3, 2, 1, 2, 0, 2, "2020-10-17 10:30");
        Locations[5] = new Location("59.858562", "17.638927", "2020-10-15", 5, 0, 3, 0, 2, 2, 1, 3, 1, 1, "2020-10-17 10:30");
        Locations[6] = new Location("59.858562", "17.638927", "2020-10-15", 6, 2, 0, 1, 3, 1, 1, 1, 0, 0, "2020-10-17 10:30");
        Locations[7] = new Location("59.858562", "17.638927", "2020-10-15", 7, 3, 0, 2, 1, 2, 1, 2, 0, 3, "2020-10-17 10:30");
        Locations[8] = new Location("59.858562", "17.638927", "2020-10-15", 8, 0, 0, 2, 3, 1, 1, 3, 3, 2, "2020-10-17 10:30");
        Locations[9] = new Location("59.858562", "17.638927", "2020-10-15", 9, 1, 2, 0, 0, 0, 1, 1, 0, 1, "2020-10-17 10:30");
        Locations[10] = new Location("59.858562", "17.638927", "2020-10-15", 10, 2, 1, 0, 3, 3, 1, 1, 0, 2, "2020-10-17 10:30");
        Locations[11] = new Location("59.858562", "17.638927", "2020-10-15", 11, 3, 2, 0, 1, 0, 1, 1, 1, 3, "2020-10-17 10:30");

        //ALLA SYMPTOM
        int diarrhea = 0;
        int runnyNose = 0;
        int nasalCongestion = 0;
        int headache = 0;
        int throat = 0;
        int breathing = 0;
        int tiredness = 0;
        int cough = 0;
        int fever = 0;
        DataPoint dataPoint1;
        DataPoint dataPoint2;
        DataPoint dataPoint3;
        DataPoint dataPoint4;
        DataPoint dataPoint5;
        DataPoint dataPoint6;
        DataPoint dataPoint7;
        DataPoint dataPoint8;
        DataPoint dataPoint9;



        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getDiarrheaRatingBar() == 1 || Locations[i].getDiarrheaRatingBar() == 2 || Locations[i].getDiarrheaRatingBar() == 3) {
                diarrhea += 1;
            } else {
                diarrhea += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getRunnyNoseRatingBar() == 1 || Locations[i].getRunnyNoseRatingBar() == 2 || Locations[i].getRunnyNoseRatingBar() == 3) {
                runnyNose += 1;
            } else {
                runnyNose += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getNasalCongestionRatingBar() == 1 || Locations[i].getNasalCongestionRatingBar() == 2 || Locations[i].getNasalCongestionRatingBar() == 3) {
                nasalCongestion += 1;
            } else {
                nasalCongestion += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getHeadacheRatingBar() == 1 || Locations[i].getHeadacheRatingBar() == 2 || Locations[i].getHeadacheRatingBar() == 3) {
                headache += 1;
            } else {
                headache += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getThroatRatingBar() == 1 || Locations[i].getThroatRatingBar() == 2 || Locations[i].getThroatRatingBar() == 3) {
                throat += 1;
            } else {
                throat += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getBreathingRatingBar() == 1 || Locations[i].getBreathingRatingBar() == 2 || Locations[i].getBreathingRatingBar() == 3) {
                breathing += 1;
            } else {
                breathing += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getTirednessRatingBar() == 1 || Locations[i].getTirednessRatingBar() == 2 || Locations[i].getTirednessRatingBar() == 3) {
                tiredness += 1;
            } else {
                tiredness += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getCoughRatingBar() == 1 || Locations[i].getCoughRatingBar() == 2 || Locations[i].getCoughRatingBar() == 3) {
                cough += 1;
            } else {
                cough += 0;
            }
        }

        for (int i = 0; i < Locations.length; i++) {
            if (Locations[i].getFeverRatingBar() == 1 || Locations[i].getFeverRatingBar() == 2 || Locations[i].getFeverRatingBar() == 3) {
                fever += 1;
            } else {
                fever += 0;
            }
        }

        //LINE GRAPH SERIES

        graph = (GraphView) view.findViewById(R.id.graph1);
        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0),
                new DataPoint(3, 0),
                new DataPoint(4, 0)
        });
        series2 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 1),
                new DataPoint(2, 1),
                new DataPoint(3, 1),
                new DataPoint(4, 1)
        });
        series3 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 2),
                new DataPoint(1, 2),
                new DataPoint(2, 2),
                new DataPoint(3, 2),
                new DataPoint(4, 2)
        });
        series4 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 3),
                new DataPoint(1, 3),
                new DataPoint(2, 3),
                new DataPoint(3, 3),
                new DataPoint(4, 3)
        });
        series5 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 4),
                new DataPoint(1, 4),
                new DataPoint(2, 4),
                new DataPoint(3, 4),
                new DataPoint(4, 4)
        });
        series6 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 5),
                new DataPoint(1, 5),
                new DataPoint(2, 5),
                new DataPoint(3, 5),
                new DataPoint(4, 5)
        });
        series7 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 6),
                new DataPoint(1, 6),
                new DataPoint(2, 6),
                new DataPoint(3, 6),
                new DataPoint(4, 6)
        });
        series8 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 7),
                new DataPoint(1, 7),
                new DataPoint(2, 7),
                new DataPoint(3, 7),
                new DataPoint(4, 7)
        });
        series9 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 8),
                new DataPoint(1, 8),
                new DataPoint(2, 8),
                new DataPoint(3, 8),
                new DataPoint(4, 8)
        });

        graph.addSeries(series);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        graph.addSeries(series2);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);
        series2.setColor(Color.GREEN);


        graph.addSeries(series3);
        series3.setDrawDataPoints(true);
        series3.setDataPointsRadius(10);
        series3.setThickness(8);

        graph.addSeries(series4);
        series4.setDrawDataPoints(true);
        series4.setDataPointsRadius(10);
        series4.setThickness(8);

        graph.addSeries(series5);
        series5.setDrawDataPoints(true);
        series5.setDataPointsRadius(10);
        series5.setThickness(8);

        graph.addSeries(series6);
        series6.setDrawDataPoints(true);
        series6.setDataPointsRadius(10);
        series6.setThickness(8);

        graph.addSeries(series7);
        series7.setDrawDataPoints(true);
        series7.setDataPointsRadius(10);
        series7.setThickness(8);

        graph.addSeries(series8);
        series8.setDrawDataPoints(true);
        series8.setDataPointsRadius(10);
        series8.setThickness(8);

        graph.addSeries(series9);
        series9.setDrawDataPoints(true);
        series9.setDataPointsRadius(10);
        series9.setThickness(8);

        graph.setTitle("All symptoms");
        graph.setTitleTextSize(80);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMinX(0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        GridLabelRenderer gridLabelX = graph.getGridLabelRenderer();
        gridLabelX.setHorizontalAxisTitle("Severity of symptom");
        GridLabelRenderer gridLabelY = graph.getGridLabelRenderer();
        gridLabelY.setVerticalAxisTitle("Number of people");

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

        }
    }

    public void showDiarrheaSeries() {
        graph.addSeries(series);
    }
    public void hideDiarrheaSeries () {
        System.out.println(series);
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
}
