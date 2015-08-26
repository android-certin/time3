package com.ciandt.worldwonders.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.helpers.Helper;
import com.ciandt.worldwonders.models.Wonder;
import com.ciandt.worldwonders.repositories.BaseRepository;
import com.ciandt.worldwonders.repositories.WondersRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WonderDetailActivity extends AppCompatActivity {

    Context context;
    Wonder wonder;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    ImageView image;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wonderdetail);
        context = this;

        Intent intent = (Intent) getIntent();
        wonder = (Wonder) intent.getSerializableExtra("wonder");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        image = (ImageView) findViewById(R.id.image);
        description = (TextView) findViewById(R.id.description);

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wonderdetail, menu);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, wonder.description);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        shareActionProvider.setShareIntent(share);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
