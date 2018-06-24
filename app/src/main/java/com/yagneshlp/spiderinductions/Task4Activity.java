package com.yagneshlp.spiderinductions;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.yagneshlp.spiderinductions.pojo.pojo_artists.ArtistList;
import com.yagneshlp.spiderinductions.pojo.pojo_artists.ArtistResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_tracks.MusicTrackResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_tracks.Track;
import com.yagneshlp.spiderinductions.pojo.pojo_tracks.TrackList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Task4Activity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "ed01bfe66adb321a2b23bed6c4b7d7ba";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.musixmatchOrange));

        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.topTracks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MusicTrackResponse> callTopTracks = apiService.getTopRatedSongs(API_KEY,1,15,"in");

        callTopTracks.enqueue(new Callback<MusicTrackResponse>() {
            @Override
            public void onResponse(Call<MusicTrackResponse>call, Response<MusicTrackResponse> response) {
                List<TrackList> trackList = response.body().getMessage().getBody().getTrackList();
                recyclerView.setAdapter(new TracksAdapter(trackList, R.layout.track_listitem, getApplicationContext()));
                //Log.d(TAG, "Number of tracks received: " + trackList.get(0).getTrack().getTrackName());
            }

            @Override
            public void onFailure(Call<MusicTrackResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });



        Call<ArtistResponse> callTopArtists = apiService.getTopRatedArtists(API_KEY,1,15,"in");
        callTopArtists.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse>call, Response<ArtistResponse> response) {
                List<ArtistList> artistList = response.body().getMessage().getBody().getArtistList();
                recyclerView.setAdapter(new ArtistsAdapter(artistList, R.layout.artist_listitem, getApplicationContext()));
                //Log.d(TAG, "Number of tracks received: " + trackList.get(0).getTrack().getTrackName());
            }

            @Override
            public void onFailure(Call<ArtistResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}

