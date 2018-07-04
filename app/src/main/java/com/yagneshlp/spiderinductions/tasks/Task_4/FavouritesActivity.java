package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.helper.RecyclerTouchListener;
import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteArtist;
import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteTrack;
import com.yagneshlp.spiderinductions.tasks.Task_4.Adapters.FavouriteArtistsAdapter;
import com.yagneshlp.spiderinductions.tasks.Task_4.Adapters.FavouriteTracksAdapter;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.FavouritesDBHelper;

import java.util.Arrays;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    TextView mTextMessage,emptyMessage;
    boolean isTrackMode = true;
    List<FavouriteTrack> trackList;
    FavouriteTrack tracks[];
    List<FavouriteArtist> artistList;
    FavouriteArtist artists[];
    FavouritesDBHelper dbHelper;
    RecyclerView recyclerView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("Favourite Tracks");
                    loadTracks();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("Favourite Artists");
                    loadArtists();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        if(isTrackMode)
            loadTracks();
        else
            loadArtists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        recyclerView = findViewById(R.id.favourites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.musixmatchOrange));

        }
        getSupportActionBar().setTitle("Favourites");

        mTextMessage = (TextView) findViewById(R.id.message);
        dbHelper = new FavouritesDBHelper(this);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        emptyMessage = findViewById(R.id.emptyMessage);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(isTrackMode) {
                    Intent i = new Intent(getApplicationContext(), TrackViewActivity.class);
                    i.putExtra("track_id", trackList.get(position).getTrack_id());
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), ArtistViewActivity.class);
                    i.putExtra("artist_id", artistList.get(position).getArtist_id());
                    startActivity(i);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        loadTracks();

    }

    public void loadTracks(){
        isTrackMode = true;
        tracks = new FavouriteTrack[1000];
        tracks = dbHelper.getAllTracks();
        if(dbHelper.getNoOfTracks() == 0){
            emptyMessage.setText("Oops! No Favourite Tracks Found :(  \n You can add a favourite track by clicking the Star button on the tracks's info page!");
            emptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            trackList = Arrays.asList(tracks);
            trackList = trackList.subList(0, dbHelper.getNoOfTracks());
            emptyMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new FavouriteTracksAdapter(trackList, R.layout.track_listitem, this));
        }
    }

    public void loadArtists(){
        isTrackMode=false;
        artists = new FavouriteArtist[1000];
        artists = dbHelper.getAllArtists();
        if(dbHelper.getNoOfArtists() == 0){
            emptyMessage.setText("Oops! No Favourite Artists Found :(  \n You can add a favourite artist by clicking the Star button on the artist's info page!");
            emptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            artistList = Arrays.asList(artists);
            artistList = artistList.subList(0, dbHelper.getNoOfArtists());
            emptyMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new FavouriteArtistsAdapter(artistList, R.layout.artist_listitem, this));
        }

    }

}
