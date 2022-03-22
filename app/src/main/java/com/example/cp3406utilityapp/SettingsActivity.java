package com.example.cp3406utilityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences settingsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void saveSettings(View view) {
        finish();
    }
}