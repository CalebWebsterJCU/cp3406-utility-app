package com.example.cp3406utilityapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private static final String SHARED_PREFS_NAME = "settings";
    private SharedPreferences settingsData;

    private EditText playlistIdInput;
    private EditText songLimitInput;
    private SwitchCompat limitSongsSwitch;
    private SwitchCompat darkModeSwitch;
    private SwitchCompat rgbModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsData = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        // Define fields.
        playlistIdInput = findViewById(R.id.et_playlistId);
        songLimitInput = findViewById(R.id.et_songLimit);
        limitSongsSwitch = findViewById(R.id.sw_limitSongs);
        darkModeSwitch = findViewById(R.id.sw_darkMode);
        rgbModeSwitch = findViewById(R.id.sw_rgbMode);
        // Fill data fields from SharedPreferences.
        playlistIdInput.setText(settingsData.getString("playlistId", "37i9dQZF1DXcBWIGoYBM5M"));

        limitSongsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Show/hide song limit input box.
                if (isChecked) {
                    songLimitInput.setVisibility(View.VISIBLE);
                } else {
                    songLimitInput.setVisibility(View.GONE);
                }
            }
        });

        limitSongsSwitch.setChecked(settingsData.getBoolean("willLimitSongs", false));
        if (limitSongsSwitch.isChecked()) {
            songLimitInput.setText(String.format(Locale.getDefault(), "%d", settingsData.getInt("songLimit", 0)));
        }
        darkModeSwitch.setChecked(settingsData.getBoolean("isDarkMode", false));
        rgbModeSwitch.setChecked(settingsData.getBoolean("isRgbMode", false));

        darkModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change night mode on or off.
                if (darkModeSwitch.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        rgbModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setRgbBackground(isChecked);
            }
        });
    }

    @Override
    protected void onResume() {
        // Set rgb background on resume.
        super.onResume();
        setRgbBackground(rgbModeSwitch.isChecked());
        Log.d(TAG, "Activity Resumed.");
    }

    private void setRgbBackground(boolean isOn) {
        View rootLayout = findViewById(R.id.rootLayout);
        if (isOn) {
            // Set root layout background and start animation.
            rootLayout.setBackgroundResource(R.drawable.colour_list);
            AnimationDrawable anim = (AnimationDrawable) rootLayout.getBackground();
            anim.setEnterFadeDuration(1000);
            anim.setExitFadeDuration(1000);
            anim.start();
            System.out.println("Started animation");
        } else {
            rootLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void saveSettings(View view) {
        SharedPreferences.Editor editor = settingsData.edit();
        if (limitSongsSwitch.isChecked()) {
            int songLimit = Integer.parseInt(songLimitInput.getText().toString());
            if (songLimit < 1 || songLimit > 100) {
                Toast.makeText(getApplicationContext(), "Invalid limit: must be between 1 and 100.", Toast.LENGTH_SHORT).show();
                return;
            }
            editor.putInt("songLimit", Integer.parseInt(songLimitInput.getText().toString()));
        }
        editor.putBoolean("willLimitSongs", limitSongsSwitch.isChecked());
        editor.putString("playlistId", playlistIdInput.getText().toString());
        editor.putBoolean("isDarkMode", darkModeSwitch.isChecked());
        editor.putBoolean("isRgbMode", rgbModeSwitch.isChecked());
        editor.apply();
        finish();
    }
}