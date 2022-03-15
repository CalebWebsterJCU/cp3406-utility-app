package com.example.cp3406utilityapp;

import android.util.Log;

import com.neovisionaries.i18n.CountryCode;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

public class TopSongsService {

    public static final String TAG = "TopSongsService";
    public static final String albumId = "37i9dQZF1DXcBWIGoYBM5M";
    private final SpotifyApi spotifyApi;

    public TopSongsService(String clientId, String clientSecret) {
        // Initialize Spotify API.
        spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).build();
    }

    public boolean connectClientCredentials() {
        try {
            // Create a credentials request and get access token.
            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            ClientCredentials clientCreds = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCreds.getAccessToken());
            Log.e(TAG, "Successfully connected to Spotify API.");
            Log.e(TAG, "Access token expires in: " + clientCreds.getExpiresIn());
            return true;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            Log.e(TAG, "Failed to connect to Spotify API.");
            return false;
        }
    }

    public void getTopSongs(TopSongsCallback topSongsCallback) {
        try {
            connectClientCredentials();
            GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(albumId).limit(50).market(CountryCode.AU).build();
            PlaylistTrack[] playlistTracks = getPlaylistsItemsRequest.execute().getItems();
            // Convert PlaylistTrack to IPlaylistItem???
            ArrayList<IPlaylistItem> songs = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                songs.add(playlistTracks[i].getTrack());
            }
            System.out.println("Successfully retrieved album tracks.");
            topSongsCallback.onResponse(songs);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Failed to retrieve album tracks.");
            topSongsCallback.onError("Failed to retrieve album tracks.");
        }
    }

    public interface TopSongsCallback {
        void onError(String errorMessage);

        void onResponse(ArrayList<IPlaylistItem> songs);
    }
}
