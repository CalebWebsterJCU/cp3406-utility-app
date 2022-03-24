package com.example.cp3406utilityapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SongListAdapter extends ArrayAdapter<Song> {

    private Context context;
    private int resource;

    public SongListAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);
        // Layout inflater used to convert resource view into usable format.
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        // Now we can fill the converted view with our song data.
        TextView songNameText = convertView.findViewById(R.id.tv_songName);
        TextView artistNameText = convertView.findViewById(R.id.tv_artistName);
        TextView rankText = convertView.findViewById(R.id.tv_rank);
        TextView popularityText = convertView.findViewById(R.id.tv_popularity);
        songNameText.setText(song.getName());
        artistNameText.setText(song.getArtist());
        rankText.setText(String.format(Locale.getDefault(), "Rank: %d", position+1));
        popularityText.setText(String.format(Locale.getDefault(), "Popularity: %d%%", song.getPopularity()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String uri = String.format("spotify:track:%s", song.getId());
//                Intent launchSpotify = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                launchSpotify.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(launchSpotify);

                String url = String.format("https://open.spotify.com/track/%s", song.getId());
                Intent launchSpotify = new Intent(Intent.ACTION_VIEW);
                launchSpotify.setData(Uri.parse(url));
                launchSpotify.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(launchSpotify);
            }
        });

        return convertView;
    }
}
