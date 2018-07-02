package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.pojo.pojo_search_artistlist.ArtistList;
import com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist.TrackList;

import java.util.List;

public class SearchViewArtistsAdapter extends RecyclerView.Adapter<SearchViewArtistsAdapter.SearchViewArtistsHolder> {

    private List<ArtistList> movies;
    private int rowLayout;
    private Context context;


    public static class SearchViewArtistsHolder extends RecyclerView.ViewHolder {
        FrameLayout trackLayout;
        TextView trackName;
        TextView artistNAme;
        ImageView icon;



        public SearchViewArtistsHolder(View v) {
            super(v);
            trackLayout = (FrameLayout) v.findViewById(R.id.trackLayout);
            trackName = (TextView) v.findViewById(R.id.item_name);
            artistNAme = (TextView) v.findViewById(R.id.additional_info);
            icon = v.findViewById(R.id.icon);

        }
    }

    public SearchViewArtistsAdapter(List<ArtistList> tracks, int rowLayout, Context context) {
        this.movies = tracks;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SearchViewArtistsHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SearchViewArtistsHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchViewArtistsHolder holder, final int position) {
        holder.trackName.setText(movies.get(position).getArtist().getArtistName());
        holder.artistNAme.setText(movies.get(position).getArtist().getArtistCountry());
        holder.icon.setImageResource(R.drawable.ic_artist);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}