package com.yagneshlp.spiderinductions.tasks.Task_4.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.TrackList;

import java.util.List;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder>  {

    private List<TrackList> movies;
    private int rowLayout;
    private Context context;


    public static class TracksViewHolder extends RecyclerView.ViewHolder {
        FrameLayout trackLayout;
        TextView trackName;
        TextView artistNAme;
        TextView movieDescription;
        TextView rating;


        public TracksViewHolder(View v) {
            super(v);
            trackLayout = (FrameLayout) v.findViewById(R.id.trackLayout);
            trackName = (TextView) v.findViewById(R.id.track_name);
            artistNAme = (TextView) v.findViewById(R.id.artist_name);

        }
    }

    public TracksAdapter(List<TrackList> tracks, int rowLayout, Context context) {
        this.movies = tracks;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TracksViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TracksViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TracksViewHolder holder, final int position) {
        holder.trackName.setText(movies.get(position).getTrack().getTrackName());
        holder.artistNAme.setText(movies.get(position).getTrack().getArtistName());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}