package com.ciandt.worldwonders.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.ciandt.worldwonders.database.BookmarksDao;
import com.ciandt.worldwonders.models.Bookmark;
import com.ciandt.worldwonders.models.Wonder;

import java.util.Locale;

public class WonderDetailHelper {

    private Context context;
    private Wonder wonder;

    public WonderDetailHelper(Context context, Wonder wonder) {
        this.context = context;
        this.wonder = wonder;
    }

    public boolean startDirections() {

        Uri uri = Uri.parse(String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(%s)", wonder.latitude, wonder.longitude, wonder.latitude, wonder.longitude, Uri.encode(wonder.name)));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
            return true;
        }

        return false;
    }

    public boolean isBookmarked() {

        BookmarksDao bookmarksDao = new BookmarksDao(context);
        Bookmark bookmark = bookmarksDao.getByWonder(wonder.id);
        return (bookmark != null);

    }

    public boolean toggleBookmark() {

        BookmarksDao bookmarksDao = new BookmarksDao(context);
        Bookmark bookmark = bookmarksDao.getByWonder(wonder.id);

        if (bookmark != null) {
            bookmarksDao.delete(bookmark);
        } else {
            bookmark = new Bookmark(wonder.id);
            bookmarksDao.insert(bookmark);
        }

        return true;
    }

    public boolean hasLocation() {
        return (wonder.latitude != 0.0 && wonder.longitude != 0.0);
    }

}
