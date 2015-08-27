package com.ciandt.worldwonders.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.database.BookmarksDao;
import com.ciandt.worldwonders.helpers.Helper;
import com.ciandt.worldwonders.helpers.WonderDetailHelper;
import com.ciandt.worldwonders.models.Bookmark;
import com.ciandt.worldwonders.models.Wonder;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class WonderDetailActivity extends AppCompatActivity {

    Context context;
    Wonder wonder;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    Menu menu;
    ImageView image;
    TextView description;
    TextView link;

    MenuItem bookmarkMenuItem;

    WonderDetailHelper wonderDetailHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wonderdetail);
        context = this;

        Intent intent = (Intent) getIntent();
        wonder = (Wonder) intent.getSerializableExtra("wonder");
        wonderDetailHelper = new WonderDetailHelper(this, wonder);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        image = (ImageView) findViewById(R.id.image);
        description = (TextView) findViewById(R.id.description);
        link = (TextView) findViewById(R.id.link);

        collapsingToolbarLayout.setTitle(wonder.name.toUpperCase());
        description.setText(wonder.description);

        String pictureFilename = wonder.photo.split("\\.")[0];
        int pictureResource = Helper.getRawResourceID(context, pictureFilename);

        Picasso.with(context)
                .load(pictureResource)
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(image);


        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(WonderDetailActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                createDialogWebview(wonder.name, wonder.url);
            }
        };

        SpannableString styledString = new SpannableString(getString(R.string.description_source));
        styledString.setSpan(new ForegroundColorSpan(Color.BLUE), 7, 16, 0);
        styledString.setSpan(new UnderlineSpan(), 7, 16, 0);
        styledString.setSpan(clickableSpan, 7, 16, 0);

        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(styledString);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_wonderdetail, menu);

        MenuItem directionsItem = menu.findItem(R.id.action_directions);
        if (!wonderDetailHelper.hasLocation()) {
            directionsItem.setVisible(false);
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, wonder.description);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(share);

        bookmarkMenuItem = menu.findItem(R.id.action_bookmark);
        if (wonderDetailHelper.isBookmarked()) {
            bookmarkMenuItem.setIcon(R.drawable.ic_bookmark_white_24dp);
        }

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_directions:
                return wonderDetailHelper.startDirections();

            case R.id.action_bookmark:
                wonderDetailHelper.toggleBookmark();
                if (wonderDetailHelper.isBookmarked()) {
                    bookmarkMenuItem.setIcon(R.drawable.ic_bookmark_white_24dp);
                } else {
                    bookmarkMenuItem.setIcon(R.drawable.ic_bookmark_border_white_24dp);
                }
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDialogWebview(String title, String url) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);

        WebView wv = new WebView(this);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton(getString(R.string.CLOSE), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
