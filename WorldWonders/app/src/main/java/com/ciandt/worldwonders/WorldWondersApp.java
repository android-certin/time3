package com.ciandt.worldwonders;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.ciandt.worldwonders.database.WondersSQLiteHelper;
import com.facebook.stetho.Stetho;

public class WorldWondersApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WondersSQLiteHelper.setupDatabase(getApplicationContext());

        Stetho.initialize(Stetho
                .newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }

}
