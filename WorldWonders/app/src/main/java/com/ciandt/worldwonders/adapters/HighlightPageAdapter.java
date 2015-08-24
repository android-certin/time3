package com.ciandt.worldwonders.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ciandt.worldwonders.fragments.HighligthFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdias on 21/08/15.
 */
public class HighlightPageAdapter extends FragmentPagerAdapter {

    List<HighligthFragment> fragments;

    public HighlightPageAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new HighligthFragment());
        fragments.add(new HighligthFragment());
        fragments.add(new HighligthFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
