package com.ciandt.worldwonders;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by wgomes on 20/08/15.
 */
public class LoginActivity extends AppCompatActivity {

    public static final int SIGN_UP_REQUEST = 1;

    private static final String TAG = "WorldWonders";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.w(TAG, "OnCreate");

        FragmentManager manager = getSupportFragmentManager();
        LoginFragment fragment = new LoginFragment();
        manager.beginTransaction().replace(R.id.container, fragment).commit();

//        username = (EditText) findViewById(R.id.textUsername);
//        password = (EditText) findViewById(R.id.textPassword);
//        signup = (Button) findViewById(R.id.sing_up_button);
//        login = (Button) findViewById(R.id.login_button);
//        user = new UserModel();
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
//                startActivityForResult(intent, SIGN_UP_REQUEST);
//            }
//        });
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


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("LOGIN", "onActivityResult");
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SIGN_UP_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                user = (UserModel) data.getSerializableExtra("user");
//                username.setText(user.name);
//            }
//        }
//    }
}
