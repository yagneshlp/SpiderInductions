package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteArtist;
import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteTrack;
import com.yagneshlp.spiderinductions.tasks.Task_4.ApiClient;
import com.yagneshlp.spiderinductions.tasks.Task_4.ApiInterface;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistViewActivity extends Activity {

    Artist artist;
    TextView artist_name,genre,rating;
    ScrollView scrollView;
    LinearLayout progressView,noInternetView;
    FloatingActionButton fab;
    FavouritesDBHelper dbHelper;
    boolean isFavourite;
    long track_id;

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
        noInternetView = findViewById(R.id.no_internetView);
        fab = findViewById(R.id.fab);
        dbHelper = new FavouritesDBHelper(this);

        Intent mIntent = getIntent();
        track_id = mIntent.getLongExtra("artist_id", 0L);

        isFavourite = dbHelper.searchArtist(track_id);
        if(isFavourite) {
            fab.setImageResource(R.drawable.ic_favourited);
            ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                loadArtistDetails();
            } else {
                noInternetView.setVisibility(View.GONE);
                FavouriteArtist offlineArtist = dbHelper.getArtistByID(track_id);
                artist_name.setText(offlineArtist.getArtist_name());
                genre.setText(offlineArtist.getGenre());
                rating.setText(""+offlineArtist.getRating());
                scrollView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
            }
        }else
            loadArtistDetails();

        artist_name.setSelected(true);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FavouriteArtist favArtist = new FavouriteArtist();
                favArtist.setArtist_id(track_id);
                favArtist.setArtist_name(artist_name.getText().toString());
                favArtist.setGenre(genre.getText().toString());
                favArtist.setRating(Integer.parseInt(rating.getText().toString()));
                if(!isFavourite){
                    dbHelper.addArtist(favArtist);
                    Snackbar.make(view, "Added to Favourites!", Snackbar.LENGTH_LONG)
                            .show();
                    isFavourite = true;
                    fab.setImageResource(R.drawable.ic_favourited);
                }else {
                    dbHelper.deleteArtist(favArtist);
                    isFavourite=false;
                    fab.setImageResource(R.drawable.ic_favourite);
                    Snackbar.make(view, "Removed from Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dbHelper.addArtist(favArtist);
                                    isFavourite = true;
                                    fab.setImageResource(R.drawable.ic_favourited);
                                }
                            }).show();


                }
            }
        });
    }
    private void loadArtistDetails(){

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork !=null ) {
            noInternetView.setVisibility(View.GONE);
            progressView.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ArtistResponse> getArtist = apiService.getArtistById(new Constants().getApiKey(), track_id);

            getArtist.enqueue(new Callback<ArtistResponse>() {
                @Override
                public void onResponse(Call<ArtistResponse> call, Response<ArtistResponse> response) {
                    artist = response.body().getMessage().getBody().getArtist();

                    artist_name.setText(artist.getArtistName());
                    try {
                        genre.setText(artist.getPrimaryGenres().getMusicGenreList().get(0).getMusicGenre().getMusicGenreName());
                    } catch (IndexOutOfBoundsException e) {
                        genre.setText("No Genre Found");
                    }
                    try {
                        genre.append(" , " + artist.getPrimaryGenres().getMusicGenreList().get(1).getMusicGenre().getMusicGenreName());
                    } catch (IndexOutOfBoundsException e) {
                        Log.d("ArtistViewActvity", "Second Primary Genre not found");
                    }
                    try {
                        genre.append(" , " + artist.getSecondaryGenres().getMusicGenreList().get(0).toString());
                    } catch (IndexOutOfBoundsException e) {

                    }
                    rating.setText("" + artist.getArtistRating());

                    scrollView.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    progressView.setVisibility(View.GONE);
                    //recyclerView.setAdapter(new TracksAdapter(trackList, R.layout.track_listitem, getApplicationContext()));

                    //Log.d("trackviewsize", "Number of tracks received: " + trackList.size());
                }

                @Override
                public void onFailure(Call<ArtistResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("TaskViewActivity", t.toString());
                }
            });
        }else
            noInternetView.setVisibility(View.VISIBLE);
    }
    }

