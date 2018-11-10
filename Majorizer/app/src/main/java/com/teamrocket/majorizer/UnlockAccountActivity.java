package com.teamrocket.majorizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.teamrocket.majorizer.UserGroups.Account;
import com.teamrocket.majorizer.UserGroups.Administrator;

public class UnlockAccountActivity extends AppCompatActivity {
    private Account account = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_account);

        // Retreive the Account object passed from the LoginManager.
        account = (Account) getIntent().getSerializableExtra("MyClass");
    }

    public void unlockAccount(View view) {
        EditText editText = findViewById(R.id.accountNameField);
        String userNameToUnlock = editText.getText().toString();
        ((Administrator) account).unlockAccount(userNameToUnlock, this);
    }
}
