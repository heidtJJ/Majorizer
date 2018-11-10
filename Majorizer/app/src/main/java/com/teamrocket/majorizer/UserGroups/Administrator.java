package com.teamrocket.majorizer.UserGroups;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.support.constraint.Constraints.TAG;

public class Administrator extends Account {

    public void unlockAccount(final String accountUserName, final Context context) {
        // Get reference this user's LoginAttempts field, then set it to 0.
        DatabaseReference accountsReference = FirebaseDatabase.getInstance().getReference("/Accounts/" + accountUserName + "/" + context.getText(R.string.LoginAttempts));
        accountsReference.setValue("0");
    }

    public List<String> getLockedAccounts() {
        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("/Accounts/");
        Query query = accountsRef.orderByChild("LoginAttempts").equalTo("3");

        final List<String> usernameSet = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot username : snapshot.getChildren()) {
                    usernameSet.add(username.getKey());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        return usernameSet;
    }

}
