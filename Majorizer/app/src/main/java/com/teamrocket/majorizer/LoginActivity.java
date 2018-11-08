package com.teamrocket.majorizer;

import com.teamrocket.majorizer.AppUtility.LoginManager;
import com.teamrocket.majorizer.UserGroups.Account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText clarksonUsernameField;
    private EditText passwordField;
    LoginManager loginManager = new LoginManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        clarksonUsernameField = findViewById(R.id.UsernameField);
        passwordField = findViewById(R.id.PasswordField);
    }

    public void Login(final View view) {
        // The Account login methods handles user validation, account
        // locking, and activitiy redirection, and error notifications.
        loginManager.Login(view, clarksonUsernameField, passwordField);
    }


}
