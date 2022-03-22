package com.example.cp3406utilityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static Button getSongsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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