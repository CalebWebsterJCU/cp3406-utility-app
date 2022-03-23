package com.example.cp3406utilityapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private final String name;
    private final String artist;

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    protected Song(Parcel in) {
        // Create new song from string array in parcel.
        String[] data = new String[2];
        // Read string data from parcel.
        in.readStringArray(data);
        this.name = data[0];
        this.artist = data[1];
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
        parcel.writeStringArray(new String[] {this.name, this.artist});
    }
}
