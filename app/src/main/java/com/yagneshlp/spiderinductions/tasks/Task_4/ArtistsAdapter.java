package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.pojo.pojo_artistlist.ArtistList;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder> {

    private List<ArtistList> movies;
    private int rowLayout;
    private Context context;


    public static class ArtistsViewHolder extends RecyclerView.ViewHolder {
        FrameLayout trackLayout;
        TextView trackName;
        TextView artistNAme;
        TextView movieDescription;
        TextView rating;


        public ArtistsViewHolder(View v) {
            super(v);
            trackLayout = (FrameLayout) v.findViewById(R.id.trackLayout);
            trackName = (TextView) v.findViewById(R.id.track_name);
            artistNAme = (TextView) v.findViewById(R.id.artist_name);

        }
    }

    public ArtistsAdapter(List<ArtistList> tracks, int rowLayout, Context context) {
        this.movies = tracks;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ArtistsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ArtistsViewHolder holder, final int position) {
        holder.trackName.setText(movies.get(position).getArtist().getArtistName());
        holder.artistNAme.setText(movies.get(position).getArtist().getArtistCountry());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}