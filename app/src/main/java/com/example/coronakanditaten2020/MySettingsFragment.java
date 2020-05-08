package com.example.coronakanditaten2020;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_my_settings, rootKey);

        System.out.println("INNE I MYSETTINGSFRAGMENT");
    }
}
