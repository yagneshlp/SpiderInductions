package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteTrack;
import com.yagneshlp.spiderinductions.pojo.pojo_track.TrackResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_track.Track;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.ApiClient;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.ApiInterface;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.Constants;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.FavouritesDBHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackViewActivity extends AppCompatActivity {

    Track track;
    TextView tv_trackName,tv_artistName,tv_album,tv_genre,tv_year;
    ImageButton albumArt;
    ScrollView scrollView;
    LinearLayout progressView,noInternetView;
    FloatingActionButton fab;
    FavouritesDBHelper dbHelper;
    boolean isFavourite;
    long track_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_view);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.musixmatchOrange));

        }

        tv_trackName = findViewById(R.id.track_name);
        tv_artistName = findViewById(R.id.artist_name);
        tv_album = findViewById(R.id.album_name);
        tv_genre = findViewById(R.id.genre);
        tv_year = findViewById(R.id.year);
        albumArt = findViewById(R.id.album_art);
        scrollView = findViewById(R.id.trackView);
        progressView = findViewById(R.id.progrssview);
        noInternetView = findViewById(R.id.no_internetView);
        fab = findViewById(R.id.fab);
        dbHelper = new FavouritesDBHelper(this);

        Intent mIntent = getIntent();
        track_id = mIntent.getLongExtra("track_id", 0L);

        tv_artistName.setSelected(true);
        tv_trackName.setSelected(true);

        isFavourite = dbHelper.searchTrack(track_id);
        if(isFavourite) {
            fab.setImageResource(R.drawable.ic_favourited);
            ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                loadTrackDetails();
            } else {
                noInternetView.setVisibility(View.GONE);
                FavouriteTrack offlineTrack = dbHelper.getTrackByID(track_id);
                albumArt.setImageResource(R.drawable.ic_track);
                albumArt.setColorFilter(Color.argb(150, 0, 0, 0));
                tv_trackName.setText(offlineTrack.getTrack_name());
                tv_artistName.setText(offlineTrack.getArtist_name());
                tv_album.setText(offlineTrack.getAlbum_name());
                tv_genre.setText(offlineTrack.getGenre());
                tv_year.setText("" + offlineTrack.getYear());
                scrollView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
            }
        }else
            loadTrackDetails();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FavouriteTrack favTrack = new FavouriteTrack();
                favTrack.setTrack_id(track_id);
                favTrack.setTrack_name(tv_trackName.getText().toString());
                favTrack.setArtist_name(tv_artistName.getText().toString());
                favTrack.setGenre(tv_genre.getText().toString());
                favTrack.setAlbum_name(tv_album.getText().toString());
                favTrack.setYear(Integer.parseInt(tv_year.getText().toString()));
                if(!isFavourite){
                    dbHelper.addTrack(favTrack);
                    Snackbar.make(view, "Added to Favourites!", Snackbar.LENGTH_LONG)
                            .show();
                    isFavourite = true;
                    fab.setImageResource(R.drawable.ic_favourited);
                }else {
                    dbHelper.deleteTrack(favTrack);
                    isFavourite=false;
                    fab.setImageResource(R.drawable.ic_favourite);
                    Snackbar.make(view, "Removed from Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dbHelper.addTrack(favTrack);
                                    isFavourite = true;
                                    fab.setImageResource(R.drawable.ic_favourited);
                                }
                            }).show();


                }
            }
        });
    }

    private void loadTrackDetails(){

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork !=null ){
            noInternetView.setVisibility(View.GONE);
            progressView.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<TrackResponse> getTrack = apiService.getTrackById(new Constants().getApiKey(),track_id);

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
                }

                @Override
                public void onFailure(Call<TrackResponse>call, Throwable t) {
                    // Log error here since request failed
                    Log.e("TaskViewActivity error", t.toString());
                }
            });
        }else
        {
            noInternetView.setVisibility(View.VISIBLE);
        }

    }

}
