package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.polyak.iconswitch.IconSwitch;
import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.helper.RecyclerTouchListener;

import com.yagneshlp.spiderinductions.pojo.pojo_search_artistlist.ArtistList;
import com.yagneshlp.spiderinductions.pojo.pojo_search_artistlist.ArtistListSearchResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist.Track;
import com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist.TrackList;
import com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist.TrackListSearchResponse;
import com.yagneshlp.spiderinductions.tasks.Task_4.Adapters.SearchViewArtistsAdapter;
import com.yagneshlp.spiderinductions.tasks.Task_4.Adapters.SearchViewTracksAdapter;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.ApiClient;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.ApiInterface;
import com.yagneshlp.spiderinductions.tasks.Task_4.Helper.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText search_keyword;
    IconSwitch iconswitch;
    Button search;
    int search_counter = 0;
    RecyclerView recyclerView;
    List<TrackList> trackList;
    List<ArtistList> artistList;
    boolean isTracks = true;
    TextView keyword,mode;
    LinearLayout banner,progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.musixmatchOrange));

        }

        getSupportActionBar().setTitle("Search");

        search_keyword = findViewById(R.id.search_keywords);
        keyword = findViewById(R.id.keyword);
        mode = findViewById(R.id.mode);
        iconswitch = findViewById(R.id.icon_switch);
        banner = findViewById(R.id.SeearchBanner);
        progress = findViewById(R.id.progressLayout);
        search = findViewById(R.id.search);

        recyclerView = (RecyclerView) findViewById(R.id.search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(isTracks) {
                    Track track = trackList.get(position).getTrack();
                    Intent i = new Intent(getApplicationContext(), TrackViewActivity.class);
                    long trackId = track.getTrackId();
                    i.putExtra("track_id", trackId);
                    startActivity(i);
                }else{
                    com.yagneshlp.spiderinductions.pojo.pojo_search_artistlist.Artist artist = artistList.get(position).getArtist();
                    Intent i = new Intent(getApplicationContext(), ArtistViewActivity.class);
                    long artistId = artist.getArtistId();
                    i.putExtra("artist_id", artistId);
                    startActivity(i);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        search_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (search_keyword.getText().toString().length() != 0) {
                        search_counter++;
                        Search();
                    }else {
                        Toast.makeText(getApplicationContext(),"Please enter a Keyword to search",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });


        iconswitch.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                if(current == IconSwitch.Checked.LEFT) {
                    isTracks = true;
                    mode.setText("in Tracks");
                }else {
                    isTracks = false;
                    mode.setText("in Artists");
                }

                Log.d("SearchActivity","Checkedchangelisteer called");
                if(search_counter>0 && search_keyword.getText().toString().length() !=0 )
                    Search();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_keyword.getText().toString().length() != 0) {
                    search_counter++;
                    Search();
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter a Keyword to search",Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    private void Search(){

        keyword.setText("\"" + search_keyword.getText().toString() + "\"");
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        banner.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        if(isTracks) {
            Call<com.yagneshlp.spiderinductions.pojo.pojo_search_tracklist.TrackListSearchResponse> SearchTracks = apiService.getTrackSearchResult(new Constants().getApiKey(), 1, 15, search_keyword.getText().toString(), "desc", "json");
            SearchTracks.enqueue(new Callback<TrackListSearchResponse>() {
                @Override
                public void onResponse(Call<TrackListSearchResponse> call, Response<TrackListSearchResponse> response) {
                    trackList = response.body().getMessage().getBody().getTrackList();
                    recyclerView.setAdapter(new SearchViewTracksAdapter(trackList, R.layout.search_listitem, SearchActivity.this));
                    //progressLayout.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                   banner.setVisibility(View.VISIBLE);
                    //Log.d("Search Activity", "Number of tracks received: " + trackList.size());
                }

                @Override
                public void onFailure(Call<TrackListSearchResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("SearchActivity", t.toString());
                }
            });
        }else{
            Call<ArtistListSearchResponse> SearchArtist = apiService.getArtistSearchResult(new Constants().getApiKey(), 1, 15, search_keyword.getText().toString(), "json");
            SearchArtist.enqueue(new Callback<ArtistListSearchResponse>() {
                @Override
                public void onResponse(Call<ArtistListSearchResponse> call, Response<ArtistListSearchResponse> response) {
                    artistList = response.body().getMessage().getBody().getArtistList();
                    recyclerView.setAdapter(new SearchViewArtistsAdapter(artistList, R.layout.search_listitem, SearchActivity.this));
                    //progressLayout.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    banner.setVisibility(View.VISIBLE);
                    //Log.d("Search Activity", "Number of tracks received: " + trackList.size());
                }

                @Override
                public void onFailure(Call<ArtistListSearchResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("SearchActivity", t.toString());
                }
            });

        }

    }
}
