package com.ciandt.worldwonders.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.helpers.AnimationHelper;

public class ProgressDialogFragment extends DialogFragment {

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    public static ProgressDialogFragment show(FragmentManager fragmentManager) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.show(fragmentManager, "progressdialog");
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_progressdialog, null);
        setupView(view);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view).setCancelable(false).create();
        return alertDialog;
    }

    public void setupView(View view) {

        ImageView imageView1 = (ImageView)view.findViewById(R.id.animation1);
        ImageView imageView2 = (ImageView)view.findViewById(R.id.animation2);
        ImageView imageView3 = (ImageView)view.findViewById(R.id.animation3);
        ImageView imageView4 = (ImageView)view.findViewById(R.id.animation4);
        ImageView imageView5 = (ImageView)view.findViewById(R.id.animation5);


        AnimationHelper animationHelper = new AnimationHelper(500);
        animationHelper
                .addStep(new AnimationHelper.Step(imageView1, getResources().getDrawable(R.drawable.camera_normal), getResources().getDrawable(R.drawable.camera_selected)))
                .addStep(new AnimationHelper.Step(imageView2, getResources().getDrawable(R.drawable.eiffel_tower_normal), getResources().getDrawable(R.drawable.eiffel_tower_selected)))
                .addStep(new AnimationHelper.Step(imageView3, getResources().getDrawable(R.drawable.suitcase_normal), getResources().getDrawable(R.drawable.suitcase_selected)))
                .addStep(new AnimationHelper.Step(imageView4, getResources().getDrawable(R.drawable.pyramids_normal), getResources().getDrawable(R.drawable.pyramids_selected)))
                .addStep(new AnimationHelper.Step(imageView5, getResources().getDrawable(R.drawable.taj_mahal_normal), getResources().getDrawable(R.drawable.taj_mahal_selected)))
                .build()
                .start();

    }
}
