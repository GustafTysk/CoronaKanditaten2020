package com.example.coronakanditaten2020;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
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
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class StatisticsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Fragment Statistics";

    private Button btnStatisticsToStart;
    private Button btnStatisticsToHeatmap;
    private Location[] Locations = new Location[12];
    private CheckBox diarrheaBox, runnyNoseBox, nasalConBox, headacheBox, throatBox, breathingDiffBox, tirednessBox, coughBox, feverBox;
    private Button show;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        btnStatisticsToStart = (Button) view.findViewById(R.id.btnStatisticsToStart);
        btnStatisticsToStart.setOnClickListener(this);
        btnStatisticsToHeatmap = (Button) view.findViewById(R.id.btnStatisticsToHeatmap);
        btnStatisticsToHeatmap.setOnClickListener(this);
//        addListenerOnRunnyNoseBox();
//        addListenerOnNasalConBox();
//        addListenerOnHeadacheBox();
//        addListenerOnThroatBox();
//        addListenerOnBreatingDiffBox();
//        addListenerOnTirednessBox();
//        addListenerOnCoughBox();
//        addListenerOnFeverBox();
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

//        GraphView graph = (GraphView) view.findViewById(R.id.graph1);
//        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
//                new DataPoint(1, diarrhea),
//                new DataPoint(2, runnyNose),
//                new DataPoint(3, nasalCongestion),
//                new DataPoint(4, headache),
//                new DataPoint(5, throat),
//                new DataPoint(6, breathing),
//                new DataPoint(7, tiredness),
//                new DataPoint(8, cough),
//                new DataPoint(9, fever)
//
//        });
//        graph.addSeries(series);

        GraphView graph = (GraphView) view.findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 2),
                new DataPoint(1, 4),
                new DataPoint(2, 7),
                new DataPoint(3, 1),
                new DataPoint(4, 3)
        });
        graph.addSeries(series);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        graph.addSeries(series2);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setColor(Color.GREEN);
        series2.setThickness(8);

//        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//            }
//        });
////        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
////        graph.getLegendRenderer().setVisible(true);
////        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
////        staticLabelsFormatter.setHorizontalLabels(new String[] {"YOUTH", "NOS", "IT", "SQA"});
//
//        series.setSpacing(50);
//        series.setDrawValuesOnTop(true);                // draw values on top of staplar
//        series.setValuesOnTopColor(Color.RED);
//        series.setValuesOnTopSize(40);
////graph.getViewport().setScalable(true);        // activate horizontal zooming and scrolling
//        graph.setTitle("All symptoms");
//        graph.setTitleTextSize(80);
////graph.setTitleColor(Color.BLUE);
////graph.getLegendRenderer().setVisible(true);
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(13);
//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(12);
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setXAxisBoundsManual(true);
//        GridLabelRenderer gridLabelX = graph.getGridLabelRenderer();
//        gridLabelX.setHorizontalAxisTitle("Severity of symptom");
//        GridLabelRenderer gridLabelY = graph.getGridLabelRenderer();
//        gridLabelY.setVerticalAxisTitle("Number of people");

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




}
