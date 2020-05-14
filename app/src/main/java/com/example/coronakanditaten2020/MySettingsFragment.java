package com.example.coronakanditaten2020;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;


public class MySettingsFragment extends PreferenceFragmentCompat implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private EditTextPreference usernamePreference;
    private SwitchPreferenceCompat notificationsPreference;
    private Preference removeUserLocationsPreference;
    private Preference removeUserPostsPreference;
    private Preference removeUserPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_my_settings, rootKey);

        notificationsPreference = findPreference("notifications");
        usernamePreference = findPreference("username");

        removeUserLocationsPreference = findPreference("remove_user_locations");
        removeUserLocationsPreference.setOnPreferenceClickListener(this);
        removeUserPostsPreference = findPreference("remove_user_posts");
        removeUserPostsPreference.setOnPreferenceClickListener(this);
        removeUserPreference = findPreference("remove_user");
        removeUserPreference.setOnPreferenceClickListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "notifications":
                if (notificationsPreference.isChecked()){
                    Toast.makeText(getContext(),"Notifications enabled", Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getContext(),"Notifications disabled", Toast.LENGTH_SHORT);
                }
                break;
            case "username":
                changeUsername("username");
                break;

        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        switch (key){
            case "remove_user_locations":
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.alert_title_locations))
                        .setMessage(getString(R.string.alert_message_locations))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeUserLocations();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case "remove_user_posts":
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.alert_title_posts))
                        .setMessage(getString(R.string.alert_message_posts))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeUserPosts();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case "remove_user":
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.alert_title_user))
                        .setMessage(getString(R.string.alert_message_user))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteUser();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            default:

        }

        return false;
    }


    private void changeUsername(String key){
        if (key.equals("username")){
            if (usernamePreference instanceof EditTextPreference){
                EditTextPreference editTextPreference =  (EditTextPreference)usernamePreference;
                if (editTextPreference.getText().trim().length() > 0){
                    String newUsername = editTextPreference.getText();
                    editTextPreference.setSummary(getString(R.string.on_changed_username) + editTextPreference.getText());

                    // TODO set USERS username = newUsername
                }else{
                    editTextPreference.setSummary(getString(R.string.invalid_username));
                }
            }
        }
    }

    public void removeUserLocations(){
        // TODO ta bort alla user locations

        Toast.makeText(getContext(),getString(R.string.alert_toast_locations),Toast.LENGTH_SHORT);
    }

    public void removeUserPosts(){
        // TODO ta bort alla user posts

        Toast.makeText(getContext(),getString(R.string.alert_toast_posts),Toast.LENGTH_SHORT);
    }

    public void deleteUser(){
        // TODO ta bort user
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
