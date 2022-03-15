package com.example.cp3406utilityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se.michaelthelin.spotify.model_objects.IPlaylistItem;

public class SongListAdapter extends ArrayAdapter<IPlaylistItem> {

    private Context context;
    private int resource;

    public SongListAdapter(Context context, int resource, ArrayList<IPlaylistItem> songs) {
        super(context, resource, songs);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IPlaylistItem song = getItem(position);
        // Layout inflater used to convert resource view into usable format.
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        // Now we can fill the converted view with our song data.
        TextView songNameText = convertView.findViewById(R.id.tv_songName);
        TextView artistNameText = convertView.findViewById(R.id.tv_artistName);
        // TODO: get artist name.
        songNameText.setText(String.valueOf(song.getName()));
//        artistNameText.setText(String.valueOf(song.getArtist()));

        return convertView;
    }
}
