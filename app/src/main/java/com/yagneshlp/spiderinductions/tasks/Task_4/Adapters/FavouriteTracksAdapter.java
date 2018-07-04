package com.yagneshlp.spiderinductions.tasks.Task_4.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteTrack;

import java.util.List;

public class FavouriteTracksAdapter extends RecyclerView.Adapter<FavouriteTracksAdapter.FavouriteTracksHolder>  {

    private List<FavouriteTrack> movies;
    private int rowLayout;
    private Context context;


    public static class FavouriteTracksHolder extends RecyclerView.ViewHolder {
        FrameLayout trackLayout;
        TextView trackName;
        TextView artistNAme;



        public FavouriteTracksHolder(View v) {
            super(v);
            trackLayout = (FrameLayout) v.findViewById(R.id.trackLayout);
            trackName = (TextView) v.findViewById(R.id.track_name);
            artistNAme = (TextView) v.findViewById(R.id.artist_name);

        }
    }

    public FavouriteTracksAdapter(List<FavouriteTrack> tracks, int rowLayout, Context context) {
        this.movies = tracks;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public FavouriteTracksHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FavouriteTracksHolder(view);
    }


    @Override
    public void onBindViewHolder(FavouriteTracksHolder holder, final int position) {
        holder.trackName.setText(movies.get(position).getTrack_name());
        holder.artistNAme.setText(movies.get(position).getArtist_name());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}