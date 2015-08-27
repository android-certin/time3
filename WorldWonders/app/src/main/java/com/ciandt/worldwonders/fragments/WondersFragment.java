package com.ciandt.worldwonders.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.activities.WonderDetailActivity;
import com.ciandt.worldwonders.adapters.HighlightPageAdapter;
import com.ciandt.worldwonders.adapters.WonderItemAdapter;
import com.ciandt.worldwonders.database.WonderDao;
import com.ciandt.worldwonders.helpers.Helper;
import com.ciandt.worldwonders.models.Wonder;
import com.ciandt.worldwonders.repositories.WondersRepository;
import com.squareup.picasso.Picasso;

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

    ProgressDialogFragment progressDialog;

    Wonder wonder;

    View view;

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
        this.view = view;
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (WondersFragment.isTablet(getContext())) {
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

                        if (WondersFragment.isTablet(getContext())) {
                            showWonder(wonder);
                        } else {
                            Intent intent = new Intent(getActivity(), WonderDetailActivity.class);
                            intent.putExtra("wonder", wonder);
                            startActivity(intent);
                        }
                    }
                });

                recyclerView.setAdapter(itemAdapter);

                if (WondersFragment.isTablet(getContext()) && wonders.size() > 0){
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

    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 720;
    }

    public void showWonder(Wonder wonder) {
        this.wonder = wonder;
        ImageView imageWonder = (ImageView) view.findViewById(R.id.image_detail);
        TextView description = (TextView) view.findViewById(R.id.description_detail);

        String pictureFilename = wonder.photo.split("\\.")[0];
        int pictureResource = Helper.getRawResourceID(getContext(), pictureFilename);

        Picasso.with(getContext())
                .load(pictureResource)
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageWonder);

        description.setText(wonder.description);
    }
}
