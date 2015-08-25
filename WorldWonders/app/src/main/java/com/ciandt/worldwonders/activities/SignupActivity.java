package com.ciandt.worldwonders.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.models.User;

public class SignupActivity extends BaseActivity {

    private EditText name;
    private EditText username;
    private EditText password;
    private Button signup;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.textName);
        username = (EditText) findViewById(R.id.textUsername);
        password = (EditText) findViewById(R.id.textPassword);
        signup = (Button) findViewById(R.id.sing_up_button);
        user = new User();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.name = username.getText().toString();
                user.password = password.getText().toString();

                Intent intent = new Intent(); // nao passar nada construtor (abrir duas vezes a activity)
                intent.putExtra("user", user);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
