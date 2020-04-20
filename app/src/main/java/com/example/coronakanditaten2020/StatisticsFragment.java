package com.example.coronakanditaten2020;

import android.content.Intent;
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
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class StatisticsFragment extends Fragment {
    private static final String TAG = "Fragment Statistics";

    private Button btnStatisticsToStart;
    private Button btnStatisticsToHeatmap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        btnStatisticsToStart = (Button) view.findViewById(R.id.btnStatisticsToStart);
        btnStatisticsToHeatmap = (Button) view.findViewById(R.id.btnStatisticsToHeatmap);

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);
        graph.setTitle("Magic");
        graph.setTitleTextSize(100);
        //graph.setTitleColor();
        series.setDrawDataPoints(true);

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
