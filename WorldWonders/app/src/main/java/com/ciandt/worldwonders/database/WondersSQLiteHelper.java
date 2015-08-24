package com.ciandt.worldwonders.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WondersSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wonders.db";
    private static final String DATABASE_DIRECTORY = "/data/data/com.ciandt.worldwonders/databases/";
    private static final String DATABASE_PATH = DATABASE_DIRECTORY + DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;

    private static final String ASSETS_DATABASE_PATH = "database/" + DATABASE_NAME;

    public WondersSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //roda o script de update
    }

    public static void setupDatabase(Context context) {

        File path = new File(DATABASE_DIRECTORY);
        if (!path.exists()) {
            path.mkdirs();
        }

        File db = new File(DATABASE_PATH);
        if (!db.exists()) {

            try {
                InputStream in = context.getAssets().open(ASSETS_DATABASE_PATH);
                FileOutputStream out = new FileOutputStream(db);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.flush();
                in.close();
                out.close();

            } catch (Exception e) {
                Log.e("WondersSQLiteHelper", e.getMessage());
            }
        }

    }

}
