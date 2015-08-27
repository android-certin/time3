package com.ciandt.worldwonders.helpers;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import java.util.ArrayList;

public class AnimationHelper {

    private ArrayList<Step> steps;
    private ArrayList<AnimationDrawable> animationDrawables;

    private int highlightDuration;
    private int numItems;

    public AnimationHelper(int highlightDuration) {
        this.highlightDuration = highlightDuration;
        steps = new ArrayList<>();
        animationDrawables = new ArrayList<>();
    }

    public AnimationHelper addStep(Step step) {
        steps.add(step);
        return this;
    }

    public AnimationHelper build() {

        int totalDuration = steps.size() * (2 * highlightDuration);

        for (int i = 0; i < steps.size(); i++) {

            Step step = steps.get(i);

            AnimationDrawable animationDrawable = new AnimationDrawable();

            int sleep = i * (2 * highlightDuration);

            animationDrawable.addFrame(step.normal, sleep);
            animationDrawable.addFrame(step.highlighted, highlightDuration);
            animationDrawable.addFrame(step.normal, totalDuration - sleep - highlightDuration);
            animationDrawable.setOneShot(false);

            step.imageView.setBackgroundDrawable(animationDrawable);
            animationDrawables.add(animationDrawable);

        }

        return this;
    }

    public void start() {

        for (AnimationDrawable animationDrawable: animationDrawables) {
            animationDrawable.start();
        }

    }


    public static class Step {

        ImageView imageView;
        Drawable normal;
        Drawable highlighted;

        public Step(ImageView imageView, Drawable normal, Drawable highlighted) {
            this.imageView = imageView;
            this.normal = normal;
            this.highlighted = highlighted;
        }
    }


}
