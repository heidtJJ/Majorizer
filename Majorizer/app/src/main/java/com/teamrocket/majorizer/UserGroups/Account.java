package com.teamrocket.majorizer.UserGroups;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.MainActivity;
import com.teamrocket.majorizer.AppUtility.DatabaseManager;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.AppUtility.Utility;

public class Account {
    private static final String BAD_CREDENTIALS = "Your username and/or password was incorrect!";
    private static final String EMPTY_FIELD = "Your username and/or password was empty!";
    private static final String BAD_QUERY = "Could not verify your credentials!";

    DatabaseManager databaseManager = new DatabaseManager();

    public enum AccountType {
        ERROR, UNDERGRAD, GRAD, ADVISOR, ADMIN;
    }

    private String id = null;
    private String username = null;
    private String password = null;// PROBABLY A BAD IDEA TO HAVE THIS IN MEMORY
    private AccountType accountType;
    private boolean accountLocked = false;

    String getId() {
        return this.id;
    }

    String getUsername() {
        return this.username;
    }

    AccountType getAccountType() {
        return this.accountType;
    }

    boolean isLocked() {
        return this.accountLocked;
    }

    void receiveNotification() {

    }

    void sendNotification() {

    }

    // Launch main activity if credentials are correct.
    public void Login(final View view, final EditText clarksonIdField, final EditText passwordField) {
        // Retrieve entered clarksonId and password from EditTexts.
        final String enteredClarksonID = clarksonIdField.getText().toString();
        final String enteredPassword = passwordField.getText().toString();

        // Hide the keyboard for user visibility.
        Utility.hideKeyboard(view);

        // Check if either user field is empty.
        if (enteredClarksonID.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(view.getContext(), EMPTY_FIELD, Toast.LENGTH_SHORT).show();
            return;
        }

        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + view.getContext().getText(R.string.Accounts) + "/" + enteredClarksonID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Context context = view.getContext();
                // Check if this clarkson ID exists in the database under /Accounts.
                if (dataSnapshot.getChildrenCount() == 0) {
                    // Entered clarkson ID was incorrect. Notify user.
                    Toast.makeText(context, BAD_CREDENTIALS, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseManager.isUserLockedOut(dataSnapshot, view)) return;

                // Check if actual password matches entered password.
                String actualPassword = dataSnapshot.child("/" + context.getText(R.string.Password)).getValue().toString();
                if (actualPassword.equals(enteredPassword)) {
                    // Entered password is correct. Advance to main activity.
                    Intent mainActivity = new Intent(context, MainActivity.class);

                    // Reset the user's number of login attempts.
                    databaseManager.resetLoginAttempts(dataSnapshot, enteredClarksonID, view);

                    // Put all of the user's data into the intent. This may save need for bandwidth use later.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        if (!key.equals(context.getText(R.string.CourseHistory)) && !key.equals(context.getText(R.string.CurrentCourses))) {
                            String value = snapshot.getValue().toString();
                            mainActivity.putExtra(key, value);
                        }
                    }

                    // Startup the main activity.
                    context.startActivity(mainActivity);
                    //((Activity) context).finish();
                } else {
                    // Entered password was incorrect. Notify user.
                    Toast.makeText(context, BAD_CREDENTIALS, Toast.LENGTH_SHORT).show();

                    // If user is not an admin, increment LoginAttempts field in their account.
                    databaseManager.incrementLoginAttempts(dataSnapshot, enteredClarksonID, view);
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(view.getContext(), BAD_QUERY, Toast.LENGTH_SHORT).show();
            }

        });

    }
}
