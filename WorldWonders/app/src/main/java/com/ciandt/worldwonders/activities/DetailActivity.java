package com.ciandt.worldwonders.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    Context context;
    ImageView image;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;

        image = (ImageView)findViewById(R.id.image);
        description = (TextView)findViewById(R.id.description);

        WondersRepository wondersRepository = new WondersRepository(this);
        wondersRepository.getAll(new BaseRepository.ListResultListener<Wonder>() {
            @Override
            public void onResult(Exception e, List<Wonder> list) {
                if(list.size() > 0) {

                    Wonder wonder = list.get(0);

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
            }
        });

    }

}
