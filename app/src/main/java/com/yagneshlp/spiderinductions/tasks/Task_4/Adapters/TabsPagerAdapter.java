package com.yagneshlp.spiderinductions.tasks.Task_4.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yagneshlp.spiderinductions.tasks.Task_4.TopArtistsFragment;
import com.yagneshlp.spiderinductions.tasks.Task_4.TopTracksFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "tracks", "artists" };

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new TopTracksFragment();
            case 1:
                return new TopArtistsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}