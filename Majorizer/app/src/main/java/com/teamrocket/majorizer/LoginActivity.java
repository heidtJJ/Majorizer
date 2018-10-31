package com.teamrocket.majorizer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private EditText clarksonIdField;
    private EditText passwordField;

    private static final String BAD_CREDENTIALS = "Your username and/or password was incorrect!";
    private static final String EMPTY_FIELD = "Your username and/or password was empty!";
    private static final String BAD_QUERY = "Could not verify your credentials!";
    private static final String USER_LOCKED_OUT = "Your account is locked! You have exceeded the number of login attempts.";

    private static final int NUM_LOCKED_OUT_ATTEMPTS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        clarksonIdField = findViewById(R.id.ClarksonIDField);
        passwordField = findViewById(R.id.PasswordField);
    }

    // Launch main activity if credentials are correct.
    public void Login(final View view) {
        // Retrieve entered clarksonId and password from EditTexts.
        final String enteredClarksonID = clarksonIdField.getText().toString();
        final String enteredPassword = passwordField.getText().toString();

        // Hide the keyboard for user visibility.
        hideKeyboard(view);

        // Check if either user field is empty.
        if (enteredClarksonID.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, EMPTY_FIELD, Toast.LENGTH_SHORT).show();
            return;
        }

        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + getText(R.string.Accounts) + "/" + enteredClarksonID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if this clarkson ID exists in the database under /Accounts.
                if (dataSnapshot.getChildrenCount() == 0) {
                    // Entered clarkson ID was incorrect. Notify user.
                    Toast.makeText(LoginActivity.this, BAD_CREDENTIALS, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isUserLockedOut(dataSnapshot)) return;

                // Check if actual password matches entered password.
                String actualPassword = dataSnapshot.child("/" + getText(R.string.Password)).getValue().toString();
                if (actualPassword.equals(enteredPassword)) {
                    // Entered password is correct. Advance to main activity.
                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);

                    // Reset the user's number of login attempts.
                    resetLoginAttempts(dataSnapshot, enteredClarksonID);

                    // Put all of the user's data into the intent. This may save need for bandwidth use later.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        if (!key.equals(getText(R.string.CourseHistory)) && !key.equals(getText(R.string.CurrentCourses))) {
                            String value = snapshot.getValue().toString();
                            mainActivity.putExtra(key, value);
                        }
                    }

                    // Startup the main activity.
                    startActivity(mainActivity);
                } else {
                    // Entered password was incorrect. Notify user.
                    Toast.makeText(LoginActivity.this, BAD_CREDENTIALS, Toast.LENGTH_SHORT).show();

                    // If user is not an admin, increment LoginAttempts field in their account.
                    incrementLoginAttempts(dataSnapshot, enteredClarksonID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(LoginActivity.this, BAD_QUERY, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private boolean isUserLockedOut(DataSnapshot dataSnapshot) {
        // Check if this user is already locked out. Admins will not have LoginAttempts in their account fields.
        if (dataSnapshot.hasChild(getString(R.string.LoginAttempts))) {
            // Find the number of LoginAttempts value in the database for this user.
            DataSnapshot numLoginAttemptsDataSnap = dataSnapshot.child(getText(R.string.LoginAttempts).toString());
            String numLoginAttemptsString = numLoginAttemptsDataSnap.getValue().toString();

            // Increment and update the number of LoginAttempts for this user.
            int numLoginAttemptsInt = Integer.parseInt(numLoginAttemptsString);
            if (numLoginAttemptsInt >= NUM_LOCKED_OUT_ATTEMPTS) {
                // User is already locked out. Nofify them.
                Toast.makeText(LoginActivity.this, USER_LOCKED_OUT, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    private void incrementLoginAttempts(DataSnapshot dataSnapshot, String enteredClarksonId) {
        // Check if database has a LoginAttempts field for this user. Admins will not have this field.
        if (dataSnapshot.hasChild(getString(R.string.LoginAttempts))) {
            // Find the number of LoginAttempts value in the database for this user.
            DataSnapshot numLoginAttemptsDataSnap = dataSnapshot.child(getText(R.string.LoginAttempts).toString());
            String numLoginAttemptsString = numLoginAttemptsDataSnap.getValue().toString();

            // Increment and update the number of LoginAttempts for this user.
            int numLoginAttemptsInt = Integer.parseInt(numLoginAttemptsString) + 1;
            mDatabase.child("/" + getText(R.string.Accounts) + "/" + enteredClarksonId + "/" + getString(R.string.LoginAttempts))
                    .setValue(String.valueOf(numLoginAttemptsInt));
        }
    }

    private void resetLoginAttempts(DataSnapshot dataSnapshot, String enteredClarksonId) {
        // Check if database has a LoginAttempts field for this user. Admins will not have this field.
        if (dataSnapshot.hasChild(getString(R.string.LoginAttempts))) {
            // Reset the number of LoginAttempts for this user.
            mDatabase.child("/" + getText(R.string.Accounts) + "/" + enteredClarksonId + "/" + getString(R.string.LoginAttempts))
                    .setValue(String.valueOf(0));
        }
    }

    // A utility method for hiding the keyboard on the screen.
    public void hideKeyboard(final View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
