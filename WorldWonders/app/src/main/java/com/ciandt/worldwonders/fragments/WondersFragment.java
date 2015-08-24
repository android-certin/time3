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

        ArrayList<Wonder> wonders = new ArrayList<Wonder>();

        Wonder w1 = new Wonder();
        w1.name = "Pirâmide de Queóps";
        w1.url = "http://1.bp.blogspot.com/-OwFpNnwopp4/Tw1xYXVASmI/AAAAAAAANlU/XbwNPVSeC8Q/s1600/piramide-queops-egito.JPG";

        Wonder w2 = new Wonder();
        w2.name = "Jardins suspensos da Babilônia";
        w2.url = "http://renatatilli.com.br/blog/wp-content/uploads/jardins-suspensos-da-Babilonia-7.jpg";

        Wonder w3 = new Wonder();
        w3.name = "Colosso de Rodes";
        w3.url = "http://1.bp.blogspot.com/-VZelt-4Bumw/UaOTjPaPlCI/AAAAAAAABbY/5KFsm0PD6uk/s640/colossus_of_rhodes_by_pervandr-d4qcdti.jpg";

        wonders.add(w1);
        wonders.add(w2);
        wonders.add(w3);

        FragmentManager fragmentManager = getFragmentManager();
        HighlightPageAdapter adapter = new HighlightPageAdapter(fragmentManager, wonders);
        viewPager.setAdapter(adapter);

    }

}
