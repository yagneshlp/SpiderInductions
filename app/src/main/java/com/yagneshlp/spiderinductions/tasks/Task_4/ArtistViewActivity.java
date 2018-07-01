package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.pojo.pojo_artist.Artist;
import com.yagneshlp.spiderinductions.pojo.pojo_artist.ArtistResponse;
import com.yagneshlp.spiderinductions.tasks.Task_4.ApiClient;
import com.yagneshlp.spiderinductions.tasks.Task_4.ApiInterface;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistViewActivity extends Activity {

    private final static String API_KEY = "ed01bfe66adb321a2b23bed6c4b7d7ba";
    Artist artist;
    TextView artist_name,genre,rating;
    ScrollView scrollView;
    LinearLayout progressView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.musixmatchOrange));

        }

        artist_name = findViewById(R.id.artist_name);
        genre = findViewById(R.id.genres);
        rating = findViewById(R.id.rating);
        scrollView = findViewById(R.id.trackView);
        progressView = findViewById(R.id.progrssview);
        fab = findViewById(R.id.fab);

        Intent mIntent = getIntent();
        final long track_id = mIntent.getLongExtra("artist_id", 0L);

        artist_name.setSelected(true);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArtistResponse> getArtist = apiService.getArtistById(API_KEY,track_id);

        getArtist.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse>call, Response<ArtistResponse> response) {
                artist = response.body().getMessage().getBody().getArtist();

                artist_name.setText(artist.getArtistName());


                try {
                    genre.setText(artist.getPrimaryGenres().getMusicGenreList().get(0).getMusicGenre().getMusicGenreName());
                } catch (IndexOutOfBoundsException e)
                {
                    genre.setText("No Genre Found");
                }
                try {
                    genre.append(" , " + artist.getPrimaryGenres().getMusicGenreList().get(1).getMusicGenre().getMusicGenreName());
                } catch (IndexOutOfBoundsException e)
                {
                    Log.d("ArtistViewActvity" , "Second Primary Genre not found");
                }
                try
                {
                    genre.append( " , " + artist.getSecondaryGenres().getMusicGenreList().get(0).toString());
                } catch (IndexOutOfBoundsException e){

                }
                rating.setText(""+ artist.getArtistRating());

                scrollView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                //recyclerView.setAdapter(new TracksAdapter(trackList, R.layout.track_listitem, getApplicationContext()));

                //Log.d("trackviewsize", "Number of tracks received: " + trackList.size());
            }

            @Override
            public void onFailure(Call<ArtistResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("TaskViewActivity", t.toString());
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

