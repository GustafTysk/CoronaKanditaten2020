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

    private Button btnRsToStart;
    private Button btnRsToRl;
    RatingBar feverRatingBar, coughRatingBar, tirednessRatingBar, breathingRatingBar, throatRatingBar, headacheRatingBar, nasalCongestionRatingBar, runnyNoseRatingBar, diarrheaRatingBar;
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
        coughRatingBar = (RatingBar) view.findViewById(R.id.ratingBarCough);
        tirednessRatingBar = (RatingBar) view.findViewById(R.id.ratingBarTiredness);
        breathingRatingBar = (RatingBar) view.findViewById(R.id.ratingBarBreathingDifficulties);
        throatRatingBar = (RatingBar) view.findViewById(R.id.ratingBarSoreThroat);
        headacheRatingBar = (RatingBar) view.findViewById(R.id.ratingBarHeadache);
        nasalCongestionRatingBar = (RatingBar) view.findViewById(R.id.ratingBarNasalCongestion);
        runnyNoseRatingBar = (RatingBar) view.findViewById(R.id.ratingBarRunnyNose);
        diarrheaRatingBar = (RatingBar) view.findViewById(R.id.ratingBarDiarrhea);
        System.out.println(feverSeverity);

        return view;
    }
    public void openDialog(String symptom) {

        switch (symptom){
            case "fever":
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_fever_info);
                dialog.setTitle("Fever information");
                dialog.show();
                break;
            case "cough":
                Dialog dialog2 = new Dialog(getContext());
                dialog2.setContentView(R.layout.dialog_cough_info);
                dialog2.setTitle("Cough information");
                dialog2.show();
                break;

            case "breathing":
                Dialog dialog3= new Dialog(getContext());
                dialog3.setContentView(R.layout.dialog_breathing_info);
                dialog3.setTitle("Breathing Difficulty information");
                dialog3.show();
                break;

            case "throat":
                Dialog dialog4= new Dialog(getContext());
                dialog4.setContentView(R.layout.dialog_sorethroat_info);
                dialog4.setTitle("Sore Throat information");
                dialog4.show();
                break;

            case "nasalcongestion":
                Dialog dialog5= new Dialog(getContext());
                dialog5.setContentView(R.layout.dialog_nasalcongestion_info);
                dialog5.setTitle("Nasal Congestion information");
                dialog5.show();
                break;

            case "runnynose":
                Dialog dialog6= new Dialog(getContext());
                dialog6.setContentView(R.layout.dialog_runnynose_info);
                dialog6.setTitle("Runny Nose information");
                dialog6.show();
                break;

            case "tiredness":
                Dialog dialog7= new Dialog(getContext());
                dialog7.setContentView(R.layout.dialog_tiredness_info);
                dialog7.setTitle("Tiredness information");
                dialog7.show();
                break;

            case "headache":
                Dialog dialog8= new Dialog(getContext());
                dialog8.setContentView(R.layout.dialog_headache_info);
                dialog8.setTitle("Headache information");
                dialog8.show();
                break;

            case "diarrhea":
                Dialog dialog9= new Dialog(getContext());
                dialog9.setContentView(R.layout.dialog_diarrhea_info);
                dialog9.setTitle("Diarrhea information");
                dialog9.show();
                break;
            default:

        }


    }

    private float countAllRatings(){
        return diarrheaRatingBar.getRating() + runnyNoseRatingBar.getRating() + nasalCongestionRatingBar.getRating() + headacheRatingBar.getRating()
                + throatRatingBar.getRating() + breathingRatingBar.getRating() + tirednessRatingBar.getRating() + coughRatingBar.getRating() + feverRatingBar.getRating();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRsToRl:
                Toast.makeText(getActivity(), "Going to Report Location", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(4);

                totalSeverityCount = countAllRatings();

                System.out.println(totalSeverityCount);
                totalSeverityCount = 0;
                break;

            case R.id.btnRsToStart:
                Toast.makeText(getActivity(), "Going to Start Page", Toast.LENGTH_SHORT).show();
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
        }

    }


}
