package com.ciandt.worldwonders.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.adapters.HighlightPageAdapter;
import com.ciandt.worldwonders.database.WonderDao;
import com.ciandt.worldwonders.models.Wonder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdias on 21/08/15.
 */
public class WondersFragment extends Fragment {
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wonders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager);

        WonderDao wonderDao = new WonderDao(getContext());
        ArrayList<Wonder> wonders = (ArrayList<Wonder>)wonderDao.getAll();

        FragmentManager fragmentManager = getFragmentManager();
        HighlightPageAdapter adapter = new HighlightPageAdapter(fragmentManager, wonders);
        viewPager.setAdapter(adapter);

    }

}
