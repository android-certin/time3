package com.ciandt.worldwonders.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ciandt.worldwonders.models.Bookmark;
import com.ciandt.worldwonders.models.Wonder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by daianefs on 26/08/15.
 */
public class BookmarksDao implements Dao<Bookmark> {
    static final String TABLENAME = "bookmarks";

    WondersSQLiteHelper wondersSQLiteHelper;
    SQLiteDatabase db;

    public BookmarksDao(Context context) {

        wondersSQLiteHelper = new WondersSQLiteHelper(context);
        db = wondersSQLiteHelper.getWritableDatabase();
    }

    @NonNull
    private Bookmark getBookmak(Cursor cursor) {
        Bookmark bookmark = new Bookmark();
        bookmark.id = cursor.getInt(0);
        bookmark.idWonders = cursor.getInt(1);

        return bookmark;
    }

    @NonNull
    private ContentValues getContentValues(Bookmark data) {
        ContentValues content = new ContentValues();
        content.put("id", data.id);
        content.put("idWonders", data.idWonders);
        return content;
    }

    @Override
    public List<Bookmark> getAll() {
        ArrayList<Bookmark> result = new ArrayList<>();

        if (db != null) {

            Cursor cursor = db.query(TABLENAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Bookmark bookmark = getBookmak(cursor);
                result.add(bookmark);
            }

        }

        return result;
    }

    @Override
    public Bookmark getById(int id) {
        Bookmark bookmark = null;

        if (db != null) {
            Cursor cursor = db.query(TABLENAME, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToNext()) {
                bookmark = getBookmak(cursor);
            }
        }

        return bookmark;
    }

    @Override
    public List<Bookmark> search(String word) {
        return getAll();
    }

    @Override
    public boolean update(Bookmark data) {

        if (db != null) {
            ContentValues content = getContentValues(data);
            int rows = db.update(TABLENAME, content, "id = ?", new String[]{String.valueOf(data.id)});
            return rows > 0;
        }

        return false;
    }


    @Override
    public boolean delete(Bookmark data) {

        if (db != null) {
            int rows = db.delete(TABLENAME, "id = ?", new String[]{String.valueOf(data.id)});
            return rows > 0;
        }

        return false;
    }

    @Override
    public boolean insert(Bookmark data) {

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

    public List<Bookmark> getRandom(int count) {

        if (count < 1) {
            return new ArrayList<Bookmark>();
        }

        ArrayList<Bookmark> result =  (ArrayList<Bookmark>)getAll();
        Collections.shuffle(result);

        return new ArrayList<Bookmark>(result.subList(0, Math.min(count, result.size())));
    }
}
