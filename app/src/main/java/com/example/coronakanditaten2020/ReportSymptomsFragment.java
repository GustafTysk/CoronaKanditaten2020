package com.example.coronakanditaten2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class ReportSymptomsFragment extends Fragment {

    private static final String TAG = "Fragment Statistics";

    private Button btnRsToStart;
    private Button btnRsToRl;
    RatingBar feverRatingBar, coughRatingBar, tirednessRatingBar, breathingRatingBar, throatRatingBar, headacheRatingBar, nasalCongestionRatingBar, runnyNoseRatingBar, diarrheaRatingBar;
    private float totalSeverityCount, feverSeverity, coughSeverity, tirednessSeverity, breathingSeverity, throatSeverity, headacheSeverity, nasalCongestionSeverity, runnyNoseSeverity, diarrheaSeverity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_symptoms, container, false);
        btnRsToStart = (Button) view.findViewById(R.id.btnRsToStart);
        btnRsToRl = (Button) view.findViewById(R.id.btnRsToRl);

        feverRatingBar = (RatingBar) view.findViewById(R.id.ratingBarFever);
        coughRatingBar = (RatingBar) view.findViewById(R.id.ratingBarCough);
        tirednessRatingBar = (RatingBar) view.findViewById(R.id.ratingBarTiredness);
        breathingRatingBar = (RatingBar) view.findViewById(R.id.ratingBarBreathingDifficulties);
        throatRatingBar = (RatingBar) view.findViewById(R.id.ratingBarSoreThroat);
        headacheRatingBar = (RatingBar) view.findViewById(R.id.ratingBarHeadache);
        nasalCongestionRatingBar = (RatingBar) view.findViewById(R.id.ratingBarNasalCongestion);
        runnyNoseRatingBar = (RatingBar) view.findViewById(R.id.ratingBarRunnyNose);
        diarrheaRatingBar = (RatingBar) view.findViewById(R.id.ratingBarDiarrhea);
        System.out.println(feverSeverity);

        btnRsToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Start Page", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });
        btnRsToRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Report Location", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(4);

                totalSeverityCount = countAllRatings();

                System.out.println(totalSeverityCount);
                totalSeverityCount = 0;
            }
        });

        return view;
    }

    private float countAllRatings(){
        return diarrheaRatingBar.getRating() + runnyNoseRatingBar.getRating() + nasalCongestionRatingBar.getRating() + headacheRatingBar.getRating()
                + throatRatingBar.getRating() + breathingRatingBar.getRating() + tirednessRatingBar.getRating() + coughRatingBar.getRating() + feverRatingBar.getRating();
    }



}
