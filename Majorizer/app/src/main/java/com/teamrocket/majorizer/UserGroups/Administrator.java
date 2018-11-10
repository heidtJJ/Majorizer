package com.teamrocket.majorizer.UserGroups;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.R;

public class Administrator extends Account {

    public void unlockAccount(final String accountUserName, final Context context) {
        DatabaseReference accountsReference = FirebaseDatabase.getInstance().getReference("/Accounts/" + accountUserName + "/" + context.getText(R.string.LoginAttempts));
        accountsReference.setValue("0");
        Toast.makeText(context, "Successfully reset account attempts for " + accountUserName, Toast.LENGTH_LONG).show();
    }

}
