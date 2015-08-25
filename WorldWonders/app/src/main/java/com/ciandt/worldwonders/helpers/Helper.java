package com.ciandt.worldwonders.helpers;

import android.content.Context;

public class Helper {

    public static int getRawResourceID(Context context, String rawResourceName) {
        return context.getResources().getIdentifier(rawResourceName, "raw", context.getPackageName());
    }

    public static int getDrawableResourceID(Context context, String drawableResourceName) {
        return context.getResources().getIdentifier(drawableResourceName, "drawable", context.getPackageName());
    }
}
