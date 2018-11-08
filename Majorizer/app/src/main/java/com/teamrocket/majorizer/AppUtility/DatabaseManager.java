package com.teamrocket.majorizer.AppUtility;

import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Account;

import java.io.Serializable;

public class DatabaseManager implements Serializable {

    private static final int NUM_LOCKED_OUT_ATTEMPTS = 3;
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public boolean isUserLockedOut(final DataSnapshot dataSnapshot, final View view) {
        Resources resources = view.getResources();
        // Check if this user is already locked out. Admins will not have LoginAttempts in their account fields.
        if (dataSnapshot.hasChild(resources.getString(R.string.LoginAttempts))) {
            // Find the number of LoginAttempts value in the database for this user.
            DataSnapshot numLoginAttemptsDataSnap = dataSnapshot.child(resources.getText(R.string.LoginAttempts).toString());
            String numLoginAttemptsString = numLoginAttemptsDataSnap.getValue().toString();

            // Increment and update the number of LoginAttempts for this user.
            int numLoginAttemptsInt = Integer.parseInt(numLoginAttemptsString);
            if (numLoginAttemptsInt >= NUM_LOCKED_OUT_ATTEMPTS) {
                // User is already locked out. Nofify them.
                Toast.makeText(view.getContext(), resources.getText(R.string.UserLockedOut), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public void incrementLoginAttempts(final DataSnapshot dataSnapshot, final String enteredClarksonId, final View view) {
        Resources resources = view.getResources();
        // Check if database has a LoginAttempts field for this user. Admins will not have this field.
        if (dataSnapshot.hasChild(view.getResources().getString(R.string.LoginAttempts))) {
            // Find the number of LoginAttempts value in the database for this user.
            DataSnapshot numLoginAttemptsDataSnap = dataSnapshot.child(resources.getText(R.string.LoginAttempts).toString());
            String numLoginAttemptsString = numLoginAttemptsDataSnap.getValue().toString();

            // Increment and update the number of LoginAttempts for this user.
            int numLoginAttemptsInt = Integer.parseInt(numLoginAttemptsString) + 1;
            mDatabase.child("/" + resources.getText(R.string.Accounts) + "/" + enteredClarksonId + "/" +
                    resources.getString(R.string.LoginAttempts)).setValue(String.valueOf(numLoginAttemptsInt));
        }
    }

    public void resetLoginAttempts(final DataSnapshot dataSnapshot, final String enteredClarksonId, final View view) {
        Resources resources = view.getResources();
        // Check if database has a LoginAttempts field for this user. Admins will not have this field.
        if (dataSnapshot.hasChild(resources.getString(R.string.LoginAttempts))) {
            // Reset the number of LoginAttempts for this user.
            mDatabase.child("/" + resources.getText(R.string.Accounts) + "/" + enteredClarksonId + "/" +
                    resources.getString(R.string.LoginAttempts)).setValue(String.valueOf(0));
        }
    }

    public void populateAccount(final DataSnapshot dataSnapshot, final Account account) {

    }
}
