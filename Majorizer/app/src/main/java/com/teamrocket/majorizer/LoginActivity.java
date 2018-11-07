package com.teamrocket.majorizer;

import com.teamrocket.majorizer.UserGroups.Account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText clarksonIdField;
    private EditText passwordField;
    Account account = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        clarksonIdField = findViewById(R.id.ClarksonIDField);
        passwordField = findViewById(R.id.PasswordField);
    }

    public void Login(final View view) {
        // The Account login methods handles user validation, account
        // locking, and activitiy redirection, and error notifications.
        account.Login(view, clarksonIdField, passwordField);
    }


}
