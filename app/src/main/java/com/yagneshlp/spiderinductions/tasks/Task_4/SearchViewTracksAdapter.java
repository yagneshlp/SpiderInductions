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
import com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist.TrackList;

import java.util.List;

public class SearchViewTracksAdapter extends RecyclerView.Adapter<SearchViewTracksAdapter.SearchViewTracksHolder> {

    private List<TrackList> movies;
    private int rowLayout;
    private Context context;


    public static class SearchViewTracksHolder extends RecyclerView.ViewHolder {
        FrameLayout trackLayout;
        TextView trackName;
        TextView artistNAme;
        TextView movieDescription;
        TextView rating;


        public SearchViewTracksHolder(View v) {
            super(v);
            trackLayout = (FrameLayout) v.findViewById(R.id.trackLayout);
            trackName = (TextView) v.findViewById(R.id.item_name);
            artistNAme = (TextView) v.findViewById(R.id.additional_info);

        }
    }

    public SearchViewTracksAdapter(List<com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist.TrackList> tracks, int rowLayout, Context context) {
        this.movies = tracks;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SearchViewTracksHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SearchViewTracksHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchViewTracksHolder holder, final int position) {
        holder.trackName.setText(movies.get(position).getTrack().getTrackName());
        holder.artistNAme.setText(movies.get(position).getTrack().getArtistName());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}