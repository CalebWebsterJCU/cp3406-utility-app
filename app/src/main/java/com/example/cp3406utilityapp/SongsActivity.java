package com.example.cp3406utilityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;

public class SongsActivity extends AppCompatActivity {

    TopSongsService topSongsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        topSongsService = new TopSongsService("87f7150f2a784eb0bc252cc29436f4af", "ba29f02133bb422487493547c3f2fa04");
        new GetSongsTask().execute();
    }

    class GetSongsTask extends AsyncTask<Void, Void, ArrayList<IPlaylistItem>> {

        @Override
        protected ArrayList<IPlaylistItem> doInBackground(Void... voids) {
            try {
                topSongsService.connectClientCredentials();
                return topSongsService.getTopSongs();
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                Toast.makeText(getApplicationContext(), "Failed to get top songs.", Toast.LENGTH_LONG);
            }
            return null;
        }
    }
}