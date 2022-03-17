package com.example.cp3406utilityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class SongsActivity extends AppCompatActivity {

    TopSongsService topSongsService;
    ListView songsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        topSongsService = new TopSongsService("87f7150f2a784eb0bc252cc29436f4af", "ba29f02133bb422487493547c3f2fa04");
        songsListView = findViewById(R.id.lv_songs);
        new GetSongsTask().execute();
    }

    class GetSongsTask extends AsyncTask<Void, Void, ArrayList<Track>> {

        @Override
        protected ArrayList<Track> doInBackground(Void... voids) {
            try {
                topSongsService.connectClientCredentials();
                return topSongsService.getTopSongs();
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Track> songs) {
            if (songs != null) {
                songsListView.setAdapter(new SongListAdapter(getApplicationContext(), R.layout.song_list_item, songs));
            } else {
                Toast.makeText(getApplicationContext(), "Failed to get top songs.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SongsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}