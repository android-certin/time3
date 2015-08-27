package com.ciandt.worldwonders.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.fragments.WonderDetailFragment;
import com.ciandt.worldwonders.models.Wonder;

public class WonderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wonderdetail);

        Intent intent = (Intent) getIntent();
        Wonder wonder = (Wonder) intent.getSerializableExtra("wonder");

        FragmentManager manager = getSupportFragmentManager();
        WonderDetailFragment fragment = WonderDetailFragment.newInstance(wonder);
        manager.beginTransaction().replace(R.id.container_detail, fragment).commit();
    }

}
