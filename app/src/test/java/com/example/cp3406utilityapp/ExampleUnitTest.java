package com.example.cp3406utilityapp;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import se.michaelthelin.spotify.model_objects.specification.Track;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void serviceCanConnect() {
//        TopSongsService service = new TopSongsService("87f7150f2a784eb0bc252cc29436f4af", "ba29f02133bb422487493547c3f2fa04");
//        assertTrue(service.connectClientCredentials());
//    }
//
//    @Test
//    public void serviceCanGetTopSongs() {
//        TopSongsService service = new TopSongsService("87f7150f2a784eb0bc252cc29436f4af", "ba29f02133bb422487493547c3f2fa04");
//        service.connectClientCredentials();
//        ArrayList<Track> tracks = service.getTopSongs();
//        assertNotNull(tracks);
//        for (Track track : tracks) {
//            System.out.println(track.getName());
//        }
//    }
}