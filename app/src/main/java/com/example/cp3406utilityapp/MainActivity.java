package com.example.cp3406utilityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "settings";
    private SharedPreferences settingsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsData = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRgbBackground(findViewById(R.id.rootLayout), settingsData.getBoolean("isRgbMode", false));
    }

    private void setRgbBackground(View rootLayout, boolean isOn) {
        if (isOn) {
            rootLayout.setBackgroundResource(R.drawable.colour_list);
            AnimationDrawable anim = (AnimationDrawable) rootLayout.getBackground();
            anim.setEnterFadeDuration(1000);
            anim.setExitFadeDuration(1000);
            anim.start();
        } else {
            rootLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void openSongs(View view) {
        Intent toSongs = new Intent(MainActivity.this, SongsActivity.class);
        startActivity(toSongs);
    }

    public void openSettings(View view) {
        Intent toSettings = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(toSettings);
    }
}