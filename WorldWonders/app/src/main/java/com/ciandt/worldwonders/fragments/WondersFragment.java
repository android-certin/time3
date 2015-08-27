package com.ciandt.worldwonders.fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.WorldWondersApp;
import com.ciandt.worldwonders.activities.WonderDetailActivity;
import com.ciandt.worldwonders.adapters.HighlightPageAdapter;
import com.ciandt.worldwonders.adapters.WonderItemAdapter;
import com.ciandt.worldwonders.helpers.WonderDetailHelper;
import com.ciandt.worldwonders.models.Wonder;
import com.ciandt.worldwonders.repositories.WondersRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bdias on 21/08/15.
 */
public class WondersFragment extends Fragment {

    ViewPager viewPager;
    RecyclerView recyclerView;

    FragmentManager fragmentManager;
    WondersRepository wondersRepository;

    ProgressDialogFragment progressDialog;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        this.view = view;
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (WorldWondersApp.isTablet(getContext())) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        recyclerView = (RecyclerView) view.findViewById(R.id.wonder_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        WonderItemAdapter adapter = new WonderItemAdapter(new ArrayList<Wonder>(), null);
        recyclerView.setAdapter(adapter);


        fragmentManager = getFragmentManager();
        wondersRepository = new WondersRepository(getContext());

        showDialog();

        wondersRepository.getAll(new WondersRepository.ListResultListener<Wonder>() {
            @Override
            public void onResult(Exception e, List<Wonder> wonders) {

                ArrayList<Wonder> random = new ArrayList<Wonder>(wonders);
                Collections.shuffle(random);
                ArrayList<Wonder> highlights = new ArrayList<Wonder>(random.subList(0, Math.min(3, random.size())));
                random = null;

                if (viewPager != null) {
                    HighlightPageAdapter highlightAdapter = new HighlightPageAdapter(fragmentManager, highlights);
                    viewPager.setAdapter(highlightAdapter);
                }

                WonderItemAdapter itemAdapter = new WonderItemAdapter(wonders, new WonderItemAdapter.WonderOnClickListener() {
                    @Override
                    public void onClick(Wonder wonder) {

                        if (WorldWondersApp.isTablet(getContext())) {
                            showWonder(wonder);
                        } else {
                            Intent intent = new Intent(getActivity(), WonderDetailActivity.class);
                            intent.putExtra("wonder", wonder);
                            startActivity(intent);
                        }
                    }
                });

                recyclerView.setAdapter(itemAdapter);

                if (WorldWondersApp.isTablet(getContext()) && wonders.size() > 0){
                    showWonder(wonders.get(0));
                }

                dismissDialog();
            }
        });
    }

    private void showDialog() {
        progressDialog = ProgressDialogFragment.show(getFragmentManager());
    }

    private void dismissDialog() {
        progressDialog.dismiss();
    }

    public void showWonder(Wonder wonder) {
        FragmentManager manager = getFragmentManager();
        WonderDetailFragment fragment = WonderDetailFragment.newInstance(wonder);
        manager.beginTransaction().replace(R.id.container_detail, fragment).commit();
    }

}
