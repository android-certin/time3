package com.ciandt.worldwonders.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ciandt.worldwonders.models.Wonder;

import java.util.ArrayList;
import java.util.List;

public class WonderDao implements Dao<Wonder> {
    static final String TABLENAME = "wonders";

    WondersSQLiteHelper wondersSQLiteHelper;
    SQLiteDatabase db;

    public WonderDao(Context context) {

        wondersSQLiteHelper = new WondersSQLiteHelper(context);
        db = wondersSQLiteHelper.getWritableDatabase();
    }

    @NonNull
    private Wonder getWonder(Cursor cursor) {
        Wonder wonder =  new Wonder();
        wonder.id = cursor.getInt(0);
        wonder.name = cursor.getString(1);
        wonder.description = cursor.getString(2);
        wonder.url = cursor.getString(3);
        wonder.photo = cursor.getString(4);
        wonder.latitude = cursor.getDouble(5);
        wonder.longitude = cursor.getDouble(6);
        return wonder;
    }

    @NonNull
    private ContentValues getContentValues(Wonder data) {
        ContentValues content = new ContentValues();
        content.put("id", data.id);
        content.put("name", data.name);
        content.put("description", data.description);
        content.put("url", data.url);
        content.put("photo", data.photo);
        content.put("latitude", data.latitude);
        content.put("longitude", data.longitude);
        return content;
    }

    @Override
    public List<Wonder> getAll() {
        ArrayList<Wonder> result = new ArrayList<>();

        if (db != null) {

            try {

                Cursor cursor = db.query(TABLENAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    Wonder wonder = getWonder(cursor);
                    result.add(wonder);
                }
            }
            catch (Exception e) {
                e.getMessage();
            }

        }

        return result;
    }

    @Override
    public Wonder getById(int id) {
        Wonder wonder = null;

        if (db != null) {
            Cursor cursor = db.query(TABLENAME, null, "id = ?" , new String[] { String.valueOf(id) }, null, null, null);

            if (cursor.moveToNext()) {
                wonder = getWonder(cursor);
            }
        }

        return wonder;
    }

    @Override
    public List<Wonder> search(String word) {

        ArrayList<Wonder> result = new ArrayList<>();

        if (db != null) {
            Cursor cursor = db.query(TABLENAME, null, "name LIKE '?'", new String[]{word}, null, null, null);
            while (cursor.moveToNext()) {
                Wonder wonder = getWonder(cursor);
                result.add(wonder);
            }
        }

        return result;
    }

    @Override
    public boolean update(Wonder data) {

        if (db != null) {
            ContentValues content = getContentValues(data);
            int rows = db.update(TABLENAME, content, "id = ?" , new String[] { String.valueOf(data.id) });
            return rows > 0;
        }

        return false;
    }



    @Override
    public boolean delete(Wonder data) {

        if (db != null) {
            int rows = db.delete(TABLENAME, "id = ?", new String[]{String.valueOf(data.id)});
            return rows > 0;
        }

        return false;
    }

    @Override
    public boolean insert(Wonder data) {

        if (db != null) {
            ContentValues content = getContentValues(data);
            long id = db.insert(TABLENAME, null, content);
            return id > -1;
        }

        return false;
    }

    @Override
    public void close() {
        if (db != null) {
            db.close();
        }
        if (wondersSQLiteHelper != null) {
            wondersSQLiteHelper.close();
        }
    }
}
