package com.ciandt.worldwonders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by bdias on 21/08/15.
 */
public class LoginFragment extends Fragment {

    private EditText username;
    private EditText password;
    private Button signup;
    private Button login;
    private UserModel user;

    public static final int SIGN_UP_REQUEST = 1;
    public static final int LOGIN_REQUEST = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = (EditText) view.findViewById(R.id.textUsername);
        password = (EditText) view.findViewById(R.id.textPassword);
        signup = (Button) view.findViewById(R.id.sing_up_button);
        login = (Button) view.findViewById(R.id.login_button);
        user = new UserModel();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(LoginFragment.this.getActivity(), SignupActivity.class);
            startActivityForResult(intent, SIGN_UP_REQUEST);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFragment.this.getActivity(), WorldWondersActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                user = (UserModel) data.getSerializableExtra("user");
                username.setText(user.name);
            }
        }
    }
}
