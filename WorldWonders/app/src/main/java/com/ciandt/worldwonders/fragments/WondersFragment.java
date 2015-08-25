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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.adapters.HighlightPageAdapter;
import com.ciandt.worldwonders.adapters.WonderItemAdapter;
import com.ciandt.worldwonders.database.WonderDao;
import com.ciandt.worldwonders.models.Wonder;
import com.ciandt.worldwonders.repositories.WondersRepository;

import java.util.ArrayList;
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
    int dialogCount = 0;

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

        recyclerView = (RecyclerView)view.findViewById(R.id.wonder_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        WonderItemAdapter adapter = new WonderItemAdapter(new ArrayList<Wonder>());
        recyclerView.setAdapter(adapter);


        fragmentManager = getFragmentManager();
        wondersRepository = new WondersRepository(getContext());

        showDialog(2);

        wondersRepository.getRandom(3, new WondersRepository.WonderRandomListener() {
            @Override
            public void onWonderRandom(Exception e, List<Wonder> wonders) {
                HighlightPageAdapter adapter = new HighlightPageAdapter(fragmentManager, (ArrayList<Wonder>) wonders);
                viewPager.setAdapter(adapter);
                dismissDialog();
            }
        });

        wondersRepository.getAll(new WondersRepository.WonderAllListener() {
            @Override
            public void onWonderAll(Exception e, List<Wonder> wonders) {
                WonderItemAdapter adapter = new WonderItemAdapter(wonders);
                recyclerView.setAdapter(adapter);
                dismissDialog();
            }
        });
    }

    private void showDialog(int count) {
        dialogCount = count;
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getContext(), "Título", "Mensagem", false, true, new ProgressDialog.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    wondersRepository.cancel();
                }
            });
        }
    }

    private void dismissDialog() {
        dialogCount--;
        if (dialogCount == 0) {
            progressDialog.dismiss();
        }
    }

}
