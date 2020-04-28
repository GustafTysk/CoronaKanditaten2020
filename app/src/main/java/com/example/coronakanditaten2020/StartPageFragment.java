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

public class StartPageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Fragment StartPage";

    private Button btnStartToStatistics;
    private Button btnStartToHeatmap;
    private Button btnStartToRs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_page, container, false);
        btnStartToStatistics = (Button) view.findViewById(R.id.btnStartToStatistics);
        btnStartToStatistics.setOnClickListener(this);
        btnStartToHeatmap = (Button) view.findViewById(R.id.btnStartToHeatmap);
        btnStartToHeatmap.setOnClickListener(this);
        btnStartToRs = (Button) view.findViewById(R.id.btnStartToRs);
        btnStartToRs.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnStartToHeatmap:
                ((MainActivity)getActivity()).setViewPager(2);
                break;
            case R.id.btnStartToStatistics:
                ((MainActivity)getActivity()).setViewPager(1);
                break;
            case R.id.btnStartToRs:
                ((MainActivity)getActivity()).setViewPager(3);
                break;

            default:
        }
    }
}
