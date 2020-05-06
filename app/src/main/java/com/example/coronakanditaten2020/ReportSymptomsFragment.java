package com.example.coronakanditaten2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

public class ReportSymptomsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Fragment Statistics";
    private ImageButton btnFeverDialog;
    private ImageButton btnCoughDialog;
    private ImageButton btnDifficultyBreathingDialog;
    private ImageButton btnSoreThroatDialog;
    private ImageButton btnNasalCongestionDialog;
    private ImageButton btnRunnyNoseDialog;
    private ImageButton btnTirednessDialog;
    private ImageButton btnHeadacheDialog;
    private ImageButton btnDiarrheaDialog;

    private Button btnClearFeverRating, btnClearCoughRating, btnClearBreathingRating, btnClearSoreThroatRating, btnClearNasalCongestionRating, btnClearRunnyNoseRating
            , btnClearTirednessRating, btnClearHeadacheRating, btnClearDiarrheaRating;

    private Button btnRsToStart;
    private Button btnRsToRl;
    RatingBar feverRatingBar, coughRatingBar, tirednessRatingBar, breathingRatingBar, SoreThroatRatingBar, headacheRatingBar, nasalCongestionRatingBar, runnyNoseRatingBar, diarrheaRatingBar;
    private float totalSeverityCount, feverSeverity, coughSeverity, tirednessSeverity, breathingSeverity, throatSeverity, headacheSeverity, nasalCongestionSeverity, runnyNoseSeverity, diarrheaSeverity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_symptoms, container, false);
        btnRsToStart = (Button) view.findViewById(R.id.btnRsToStart);
        btnRsToStart.setOnClickListener(this);
        btnRsToRl = (Button) view.findViewById(R.id.btnRsToRl);
        btnRsToRl.setOnClickListener(this);

        btnFeverDialog = (ImageButton) view.findViewById(R.id.btnFeverDialog);
        btnFeverDialog.setOnClickListener(this);
        btnCoughDialog = (ImageButton) view.findViewById(R.id.btnCoughDialog);
        btnCoughDialog.setOnClickListener(this);
        btnDifficultyBreathingDialog = (ImageButton) view.findViewById(R.id.btnBreathingDialog);
        btnDifficultyBreathingDialog.setOnClickListener(this);
        btnSoreThroatDialog = (ImageButton) view.findViewById(R.id.btnSoreThroatDialog);
        btnSoreThroatDialog.setOnClickListener(this);
        btnNasalCongestionDialog = (ImageButton) view.findViewById(R.id.btnNasalCongestionDialog);
        btnNasalCongestionDialog.setOnClickListener(this);
        btnRunnyNoseDialog = (ImageButton) view.findViewById(R.id.btnRunnyNoseDialog);
        btnRunnyNoseDialog.setOnClickListener(this);
        btnTirednessDialog = (ImageButton) view.findViewById(R.id.btnTirednessDialog);
        btnTirednessDialog.setOnClickListener(this);
        btnHeadacheDialog = (ImageButton) view.findViewById(R.id.btnHeadacheDialog);
        btnHeadacheDialog.setOnClickListener(this);
        btnDiarrheaDialog = (ImageButton) view.findViewById(R.id.btnDiarrheaDialog);
        btnDiarrheaDialog.setOnClickListener(this);


        feverRatingBar = (RatingBar) view.findViewById(R.id.ratingBarFever);
        btnClearFeverRating = (Button) view.findViewById(R.id.btnClearFeverRating);
        btnClearFeverRating.setOnClickListener(this);
        coughRatingBar = (RatingBar) view.findViewById(R.id.ratingBarCough);
        btnClearCoughRating = (Button) view.findViewById(R.id.btnClearCoughRating);
        btnClearCoughRating.setOnClickListener(this);
        tirednessRatingBar = (RatingBar) view.findViewById(R.id.ratingBarTiredness);
        btnClearTirednessRating = (Button) view.findViewById(R.id.btnClearTirednessRating);
        btnClearTirednessRating.setOnClickListener(this);
        breathingRatingBar = (RatingBar) view.findViewById(R.id.ratingBarBreathingDifficulties);
        btnClearBreathingRating = (Button) view.findViewById(R.id.btnClearBreathingRating);
        btnClearBreathingRating.setOnClickListener(this);
        SoreThroatRatingBar = (RatingBar) view.findViewById(R.id.ratingBarSoreThroat);
        btnClearSoreThroatRating = (Button) view.findViewById(R.id.btnClearSoreThroatRating);
        btnClearSoreThroatRating.setOnClickListener(this);
        headacheRatingBar = (RatingBar) view.findViewById(R.id.ratingBarHeadache);
        btnClearHeadacheRating = (Button) view.findViewById(R.id.btnClearHeadacheRating);
        btnClearHeadacheRating.setOnClickListener(this);
        nasalCongestionRatingBar = (RatingBar) view.findViewById(R.id.ratingBarNasalCongestion);
        btnClearNasalCongestionRating = (Button) view.findViewById(R.id.btnClearNasalCongestionRating);
        btnClearNasalCongestionRating.setOnClickListener(this);
        runnyNoseRatingBar = (RatingBar) view.findViewById(R.id.ratingBarRunnyNose);
        btnClearRunnyNoseRating = (Button) view.findViewById(R.id.btnCleaarRunnyNoseRating);
        btnClearRunnyNoseRating.setOnClickListener(this);
        diarrheaRatingBar = (RatingBar) view.findViewById(R.id.ratingBarDiarrhea);
        btnClearDiarrheaRating = (Button) view.findViewById(R.id.btnClearDiarrheaRating);
        btnClearDiarrheaRating.setOnClickListener(this);
        System.out.println(feverSeverity);

        return view;
    }
    public void openDialog(String symptom) {
        Dialog dialog = new Dialog(getContext());
        switch (symptom){
            case "fever":
                dialog.setContentView(R.layout.dialog_fever_info);
                dialog.setTitle("Fever information");
                break;
            case "cough":
                dialog.setContentView(R.layout.dialog_cough_info);
                dialog.setTitle("Cough information");
                break;
            case "breathing":
                dialog.setContentView(R.layout.dialog_breathing_info);
                dialog.setTitle("Breathing Difficulty information");
                break;
            case "throat":
                dialog.setContentView(R.layout.dialog_sorethroat_info);
                dialog.setTitle("Sore Throat information");
                break;
            case "nasalcongestion":
                dialog.setContentView(R.layout.dialog_nasalcongestion_info);
                dialog.setTitle("Nasal Congestion information");
                break;
            case "runnynose":
                dialog.setContentView(R.layout.dialog_runnynose_info);
                dialog.setTitle("Runny Nose information");
                break;
            case "tiredness":
                dialog.setContentView(R.layout.dialog_tiredness_info);
                dialog.setTitle("Tiredness information");
                break;
            case "headache":
                dialog.setContentView(R.layout.dialog_headache_info);
                dialog.setTitle("Headache information");
                break;
            case "diarrhea":
                dialog.setContentView(R.layout.dialog_diarrhea_info);
                dialog.setTitle("Diarrhea information");
                break;
            default:

        }
        dialog.show();
    }

    private float countAllRatings(){
        return diarrheaRatingBar.getRating() + runnyNoseRatingBar.getRating() + nasalCongestionRatingBar.getRating() + headacheRatingBar.getRating()
                + SoreThroatRatingBar.getRating() + breathingRatingBar.getRating() + tirednessRatingBar.getRating() + coughRatingBar.getRating() + feverRatingBar.getRating();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRsToRl:
                ((MainActivity)getActivity()).setViewPager(4);

                totalSeverityCount = countAllRatings();

                System.out.println(totalSeverityCount);
                totalSeverityCount = 0;
                break;

            case R.id.btnRsToStart:
                ((MainActivity)getActivity()).setViewPager(0);
                break;

            case R.id.btnFeverDialog:
                openDialog("fever");
                break;

            case R.id.btnCoughDialog:
                openDialog("cough");
                break;

            case R.id.btnBreathingDialog:
                openDialog("breathing");
                break;

            case R.id.btnSoreThroatDialog:
                openDialog("throat");
                break;

            case R.id.btnNasalCongestionDialog:
                openDialog("nasalcongestion");
                break;

            case R.id.btnRunnyNoseDialog:
                openDialog("runnynose");
                break;

            case R.id.btnTirednessDialog:
                openDialog("tiredness");
                break;

            case R.id.btnHeadacheDialog:
                openDialog("headache");
                break;

            case R.id.btnDiarrheaDialog:
                openDialog("diarrhea");
                break;

            case R.id.btnClearFeverRating:
                feverRatingBar.setRating(0);
                break;
            case R.id.btnClearCoughRating:
                coughRatingBar.setRating(0);
                break;
            case R.id.btnClearBreathingRating:
                breathingRatingBar.setRating(0);
                break;
            case R.id.btnClearSoreThroatRating:
                SoreThroatRatingBar.setRating(0);
                break;
            case R.id.btnClearNasalCongestionRating:
                nasalCongestionRatingBar.setRating(0);
                break;
            case R.id.btnCleaarRunnyNoseRating:
                runnyNoseRatingBar.setRating(0);
                break;
            case R.id.btnClearTirednessRating:
                tirednessRatingBar.setRating(0);
                break;
            case R.id.btnClearHeadacheRating:
                headacheRatingBar.setRating(0);
                break;
            case R.id.btnClearDiarrheaRating:
                diarrheaRatingBar.setRating(0);
                break;
        }

    }



}
