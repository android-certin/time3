package com.ciandt.worldwonders.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.adapters.HighlightPageAdapter;
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
    FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    WondersRepository wondersRepository;

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
        fragmentManager = getFragmentManager();
        showDialog();

        wondersRepository = new WondersRepository(getContext());
        wondersRepository.getRandom(3, new WondersRepository.WonderRandomListener() {
            @Override
            public void onWonderRandom(Exception e, List<Wonder> wonders) {
                HighlightPageAdapter adapter = new HighlightPageAdapter(fragmentManager, (ArrayList<Wonder>)wonders);
                viewPager.setAdapter(adapter);
                progressDialog.dismiss();
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

}
