package com.ciandt.worldwonders;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by wgomes on 20/08/15.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "WorldWonders";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.w(TAG,"OnCreate");

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
