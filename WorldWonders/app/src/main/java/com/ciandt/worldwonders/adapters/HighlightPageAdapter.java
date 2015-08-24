package com.ciandt.worldwonders.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ciandt.worldwonders.fragments.HighlightFragment;
import com.ciandt.worldwonders.models.Wonder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdias on 21/08/15.
 */
public class HighlightPageAdapter extends FragmentPagerAdapter {

    ArrayList<Wonder> wonders;

    public HighlightPageAdapter(FragmentManager fm, ArrayList<Wonder> wonders) {
        super(fm);
        this.wonders = wonders;
    }

    @Override
    public Fragment getItem(int position) {
        Wonder wonder = wonders.get(position);
        return HighlightFragment.newInstance(wonder);
    }

    @Override
    public int getCount() {
        return wonders.size();
    }
}
