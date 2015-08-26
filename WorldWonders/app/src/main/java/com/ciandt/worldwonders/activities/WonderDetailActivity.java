package com.ciandt.worldwonders.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    ImageView image;
    TextView name;
    TextView description;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wonderdetail);
        context = this;


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
  //      toolbar.inflateMenu(R.menu.menu_wonderdetail);


        image = (ImageView)findViewById(R.id.image);
        name = (TextView)findViewById(R.id.name);
        description = (TextView)findViewById(R.id.description);

        WondersRepository wondersRepository = new WondersRepository(this);
        wondersRepository.getAll(new BaseRepository.ListResultListener<Wonder>() {
            @Override
            public void onResult(Exception e, List<Wonder> list) {
                if(list.size() > 0) {

                    Wonder wonder = list.get(0);

                    name.setText(wonder.name.toUpperCase());
                    description.setText(wonder.description+ wonder.description);

                    String pictureFilename = wonder.photo.split("\\.")[0];
                    int pictureResource = Helper.getRawResourceID(context, pictureFilename);

                    Picasso.with(context)
                            .load(pictureResource)
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(image);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wonderdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
