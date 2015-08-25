package com.ciandt.worldwonders.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.adapters.HighlightPageAdapter;
import com.ciandt.worldwonders.adapters.WonderItemAdapter;
import com.ciandt.worldwonders.database.WonderDao;
import com.ciandt.worldwonders.models.Wonder;
import com.ciandt.worldwonders.repositories.WondersRepository;

import java.util.ArrayList;
import java.util.Collection;
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

    ProgressDialog progressDialog;

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

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        recyclerView = (RecyclerView) view.findViewById(R.id.wonder_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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

                HighlightPageAdapter highlightAdapter = new HighlightPageAdapter(fragmentManager, highlights);
                viewPager.setAdapter(highlightAdapter);

                WonderItemAdapter itemAdapter = new WonderItemAdapter(wonders, new WonderItemAdapter.WonderOnClickListener() {
                    @Override
                    public void onClick(Wonder wonder) {
                        Toast.makeText(getContext(), wonder.name.toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(itemAdapter);
                dismissDialog();
            }
        });
    }

    private void showDialog() {
        progressDialog = ProgressDialog.show(getContext(), "Título", "Mensagem", false, true, new ProgressDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                wondersRepository.cancel();
            }
        });
    }

    private void dismissDialog() {
        progressDialog.dismiss();
    }

}
