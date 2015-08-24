package com.ciandt.worldwonders;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.ciandt.worldwonders.database.WondersSQLiteHelper;

public class WorldWondersApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WondersSQLiteHelper.setupDatabase(getApplicationContext());
    }

}
