package com.yagneshlp.spiderinductions.tasks.Task_4;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.github.fabtransitionactivity.SheetLayout;
import com.yagneshlp.spiderinductions.MainActivity;
import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.tasks.Task_4.Adapters.TabsPagerAdapter;

public class Task4Activity extends AppCompatActivity implements  SheetLayout.OnFabAnimationEndListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1;
    SheetLayout mSheetLayout;
    ViewPager viewPager;
    LinearLayout noInternet;
    FloatingActionButton fabSearch,fabFav;
    private TabsPagerAdapter mAdapter;

    @Override
    public void onResume(){
        super.onResume();
        fabFav.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null){
            viewPager.setVisibility(View.VISIBLE);
            viewPager.setAdapter(mAdapter);
            fabSearch.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
        }
    }

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
        FloatingActionButton fab =  findViewById(R.id.fab);
        noInternet = findViewById(R.id.no_internetView);
        viewPager =  findViewById(R.id.pager);
        fabSearch =findViewById(R.id.fab);
        fabFav = findViewById(R.id.fabFav);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null){
            viewPager.setVisibility(View.VISIBLE);
            viewPager.setAdapter(mAdapter);
            fabSearch.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
        }
        mSheetLayout.setFab(fab);
        mSheetLayout.setFabAnimationEndListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Goodplays");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabFav.setVisibility(View.GONE);
                mSheetLayout.expandFab();
            }
        });


        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FavouritesActivity.class);
                startActivity(intent);
            }
        });

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

