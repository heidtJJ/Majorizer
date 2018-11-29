package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.List;

public class UnlockAccountActivity extends AppCompatActivity {
    private Administrator administrator = null;

    private List<String> lockedUsernameList = null;
    private ArrayAdapter<String> arrayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_account);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // Get list of locked accounts.
        lockedUsernameList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lockedUsernameList);

        administrator.getLockedAccounts(lockedUsernameList, arrayAdapter);

        // Create array adapter for the list of usernames.
        ListView userNameListView = findViewById(R.id.usernameListView);
        userNameListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    public void unlockAccount(final View view) {
        // Retrieve the username entered in the accountNameField.
        EditText editText = findViewById(R.id.accountNameField);
        String userNameToUnlock = editText.getText().toString();
        if (userNameToUnlock.isEmpty()) {
            // Notify the user that their input was empty.
            Toast.makeText(this, getText(R.string.EmptyUsername).toString(), Toast.LENGTH_LONG).show();
        } else if (lockedUsernameList.contains(userNameToUnlock)) {
            // Unlock the user's account.
            administrator.unlockAccount(userNameToUnlock.trim(), this);

            // Update the UI's listview.
            lockedUsernameList.remove(userNameToUnlock);
            arrayAdapter.notifyDataSetChanged();

            // Set the user's text (username field) to be empty.
            editText.setText("");
            Toast.makeText(this, getText(R.string.UnlockSuccess).toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getText(R.string.NotLocked).toString(), Toast.LENGTH_LONG).show();
        }
    }
}
