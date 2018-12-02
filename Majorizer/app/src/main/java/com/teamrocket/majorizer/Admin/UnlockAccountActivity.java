package com.teamrocket.majorizer.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        final Context context = this;
        userNameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                final String userNameToUnlock = (String) adapter.getItemAtPosition(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Account Unlock Confirmation");
                alertDialogBuilder
                        .setMessage("Unlock %s account?".format(userNameToUnlock))
                        .setPositiveButton("Unlock", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                administrator.unlockAccount(userNameToUnlock.trim(), context);
                                // Update the UI's listview.
                                lockedUsernameList.remove(userNameToUnlock);
                                arrayAdapter.notifyDataSetChanged();
                                Toast.makeText(context, getText(R.string.UnlockSuccess).toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}
