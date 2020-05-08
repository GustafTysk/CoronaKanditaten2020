package com.example.coronakanditaten2020;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;


public class MySettingsFragment extends PreferenceFragmentCompat implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private EditTextPreference signaturePreference;
    private SwitchPreferenceCompat enableUserSettings;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_my_settings, rootKey);

        enableUserSettings = findPreference("enable_user_settings");
        signaturePreference = findPreference("signature");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "enable_user_settings":
                if (enableUserSettings.isChecked()){
                    showOrHideUserSettings(true);
                }else showOrHideUserSettings(false);
                break;
        }
    }

    public void showOrHideUserSettings(Boolean visible){
        signaturePreference.setVisible(visible);
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }
    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

}
