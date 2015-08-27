package com.ciandt.worldwonders.activities;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.fragments.LoginFragment;
import com.ciandt.worldwonders.fragments.WondersFragment;
import com.ciandt.worldwonders.helpers.AnimationHelper;
import com.ciandt.worldwonders.models.User;
import com.facebook.stetho.Stetho;


/**
 * Created by wgomes on 20/08/15.
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "WorldWonders";

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.w(TAG, "OnCreate");

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager manager = getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setLoginListener(new LoginFragment.OnLoginListener() {
            @Override
            public void onLogin(User user) {
                FragmentManager manager = getSupportFragmentManager();
                WondersFragment wondersFragment = new WondersFragment();
                manager.beginTransaction().replace(R.id.container, wondersFragment).commit();
            }
        });

        manager.beginTransaction().replace(R.id.container, loginFragment).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "OnStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "OnRestart");
    }

}
