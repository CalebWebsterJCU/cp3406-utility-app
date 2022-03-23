package com.example.cp3406utilityapp;

import android.util.Log;

import com.neovisionaries.i18n.CountryCode;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

public class TopSongsService {

    // 37i9dQZF1DXcBWIGoYBM5M
    private final SpotifyApi spotifyApi;
    public static final String TAG = "TopSongsService";

    public TopSongsService(String clientId, String clientSecret) {
        // Initialize Spotify API.
        spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).build();
    }

    public boolean connectClientCredentials()
            throws IOException, SpotifyWebApiException, ParseException {
        // Create a credentials request and get access token.
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        ClientCredentials clientCreds = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCreds.getAccessToken());
        Log.e(TAG, "Successfully connected to Spotify API.");
        Log.e(TAG, "Access token expires in: " + clientCreds.getExpiresIn() + " seconds.");
        return true;
    }

    public ArrayList<Track> getTopSongs(String albumId, int songLimit)
            throws IOException, SpotifyWebApiException, ParseException {
        GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(albumId).limit(songLimit).market(CountryCode.AU).build();
        PlaylistTrack[] playlistTracks = getPlaylistsItemsRequest.execute().getItems();
        Track[] tracks = new Track[50];
        for (int i = 0; i < 50; i++) {
            Track track = (Track) playlistTracks[i].getTrack();
            // PlaylistTrack.getTrack() can be a Episode or a Track, which both implement IPlaylistItem.
            // Since we know the items in this playlist are Tracks, we can cast to type Track.
            System.out.printf("%s by %s%n", track.getName(), Arrays.toString(track.getArtists()));
            tracks[i] = track;
        }
        Log.e(TAG, "Successfully retrieved album tracks.");
        return new ArrayList<>(Arrays.asList(tracks));
    }
}
