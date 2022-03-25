package com.example.cp3406utilityapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private final String name;
    private final String artist;
    private final int popularity;
    private final String id;

    public Song(String name, String artist, int popularity, String id) {
        this.name = name;
        this.artist = artist;
        this.popularity = popularity;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getId() {
        return id;
    }

    protected Song(Parcel in) {
        // Create new song from string array in parcel.
        String[] data = new String[4];
        // Read string data from parcel.
        in.readStringArray(data);
        this.name = data[0];
        this.artist = data[1];
        this.popularity = Integer.parseInt(data[2]);
        this.id = data[3];
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.name, this.artist});
    }
}
