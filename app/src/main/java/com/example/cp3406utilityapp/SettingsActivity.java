package com.example.cp3406utilityapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "settings";
    private SharedPreferences settingsData;

    private EditText playlistIdInput;
    private EditText songLimitInput;
    private SwitchCompat darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsData = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        // Define fields.
        playlistIdInput = findViewById(R.id.et_playlistId);
        songLimitInput = findViewById(R.id.et_songLimit);
        darkModeSwitch = findViewById(R.id.sw_darkMode);
        // TODO: Read out playlist name.
        // Fill data fields from SharedPreferences.
        playlistIdInput.setText(settingsData.getString("playlistId", "37i9dQZF1DXcBWIGoYBM5M"));
        songLimitInput.setText(String.format(Locale.getDefault(), "%d", settingsData.getInt("songLimit", 50)));
        darkModeSwitch.setChecked(settingsData.getBoolean("isDarkMode", false));
    }

    public void saveSettings(View view) {
        SharedPreferences.Editor editor = settingsData.edit();
        // Set preferences from fields.
        editor.putString("playlistId", playlistIdInput.getText().toString());
        editor.putInt("songLimit", Integer.parseInt(songLimitInput.getText().toString()));
        editor.putBoolean("isDarkMode", darkModeSwitch.isChecked());
        editor.apply();
        finish();
    }
}