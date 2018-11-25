package com.teamrocket.majorizer.AppUtility;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.R;

import java.util.HashMap;
import java.util.Map;

import static com.teamrocket.majorizer.AppUtility.Utility.isValidName;
import static com.teamrocket.majorizer.AppUtility.Utility.isValidUserName;

public class AccountCreationManager {
    private static final String NULL = "NULL";

    public static void createNewUndergradStudentAccount(final Context context, final EditText usernameField,
                                                        final EditText passwordField, final EditText passwordRetryField,
                                                        final EditText firstNameField, final EditText lastNameField,
                                                        final String major1, final String major2,
                                                        final String minor1, final String minor2) {

        // Retrieve all strings from their respected EditText field.
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();
        final String passwordRetry = passwordRetryField.getText().toString();
        final String firstName = firstNameField.getText().toString();
        final String lastName = lastNameField.getText().toString();

        // Check if any of the fields are empty.
        if (username.isEmpty() || password.isEmpty() || passwordRetry.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty()) {
            // A field was empty. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.RequiredFieldEmpty), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the passwords match.
        if (!(password).equals(passwordRetry)) {
            // The passwords do not match. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.PasswordMismatch), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the username is valid (6 lowercase letters).
        if (!isValidUserName(username)) {
            // The username is invalid. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.InvalidUsername), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if first name and last name are valid names (contains only letters).
        if (!isValidName(firstName) || !isValidName(lastName)) {
            Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_LONG).show();
            return;
        }

        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Check if the username already exists in the database.
                if (dataSnapshot.hasChild(username)) {
                    // This username already exists
                    Toast.makeText(context, context.getText(R.string.UsernameExists), Toast.LENGTH_LONG).show();
                    return;
                }

                // Insert the user's credentials into the a map.
                Map<String, String> undergradFields = new HashMap<>();
                undergradFields.put(context.getText(R.string.Major1).toString(), major1);
                undergradFields.put(context.getText(R.string.Major2).toString(), major2);
                undergradFields.put(context.getText(R.string.Minor1).toString(), minor1);
                undergradFields.put(context.getText(R.string.Minor2).toString(), minor2);
                undergradFields.put(context.getText(R.string.FirstNameKey).toString(), firstName);
                undergradFields.put(context.getText(R.string.Id).toString(), String.valueOf("0000010"));
                undergradFields.put(context.getText(R.string.LastNameKey).toString(), lastName);
                undergradFields.put(context.getText(R.string.LoginAttempts).toString(), String.valueOf(0));
                undergradFields.put(context.getText(R.string.PasswordKey).toString(), password);
                undergradFields.put(context.getText(R.string.Type).toString(), context.getText(R.string.Undergrad).toString());
                undergradFields.put(context.getText(R.string.UsernameKey).toString(), username);
                undergradFields.put(context.getText(R.string.Advisor1).toString(), NULL);
                undergradFields.put(context.getText(R.string.Advisor2).toString(), NULL);

                // Insert the map into the database and notify the user.
                DatabaseReference newAccountRef = FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts) + "/" + username);
                newAccountRef.setValue(undergradFields);
                Toast.makeText(context, context.getText(R.string.SuccessfulAccountCreation), Toast.LENGTH_SHORT).show();

                // Set the EditTexts to empty.
                usernameField.setText("");
                firstNameField.setText("");
                lastNameField.setText("");
                passwordField.setText("");
                passwordRetryField.setText("");
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static void createNewGradStudentAccount(final Context context, final EditText usernameField,
                                                   final EditText passwordField, final EditText passwordRetryField,
                                                   final EditText firstNameField, final EditText lastNameField,
                                                   final String major) {

        // Retrieve all strings from their respected EditText field.
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();
        final String passwordRetry = passwordRetryField.getText().toString();
        final String firstName = firstNameField.getText().toString();
        final String lastName = lastNameField.getText().toString();

        // Check if any of the fields are empty.
        if (username.isEmpty() || password.isEmpty() || passwordRetry.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty()) {
            // A field was empty. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.RequiredFieldEmpty), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the passwords match.
        if (!(password).equals(passwordRetry)) {
            // The passwords do not match. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.PasswordMismatch), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the username is valid (6 lowercase letters).
        if (!isValidUserName(username)) {
            // The username is invalid. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.InvalidUsername), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if first name and last name are valid names (contains only letters).
        if (!isValidName(firstName) || !isValidName(lastName)) {
            Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_LONG).show();
            return;
        }

        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Check if the username already exists in the database.
                if (dataSnapshot.hasChild(username)) {
                    // This username already exists
                    Toast.makeText(context, context.getText(R.string.UsernameExists), Toast.LENGTH_LONG).show();
                    return;
                }

                // Insert the user's credentials into the a map.
                Map<String, String> undergradFields = new HashMap<>();
                undergradFields.put(context.getText(R.string.Major1).toString(), major);
                undergradFields.put(context.getText(R.string.FirstNameKey).toString(), firstName);
                undergradFields.put(context.getText(R.string.Id).toString(), String.valueOf("0000010"));
                undergradFields.put(context.getText(R.string.LastNameKey).toString(), lastName);
                undergradFields.put(context.getText(R.string.LoginAttempts).toString(), String.valueOf(0));
                undergradFields.put(context.getText(R.string.PasswordKey).toString(), password);
                undergradFields.put(context.getText(R.string.Type).toString(), context.getText(R.string.Grad).toString());
                undergradFields.put(context.getText(R.string.UsernameKey).toString(), username);
                undergradFields.put(context.getText(R.string.Advisor1).toString(), NULL);
                undergradFields.put(context.getText(R.string.Advisor2).toString(), NULL);

                // Insert the map into the database and notify the user.
                DatabaseReference newAccountRef = FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts) + "/" + username);
                newAccountRef.setValue(undergradFields);
                Toast.makeText(context, context.getText(R.string.SuccessfulAccountCreation), Toast.LENGTH_SHORT).show();

                // Set the EditTexts to empty.
                usernameField.setText("");
                firstNameField.setText("");
                lastNameField.setText("");
                passwordField.setText("");
                passwordRetryField.setText("");
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static void createNewAdvisorAccount(final Context context, final EditText usernameField,
                                               final EditText passwordField, final EditText passwordRetryField,
                                               final EditText firstNameField, final EditText lastNameField,
                                               final String department) {

        // Retrieve all strings from their respected EditText field.
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();
        final String passwordRetry = passwordRetryField.getText().toString();
        final String firstName = firstNameField.getText().toString();
        final String lastName = lastNameField.getText().toString();

        // Check if any of the fields are empty.
        if (username.isEmpty() || password.isEmpty() || passwordRetry.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty()) {
            // A field was empty. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.RequiredFieldEmpty), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the passwords match.
        if (!(password).equals(passwordRetry)) {
            // The passwords do not match. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.PasswordMismatch), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the username is valid (6 lowercase letters).
        if (!isValidUserName(username)) {
            // The username is invalid. Alert the user and leave this method.
            Toast.makeText(context, context.getText(R.string.InvalidUsername), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if first name and last name are valid names (contains only letters).
        if (!isValidName(firstName) || !isValidName(lastName)) {
            Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_LONG).show();
            return;
        }

        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Check if the username already exists in the database.
                if (dataSnapshot.hasChild(username)) {
                    // This username already exists
                    Toast.makeText(context, context.getText(R.string.UsernameExists), Toast.LENGTH_LONG).show();
                    return;
                }

                // Insert the user's credentials into the a map.
                Map<String, String> advisorFields = new HashMap<>();
                advisorFields.put(context.getText(R.string.Department).toString(), department);
                advisorFields.put(context.getText(R.string.FirstNameKey).toString(), firstName);
                advisorFields.put(context.getText(R.string.Id).toString(), String.valueOf("0000010"));
                advisorFields.put(context.getText(R.string.LastNameKey).toString(), lastName);
                advisorFields.put(context.getText(R.string.LoginAttempts).toString(), String.valueOf(0));
                advisorFields.put(context.getText(R.string.PasswordKey).toString(), password);
                advisorFields.put(context.getText(R.string.Type).toString(), context.getText(R.string.Advisor).toString());
                advisorFields.put(context.getText(R.string.UsernameKey).toString(), username);

                // Insert the map into the database and notify the user.
                DatabaseReference newAccountRef = FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts) + "/" + username);
                newAccountRef.setValue(advisorFields);
                Toast.makeText(context, context.getText(R.string.SuccessfulAccountCreation), Toast.LENGTH_SHORT).show();

                // Set the EditTexts to empty.
                usernameField.setText("");
                firstNameField.setText("");
                lastNameField.setText("");
                passwordField.setText("");
                passwordRetryField.setText("");
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
