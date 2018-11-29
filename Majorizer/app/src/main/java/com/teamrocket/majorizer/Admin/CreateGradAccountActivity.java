package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.AccountCreationManager;
import com.teamrocket.majorizer.R;

import static com.teamrocket.majorizer.AppUtility.Utility.isValidName;
import static com.teamrocket.majorizer.AppUtility.Utility.isValidUserName;

public class CreateGradAccountActivity extends AppCompatActivity {
    Administrator administrator = null;

    // User Interface Views
    private EditText usernameField = null;
    private EditText passwordField = null;
    private EditText passwordRetryField = null;
    private EditText firstNameField = null;
    private EditText lastNameField = null;
    private RadioGroup majorRadioGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grad_account);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        usernameField = findViewById(R.id.accountUsername);
        passwordField = findViewById(R.id.accountPassword);
        passwordRetryField = findViewById(R.id.accountPasswordRetry);
        firstNameField = findViewById(R.id.accountFirstName);
        lastNameField = findViewById(R.id.accountLastName);
        majorRadioGroup = findViewById(R.id.accountMajor);
    }

    public void createNewGradStudentAccount(final View view) {
        // Retrieve the code of the department selected (i.e. CS, MA, PH)
        int radioButtonId = majorRadioGroup.getCheckedRadioButtonId();
        String major = null;
        switch (radioButtonId) {
            case R.id.radioMajor1_CS:
                major = getText(R.string.ComputerScience).toString();
                break;
            case R.id.radioMajor1_math:
                major = getText(R.string.Mathematics).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.MajorNotSelected), Toast.LENGTH_LONG).show();
                return;
        }

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
            Toast.makeText(this, getText(R.string.RequiredFieldEmpty), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the passwords match.
        if (!(password).equals(passwordRetry)) {
            // The passwords do not match. Alert the user and leave this method.
            Toast.makeText(this, getText(R.string.PasswordMismatch), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the username is valid (6 lowercase letters).
        if (!isValidUserName(username)) {
            // The username is invalid. Alert the user and leave this method.
            Toast.makeText(this, getText(R.string.InvalidUsername), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if first name and last name are valid names (contains only letters).
        if (!isValidName(firstName) || !isValidName(lastName)) {
            Toast.makeText(this, getText(R.string.InvalidName), Toast.LENGTH_LONG).show();
            return;
        }

        // Let the administrator object create the new account.
        administrator.createNewGradStudentAccount(this, major, username, password, firstName, lastName);

        // Set the EditTexts to empty and uncheck radio group.
        usernameField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        passwordRetryField.setText("");
        majorRadioGroup.clearCheck();
    }
}
