package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.helper.RecyclerTouchListener;
import com.yagneshlp.spiderinductions.pojo.pojo_artistlist.Artist;
import com.yagneshlp.spiderinductions.pojo.pojo_artistlist.ArtistList;
import com.yagneshlp.spiderinductions.pojo.pojo_artistlist.ArtistListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopArtistsFragment extends Fragment {

    List<ArtistList> artistList;
    RelativeLayout mainLayout,progressLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_artist,
                container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.topArtists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mainLayout = view.findViewById(R.id.mainLayout);
        progressLayout = view.findViewById(R.id.progressLayout);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Artist artist = artistList.get(position).getArtist();
                //Toast.makeText(getContext(), artist.getArtistName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), ArtistViewActivity.class);
                i.putExtra("artist_id", artist.getArtistId());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArtistListResponse> callTopArtists = apiService.getTopRatedArtists(new Constants().getApiKey(),1,15,"in");
        callTopArtists.enqueue(new Callback<ArtistListResponse>() {
            @Override
            public void onResponse(Call<ArtistListResponse>call, Response<ArtistListResponse> response) {
                artistList = response.body().getMessage().getBody().getArtistList();
                recyclerView.setAdapter(new ArtistsAdapter(artistList, R.layout.artist_listitem, getContext()));
                progressLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
                //Log.d(TAG, "Number of tracks received: " + trackList.get(0).getTrack().getTrackName());
            }

            @Override
            public void onFailure(Call<ArtistListResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("TopArtistFragment", t.toString());
            }
        });
        return view;
    }

}
