package com.example.cp3406utilityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;


public class SongsActivity extends AppCompatActivity {

    private static final String TAG = "SongsActivity";

    private static final String SHARED_PREFS_NAME = "settings";
    private static final String CLIENT_ID = "87f7150f2a784eb0bc252cc29436f4af";
    private static final String CLIENT_SECRET = "ba29f02133bb422487493547c3f2fa04";
    private static final String DEFAULT_PLAYLIST = "37i9dQZF1DXcBWIGoYBM5M";
    private static final int DEFAULT_SONG_LIMIT = 50;
    private SharedPreferences settingsData;
    TopSongsService topSongsService;
    ListView songsListView;
    ArrayList<Song> savedSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        settingsData = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        songsListView = findViewById(R.id.lv_songs);

        if (savedInstanceState == null) {
            new GetSongsTask().execute();
            Log.d(TAG, "Loaded from API call.");
        } else {
            savedSongs = savedInstanceState.getParcelableArrayList("songsArrayList");
            songsListView.setAdapter(new SongListAdapter(getApplicationContext(), R.layout.song_list_item, savedSongs));
            Log.d(TAG, "Loaded from saved state.");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("songsArrayList", savedSongs);
    }

    private class GetSongsTask extends AsyncTask<Void, Void, ArrayList<Song>> {

        @Override
        protected ArrayList<Song> doInBackground(Void... voids) {
            String albumId = settingsData.getString("playlistId", DEFAULT_PLAYLIST);
            boolean limitSongs = settingsData.getBoolean("willLimitSongs", false);
            int songLimit = settingsData.getInt("songLimit", DEFAULT_SONG_LIMIT);
            try {
                topSongsService = new TopSongsService(CLIENT_ID, CLIENT_SECRET);
                topSongsService.connectClientCredentials();
                return topSongsService.getTopSongs(albumId, limitSongs, songLimit);
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Song> loadedSongs) {
            if (loadedSongs != null) {
                savedSongs = loadedSongs;
                songsListView.setAdapter(new SongListAdapter(getApplicationContext(), R.layout.song_list_item, loadedSongs));
            } else {
                Toast.makeText(getApplicationContext(), "Failed to get top songs.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SongsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}