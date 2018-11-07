package com.teamrocket.majorizer;

import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {

    private static final int NUM_LOCKED_OUT_ATTEMPTS = 3;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public boolean isUserLockedOut(DataSnapshot dataSnapshot, final View view) {
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

    public void incrementLoginAttempts(DataSnapshot dataSnapshot, String enteredClarksonId, final View view) {
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

    public void resetLoginAttempts(DataSnapshot dataSnapshot, String enteredClarksonId, final View view) {
        Resources resources = view.getResources();
        // Check if database has a LoginAttempts field for this user. Admins will not have this field.
        if (dataSnapshot.hasChild(resources.getString(R.string.LoginAttempts))) {
            // Reset the number of LoginAttempts for this user.
            mDatabase.child("/" + resources.getText(R.string.Accounts) + "/" + enteredClarksonId + "/" +
                    resources.getString(R.string.LoginAttempts)).setValue(String.valueOf(0));
        }
    }
}
