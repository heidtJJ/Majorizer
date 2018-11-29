package com.teamrocket.majorizer;

import com.teamrocket.majorizer.AppUtility.LoginManager;

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

    /**
     * This function is a listener to the login-button on the login screen.
     * This calls the LoginManager class to log the user in, if possible.
     *
     * @param view the login button
     */
    public void Login(final View view) {
        // The Account login methods handles user validation, account
        // locking, and activitiy redirection, and error notifications.
        loginManager.Login(clarksonUsernameField, passwordField);
    }


}
