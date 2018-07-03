package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.pojo.pojo_track.TrackResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_track.Track;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackViewActivity extends AppCompatActivity {

    Track track;
    private final static String API_KEY = "ed01bfe66adb321a2b23bed6c4b7d7ba";
    TextView tv_trackName,tv_artistName,tv_album,tv_genre,tv_year;
    ImageButton albumArt;
    ScrollView scrollView;
    LinearLayout progressView;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.musixmatchOrange));

        }
        //TODO: Add code to check if the track is already favourited and change the background tint of the fab to a different one


        tv_trackName = findViewById(R.id.track_name);
        tv_artistName = findViewById(R.id.artist_name);
        tv_album = findViewById(R.id.album_name);
        tv_genre = findViewById(R.id.genre);
        tv_year = findViewById(R.id.year);
        albumArt = findViewById(R.id.album_art);
        scrollView = findViewById(R.id.trackView);
        progressView = findViewById(R.id.progrssview);
        fab = findViewById(R.id.fab);

        Intent mIntent = getIntent();
        final long track_id = mIntent.getLongExtra("track_id", 0L);
        tv_artistName.setSelected(true);
        tv_trackName.setSelected(true);

        //tv_trackName.setText("Trackid " + track_id);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TrackResponse> getTrack = apiService.getTrackById(API_KEY,track_id);

        getTrack.enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse>call, Response<TrackResponse> response) {
                track = response.body().getMessage().getBody().getTrack();
                Glide.with(TrackViewActivity.this).load(track.getAlbumCoverart100x100())
                        .into(albumArt);
                albumArt.setColorFilter(Color.argb(150, 0, 0, 0));
                tv_trackName.setText(track.getTrackName());
                tv_artistName.setText(track.getArtistName());
                tv_album.setText(track.getAlbumName());
                try {
                    tv_genre.setText(track.getPrimaryGenres().getMusicGenreList().get(0).getMusicGenre().getMusicGenreName());
                } catch (IndexOutOfBoundsException e)
                {
                    tv_genre.setText("No Genre Found");
                }
                try
                {
                    tv_genre.append( " , " + track.getSecondaryGenres().getMusicGenreList().get(0).getMusicGenre().getMusicGenreName());
                } catch (IndexOutOfBoundsException e){

                }
                tv_year.setText(track.getFirstReleaseDate().substring(0,4));
                scrollView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                //recyclerView.setAdapter(new TracksAdapter(trackList, R.layout.track_listitem, getApplicationContext()));

                //Log.d("trackviewsize", "Number of tracks received: " + trackList.size());
            }

            @Override
            public void onFailure(Call<TrackResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("TaskViewActivity error", t.toString());
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
