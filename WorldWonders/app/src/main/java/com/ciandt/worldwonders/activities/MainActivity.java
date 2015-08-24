package com.ciandt.worldwonders.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.fragments.LoginFragment;
import com.ciandt.worldwonders.fragments.WondersFragment;
import com.ciandt.worldwonders.models.User;


/**
 * Created by wgomes on 20/08/15.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WorldWonders";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.w(TAG, "OnCreate");

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
