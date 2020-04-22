package com.example.coronakanditaten2020;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;


public class StatisticsFragment extends Fragment {
    private static final String TAG = "Fragment Statistics";

    private Button btnStatisticsToStart;
    private Button btnStatisticsToHeatmap;
    private Location[] Locations = new Location[12];


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        btnStatisticsToStart = (Button) view.findViewById(R.id.btnStatisticsToStart);
        btnStatisticsToHeatmap = (Button) view.findViewById(R.id.btnStatisticsToHeatmap);

        Locations[0] =new Location("59.858562", "17.638927", "2020-10-15", 0, 0, 2, 0, 3, 0, 1, 1, 0, 2, "2020-10-17 10:30");
        Locations[1] =new Location("59.858562", "17.638927", "2020-10-15", 1, 2, 3, 0, 2, 1, 1, 2, 1, 1, "2020-10-17 10:30");
        Locations[2] =new Location("59.858562", "17.638927", "2020-10-15", 2, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-10-17 10:30");
        Locations[3] =new Location("59.858562", "17.638927", "2020-10-15", 3, 3, 2, 0, 1, 3, 1, 1, 0, 3, "2020-10-17 10:30");
        Locations[4] =new Location("59.858562", "17.638927", "2020-10-15", 4, 0, 0, 3, 3, 2, 1, 2, 0, 2, "2020-10-17 10:30");
        Locations[5] =new Location("59.858562", "17.638927", "2020-10-15", 5, 0, 3, 0, 2, 2, 1, 3, 1, 1, "2020-10-17 10:30");
        Locations[6] =new Location("59.858562", "17.638927", "2020-10-15", 6, 2, 0, 1, 3, 1, 1, 1, 0, 0, "2020-10-17 10:30");
        Locations[7] =new Location("59.858562", "17.638927", "2020-10-15", 7, 3, 0, 2, 1, 2, 1, 2, 0, 3, "2020-10-17 10:30");
        Locations[8] =new Location("59.858562", "17.638927", "2020-10-15", 8, 0, 0, 2, 3, 1, 1, 3, 3, 2, "2020-10-17 10:30");
        Locations[9] =new Location("59.858562", "17.638927", "2020-10-15", 9, 1, 2, 0, 0, 0, 1, 1, 0, 1, "2020-10-17 10:30");
        Locations[10] =new Location("59.858562", "17.638927", "2020-10-15", 10, 2, 1, 0, 3, 3, 1, 1, 0, 2, "2020-10-17 10:30");
        Locations[11] =new Location("59.858562", "17.638927", "2020-10-15", 11, 3, 2, 0, 1, 0, 1, 1, 1, 3, "2020-10-17 10:30");

        //DIARRHEA
        int zeros = 0;
        int ones = 0;
        int twos = 0;
        int threes = 0;
        for(int i=0; i<Locations.length; i++) {
            if (Locations[i].getDiarrheaRatingBar() == 0) {
                zeros += 1;
            } else if (Locations[i].getDiarrheaRatingBar() == 1) {
                ones += 1;
            } else if(Locations[i].getDiarrheaRatingBar() == 2) {
                twos += 1;
            } else if(Locations[i].getDiarrheaRatingBar() == 3) {
                threes += 1;
            }
        }
        GraphView graph = (GraphView) view.findViewById(R.id.graph1);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, zeros),
                new DataPoint(1, ones),
                new DataPoint(2, twos),
                new DataPoint(3, threes),
        });
        graph.addSeries(series);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);
        series.setDrawValuesOnTop(true);                // draw values on top of staplar
        series.setValuesOnTopColor(Color.RED);
        series.setValuesOnTopSize(40);
        //graph.getViewport().setScalable(true);        // activate horizontal zooming and scrolling
        graph.setTitle("Diarrhea");
        graph.setTitleTextSize(80);
        //graph.setTitleColor(Color.BLUE);
        //graph.getLegendRenderer().setVisible(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(6);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        GridLabelRenderer gridLabelX = graph.getGridLabelRenderer();
        gridLabelX.setHorizontalAxisTitle("Severity of symptom");
        GridLabelRenderer gridLabelY = graph.getGridLabelRenderer();
        gridLabelY.setVerticalAxisTitle("Number of people");

        //ALLA SYMPTOM

//FEVER

        int feverZeros = 0;
        int feverOnes = 0;
        int feverTwos = 0;
        int feverThrees = 0;
        for(int i=0; i<Locations.length; i++) {
            if (Locations[i].getFeverRatingBar() == 0) {
                feverZeros += 1;
            } else if (Locations[i].getFeverRatingBar() == 1) {
                feverOnes += 1;
            } else if(Locations[i].getFeverRatingBar() == 2) {
                feverTwos += 1;
            } else if(Locations[i].getFeverRatingBar() == 3) {
                feverThrees += 1;
            }
        }
        GraphView graph2 = (GraphView) view.findViewById(R.id.graph2);
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, feverZeros),
                new DataPoint(1, feverOnes),
                new DataPoint(2, feverTwos),
                new DataPoint(3, feverThrees),
        });

        graph2.addSeries(series2);
        series2.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        series2.setSpacing(50);
        series2.setDrawValuesOnTop(true);                // draw values on top of staplar
        series2.setValuesOnTopColor(Color.RED);
        series2.setValuesOnTopSize(40);
//graph.getViewport().setScalable(true);        // activate horizontal zooming and scrolling
        graph2.setTitle("Fever");
        graph2.setTitleTextSize(80);
//graph.setTitleColor(Color.BLUE);
//graph.getLegendRenderer().setVisible(true);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxY(8);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(4);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setXAxisBoundsManual(true);
        GridLabelRenderer gridLabelX2 = graph2.getGridLabelRenderer();
        gridLabelX2.setHorizontalAxisTitle("Severity of symptom");
        GridLabelRenderer gridLabelY2 = graph2.getGridLabelRenderer();
        gridLabelY2.setVerticalAxisTitle("Number of people");





        btnStatisticsToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Start Page", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });
        btnStatisticsToHeatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Heatmap", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(2);
            }


        });

        return view;
    }
}
