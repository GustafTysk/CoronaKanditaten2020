package com.example.coronakanditaten2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ReportSymptomsFragment extends Fragment {

    private static final String TAG = "Fragment Statistics";

    private Button btnRsToStart;
    private Button btnRsToRl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_symptoms, container, false);
        btnRsToStart = (Button) view.findViewById(R.id.btnRsToStart);
        btnRsToRl = (Button) view.findViewById(R.id.btnRsToRl);

        btnRsToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Start Page", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(2);
            }
        });
        btnRsToRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Report Location", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(4);
            }
        });

        return view;
    }
}
