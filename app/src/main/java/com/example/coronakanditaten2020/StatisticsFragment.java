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
