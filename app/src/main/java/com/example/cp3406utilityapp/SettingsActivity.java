package com.example.cp3406utilityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences settingsData;

    private EditText playlistIdInput;
    private EditText maxNumSongsInput;
    private SwitchCompat darkModeSwitch;

    private String playlistId;
    private int maxNumSongs;
    private boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsData = getSharedPreferences("settings", Context.MODE_PRIVATE);

        playlistIdInput = findViewById(R.id.et_playlistId);
        maxNumSongsInput = findViewById(R.id.et_maxNoSongs);
        darkModeSwitch = findViewById(R.id.sw_darkMode);

        playlistId = settingsData.getString("playlistId", null);
        maxNumSongs = settingsData.getInt("maxNumSongs", 0);
        isDarkMode = settingsData.getBoolean("isDarkMode", false);
    }

    public void saveSettings(View view) {
        finish();
    }
}