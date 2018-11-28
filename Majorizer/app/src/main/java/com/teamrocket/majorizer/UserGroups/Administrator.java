package com.teamrocket.majorizer.UserGroups;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class Administrator extends Account {

    public void unlockAccount(final String accountUserName, final Context context) {
        // Get reference this user's LoginAttempts field, then set it to 0.
        DatabaseReference accountsReference = FirebaseDatabase.getInstance().getReference("/Accounts/" + accountUserName + "/" + context.getText(R.string.LoginAttempts));
        accountsReference.setValue("0");
    }

    public List<String> getLockedAccounts(final List<String> usernameList, final ArrayAdapter arrayAdapter) {
        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("/Accounts/");
        Query query = accountsRef.orderByChild("LoginAttempts").equalTo("3");

        // Beware: this event listener is asynchronous.
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot username : snapshot.getChildren()) {
                    usernameList.add(username.getKey());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        return usernameList;
    }

}
