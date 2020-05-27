package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StartPageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Fragment StartPage";

    private Button btnStartToStatistics;
    private Button btnStartToHeatmap;
    private Button btnStartToRs;
    private Button btnStartToForum;
    private Button btnLogout;
    private ImageButton btnSettings;
    boolean setuprs=false;
    boolean setupstat=false;
    boolean setuppdone=false;

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
        btnStartToForum = (Button) view.findViewById(R.id.btnStartToForum);
        btnStartToForum.setOnClickListener(this);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        btnSettings = (ImageButton) view.findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSettings:
                ((MainActivity)getActivity()).setViewPager(0);

                break;
            case R.id.btnStartToHeatmap:
                if(((MainActivity)getActivity()).datahandler.gotlocation==true){
                    if ( !setuppdone){

                        ((MainActivity) getActivity()).heatmapFragment.setup(((MainActivity) getActivity()).datahandler.getHeatmaplocations());
                        setuppdone=true;
                    }

                    ((MainActivity)getActivity()).setViewPager(3);
                    ((MainActivity)getActivity()).heatmapFragment.setHeatmapBottomNav();
                    ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_heatmap);}
                break;
            case R.id.btnStartToStatistics:
                System.out.println("yays2222y");
                if(((MainActivity)getActivity()).datahandler.gotuserinfo &&((MainActivity)getActivity()).datahandler.gotlocation){
                    System.out.println("yay2233331313sy");
                    if (!setupstat){
                        System.out.println("yaysy");
                    ((MainActivity)getActivity()).statisticsFragment.setupstatistik();
                    setupstat=true;
                    }
                ((MainActivity)getActivity()).setViewPager(2);
                ((MainActivity)getActivity()).statisticsFragment.setStatisticsBottomNav();
                ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_statistics);}
                break;
            case R.id.btnStartToRs:
                if(((MainActivity)getActivity()).datahandler.gotUserlocation){
                    if(((MainActivity)getActivity()).datahandler.Userlocations.size()>=0 && !setuprs){
                        ((MainActivity)getActivity()).reportLocationFragment.userLocations=((MainActivity)getActivity()).datahandler.Userlocations;
                        ((MainActivity)getActivity()).reportSymptomsFragment.SetuppUsersymtoms();
                        ((MainActivity)getActivity()).reportLocationFragment.SetUppPage();
                        setuprs=true;
                    }
                    ((MainActivity)getActivity()).setViewPager(4);
                    ((MainActivity)getActivity()).reportSymptomsFragment.setReportSymptomsBottomNav();
                    ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_report_symptoms);
                }
                break;
            case R.id.btnStartToForum:
                if (((MainActivity)getActivity()).datahandler.viewPosts.size()!=0 && ((MainActivity)getActivity()).datahandler.user!=null){
                    ((MainActivity)getActivity()).setViewPager(6);
                    ((MainActivity)getActivity()).forumFragment.setForumBottomNav();
                    ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_forum);
                    ((MainActivity)getActivity()).forumFragment.runwhenload();
                }
                ((MainActivity)getActivity()).setViewPager(6);
                ((MainActivity)getActivity()).forumFragment.setForumBottomNav();
                ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_forum);
                break;
            case R.id.btnLogout:

                if(logOut()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                    Toast.makeText(getContext(), getString(R.string.success_logout), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), getString(R.string.fail_logout), Toast.LENGTH_LONG).show();
                }
                break;

            default:
        }
    }

    public boolean logOut(){

        return true;
    }
}
