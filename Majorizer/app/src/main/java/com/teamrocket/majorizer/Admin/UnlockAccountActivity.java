package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Account;
import com.teamrocket.majorizer.UserGroups.Administrator;

import java.util.List;

public class UnlockAccountActivity extends AppCompatActivity {
    private Account account = null;

    private List<String> usernameList = null;
    private ArrayAdapter<String> arrayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_account);

        // Retreive the Account object passed from the LoginManager.
        account = (Account) getIntent().getSerializableExtra("MyClass");

        // Get list of locked accounts.
        usernameList = ((Administrator) account).getLockedAccounts();

        // Create array adapter for the list of usernames and assign it to
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernameList);
        arrayAdapter.notifyDataSetChanged();
        ListView userNameListView = findViewById(R.id.usernameListView);
        userNameListView.setAdapter(arrayAdapter);
    }

    public void unlockAccount(View view) {
        EditText editText = findViewById(R.id.accountNameField);
        String userNameToUnlock = editText.getText().toString();
        if (userNameToUnlock.isEmpty()) {
            // Notify the user that their input was empty.
            Toast.makeText(this, "The username field is empty. Please enter a username.", Toast.LENGTH_LONG).show();
        } else {
            // Unlock the user's account.
            ((Administrator) account).unlockAccount(userNameToUnlock.trim(), this);

            // Update the UI's listview.
            usernameList.remove(userNameToUnlock);
            arrayAdapter.notifyDataSetChanged();

            // Set the user's text (username field) to be empty.
            editText.setText("");
        }
    }
}
