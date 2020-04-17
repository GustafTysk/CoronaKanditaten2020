package com.example.coronakanditaten2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StartPageFragment extends Fragment {
    private static final String TAG = "Fragment StartPage";

    private Button btnStartToStatistics;
    private Button btnStartToHeatmap;
    private Button btnStartToRs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_page, container, false);
        btnStartToStatistics = (Button) view.findViewById(R.id.btnStartToStatistics);
        btnStartToHeatmap = (Button) view.findViewById(R.id.btnStartToHeatmap);
        btnStartToRs = (Button) view.findViewById(R.id.btnStartToRs);

        btnStartToHeatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Heatmap", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).setViewPager(2);
            }
        });
        btnStartToStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Statistics", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).setViewPager(1);
            }
        });
        btnStartToRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Report Symptoms", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).setViewPager(3);
            }
        });

        return view;
    }


}
