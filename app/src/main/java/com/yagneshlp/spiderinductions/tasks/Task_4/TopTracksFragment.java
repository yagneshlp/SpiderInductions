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
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.Track;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.TrackList;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.TrackListResponse;
import com.yagneshlp.spiderinductions.tasks.Task_4.Adapters.TracksAdapter;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.ApiClient;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.ApiInterface;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopTracksFragment extends Fragment {

    List<TrackList> trackList;
    RelativeLayout mainLayout,progressLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_tracks,
                container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.topTracks);
        mainLayout = view.findViewById(R.id.mainLayout);
        progressLayout = view.findViewById(R.id.progressLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Track track = trackList.get(position).getTrack();
                //Toast.makeText(getApplicationContext(), track.getTrackName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), TrackViewActivity.class);
                i.putExtra("track_id", track.getTrackId());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TrackListResponse> callTopTracks = apiService.getTopRatedSongs(new Constants().getApiKey(),1,15,"in");

        callTopTracks.enqueue(new Callback<TrackListResponse>() {
            @Override
            public void onResponse(Call<TrackListResponse>call, Response<TrackListResponse> response) {
                trackList = response.body().getMessage().getBody().getTrackList();
                recyclerView.setAdapter(new TracksAdapter(trackList, R.layout.track_listitem, getContext()));
                progressLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);

                //Log.d(TAG, "Number of tracks received: " + trackList.get(0).getTrack().getTrackName());
            }

            @Override
            public void onFailure(Call<TrackListResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("ToptracksFragment", t.toString());
            }
        });
        return view;
    }

    }
