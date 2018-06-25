package com.yagneshlp.spiderinductions;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.fabtransitionactivity.SheetLayout;
import com.yagneshlp.spiderinductions.helper.RecyclerTouchListener;
import com.yagneshlp.spiderinductions.pojo.pojo_artists.ArtistList;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.TrackListResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.Track;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.TrackList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Task4Activity extends AppCompatActivity implements  SheetLayout.OnFabAnimationEndListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "ed01bfe66adb321a2b23bed6c4b7d7ba";
    private static final int REQUEST_CODE = 1;
    SheetLayout mSheetLayout;
    List<TrackList> trackList;
    List<ArtistList> artistList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.musixmatchOrange));

        }
        mSheetLayout = findViewById(R.id.bottom_sheet);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        mSheetLayout.setFab(fab);
        mSheetLayout.setFabAnimationEndListener(this);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.topTracks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Track track = trackList.get(position).getTrack();
                Toast.makeText(getApplicationContext(), track.getTrackName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Task4Activity.this, TrackViewActivity.class);
                i.putExtra("track_id", track.getTrackId());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Goodplays");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSheetLayout.expandFab();
            //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              //          .setAction("Action", null).show();
            }
        });



        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TrackListResponse> callTopTracks = apiService.getTopRatedSongs(API_KEY,1,15,"us");

        callTopTracks.enqueue(new Callback<TrackListResponse>() {
            @Override
            public void onResponse(Call<TrackListResponse>call, Response<TrackListResponse> response) {
                trackList = response.body().getMessage().getBody().getTrackList();
                recyclerView.setAdapter(new TracksAdapter(trackList, R.layout.track_listitem, getApplicationContext()));

                //Log.d(TAG, "Number of tracks received: " + trackList.get(0).getTrack().getTrackName());
            }

            @Override
            public void onFailure(Call<TrackListResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        /*

        Call<ArtistResponse> callTopArtists = apiService.getTopRatedArtists(API_KEY,1,15,"in");
        callTopArtists.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse>call, Response<ArtistResponse> response) {
                artistList = response.body().getMessage().getBody().getArtistList();
                recyclerView.setAdapter(new ArtistsAdapter(artistList, R.layout.artist_listitem, getApplicationContext()));
                //Log.d(TAG, "Number of tracks received: " + trackList.get(0).getTrack().getTrackName());
            }

            @Override
            public void onFailure(Call<ArtistResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });*/
    }

    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            mSheetLayout.contractFab();
        }
    }
}

