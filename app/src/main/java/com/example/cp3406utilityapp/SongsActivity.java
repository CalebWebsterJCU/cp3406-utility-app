package com.example.cp3406utilityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import se.michaelthelin.spotify.model_objects.IPlaylistItem;

public class SongsActivity extends AppCompatActivity {

    private static final String TAG = "SongsActivity";

    ListView songsListView;
    TopSongsService songsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        songsListView = (ListView) findViewById(R.id.lv_songs);

        songsService = new TopSongsService("87f7150f2a784eb0bc252cc29436f4af", "ba29f02133bb422487493547c3f2fa04");
        loadSongs();
    }

    public void loadSongs() {
        songsService.getTopSongs(new TopSongsService.TopSongsCallback() {
            @Override
            // Tell getTopSongs() how to complain.
            public void complain(String tag, String errorMessage) {
                Log.e(tag, errorMessage);
                Toast.makeText(getApplicationContext(), "Failed to retrieve top songs.", Toast.LENGTH_LONG).show();
                // TODO: Go back to main.
            }

            @Override
            // Tell getTopSongs() how to respond.
            public void respond(ArrayList<IPlaylistItem> songs) {
                SongListAdapter adapter = new SongListAdapter(SongsActivity.this, R.layout.song_list_item, songs);
                songsListView.setAdapter(adapter);
                // TODO: implement.
            }
        });
    }
}