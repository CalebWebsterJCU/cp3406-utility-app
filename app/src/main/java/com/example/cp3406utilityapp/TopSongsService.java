package com.example.cp3406utilityapp;

import android.util.Log;

import com.neovisionaries.i18n.CountryCode;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;

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

    public interface ClientCredentialsCallback {
        void complain(String tag, String errorMessage);

        void respond();
    }

    public void connectClientCredentials(ClientCredentialsCallback toGetTopSongs) {
        try {
            // Create a credentials request and get access token.
            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            ClientCredentials clientCreds = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCreds.getAccessToken());
            Log.e(TAG, "Connected! Access token expires in: " + clientCreds.getExpiresIn());
            // Respond to getTopSongs().
            toGetTopSongs.respond();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            // Complain to getTopSongs().
            toGetTopSongs.complain(TAG, "Failed to connect to Spotify API.");
        }
    }

    public interface TopSongsCallback {
        void complain(String tag, String errorMessage);

        void respond(ArrayList<IPlaylistItem> songs);
    }

    public void getTopSongs(TopSongsCallback toGetSongs) {
        connectClientCredentials(new ClientCredentialsCallback() {
            @Override
            // Tell connectClientCredentials() how to complain.
            public void complain(String tag, String errorMessage) {
                toGetSongs.complain(tag, errorMessage);
            }

            @Override
            // Tell connectClientCredentials() how to respond.
            public void respond() {
                try {
                    GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(albumId).limit(50).market(CountryCode.AU).build();
                    PlaylistTrack[] playlistTracks = getPlaylistsItemsRequest.execute().getItems();
                    // Convert PlaylistTrack to IPlaylistItem???
                    ArrayList<IPlaylistItem> songs = new ArrayList<>();
                    for (int i = 0; i < 50; i++) {
                        songs.add(playlistTracks[i].getTrack());
                    }
                    toGetSongs.respond(songs);
                } catch (IOException | SpotifyWebApiException | ParseException e) {
                    toGetSongs.complain(TAG, "Failed to get top songs.");
                }
            }
        });

    }
}
