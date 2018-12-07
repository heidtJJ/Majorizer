package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.Utility;
import com.teamrocket.majorizer.R;

import static com.teamrocket.majorizer.AppUtility.Utility.isValidName;
import static com.teamrocket.majorizer.AppUtility.Utility.isValidUserName;

public class CreateUndergradAccountActivity extends AppCompatActivity {
    private Administrator administrator = null;

    // User Interface Views
    private EditText usernameField = null;
    private EditText passwordField = null;
    private EditText passwordRetryField = null;
    private EditText firstNameField = null;
    private EditText lastNameField = null;
    private RadioGroup major1RadioGroup = null;
    private RadioGroup major2RadioGroup = null;
    private RadioGroup minor1RadioGroup = null;
    private RadioGroup minor2RadioGroup = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_undergrad_account);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // Retrieve all use entries.
        usernameField = findViewById(R.id.accountUsername);
        passwordField = findViewById(R.id.accountPassword);
        passwordRetryField = findViewById(R.id.accountPasswordRetry);
        firstNameField = findViewById(R.id.accountFirstName);
        lastNameField = findViewById(R.id.accountLastName);
        major1RadioGroup = findViewById(R.id.accountMajor1);
        major2RadioGroup = findViewById(R.id.accountMajor2);
        minor1RadioGroup = findViewById(R.id.accountMinor1);
        minor2RadioGroup = findViewById(R.id.accountMinor2);
    }

    /**
     * This is the response of the onClick field for the Create Account button.
     *
     * @param view
     */
    public void createNewUndergradAccount(final View view) {

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

        // Check if password is valid
        if (!Utility.isValidPassword(password)) {
            // The password is invalid. Alert the user and leave this method.
            Toast.makeText(this, "The password which you entered is invalid!", Toast.LENGTH_LONG).show();
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
        // Retreive the selected first student major option.
        int major1RadioGroupChoice = this.major1RadioGroup.getCheckedRadioButtonId();
        String major1 = null;
        switch (major1RadioGroupChoice) {
            case R.id.radioMajor1_CS:
                major1 = getText(R.string.ComputerScience).toString();
                break;
            case R.id.radioMajor1_math:
                major1 = getText(R.string.Mathematics).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.FirstMajorNotSelected), Toast.LENGTH_LONG).show();
                return;
        }

        // Retreive the selected second major option.
        int major2RadioGroupChoice = this.major2RadioGroup.getCheckedRadioButtonId();
        String major2 = null;
        switch (major2RadioGroupChoice) {
            case R.id.radioMajor2_CS:
                major2 = getText(R.string.ComputerScience).toString();
                break;
            case R.id.radioMajor2_math:
                major2 = getText(R.string.Mathematics).toString();
                break;
            case R.id.radioMajor2_none:
                major2 = getText(R.string.NullString).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.SecondMajorNotSelected), Toast.LENGTH_LONG).show();
                return;
        }

        // Check if the user checked the same major for both major1 and major2. This would be a user error.
        if (major1.equals(major2)) {
            // User selected same major twice.
            Toast.makeText(this, getText(R.string.SameMajorError), Toast.LENGTH_LONG).show();
            return;
        }


        // Retreive the selected first minor option.
        int minor1RadioGroupChoice = this.minor1RadioGroup.getCheckedRadioButtonId();
        String minor1 = null;
        switch (minor1RadioGroupChoice) {
            case R.id.radioMinor1_PH:
                minor1 = getText(R.string.Physics).toString();
                break;
            case R.id.radioMinor1_math:
                minor1 = getText(R.string.Mathematics).toString();
                break;
            case R.id.radioMinor1_none:
                minor1 = getText(R.string.NullString).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.FirstMinorNotSelected), Toast.LENGTH_LONG).show();
                return;
        }

        // Retreive the selected second minor option.
        int minor2RadioGroupChoice = this.minor2RadioGroup.getCheckedRadioButtonId();
        String minor2 = null;
        switch (minor2RadioGroupChoice) {
            case R.id.radioMinor2_PH:
                minor2 = getText(R.string.Physics).toString();
                break;
            case R.id.radioMinor2_math:
                minor2 = getText(R.string.Mathematics).toString();
                break;
            case R.id.radioMinor2_none:
                minor2 = getText(R.string.NullString).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.SecondMinorNotSelected), Toast.LENGTH_LONG).show();
                return;
        }
        // Check if the user selected a minor2 but not a minor1.
        if (minor1.equals(getText(R.string.NullString).toString()) && !minor2.equals(getText(R.string.NullString).toString())) {
            // User selected same major twice.
            Toast.makeText(this, getText(R.string.Minor1NotSelected), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the user checked the same major for both minor1 and minor2. This would be a user error.
        if (minor1.equals(minor2) && !minor1.equals(getText(R.string.NullString).toString())) {
            // User selected same major twice.
            Toast.makeText(this, getText(R.string.SameMinorError), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the user checked the same major1 for both minor1 and minor2. This would be a user error.
        if (minor1.equals(major1) || minor2.equals(major1)) {
            // User has selected the same major as a minor.
            Toast.makeText(this, getText(R.string.SameMajorMinorError), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if the user checked the same major2 for both minor1 and minor2. This would be a user error.
        if ((minor1.equals(major2) || minor2.equals(major2)) && !major2.equals(getText(R.string.NullString).toString())) {
            // User has selected the same major as a minor.
            Toast.makeText(this, getText(R.string.SameMajorMinorError), Toast.LENGTH_LONG).show();
            return;
        }

        // User input was valid. Let Admin create new undergrad account.
        administrator.createNewUndergradStudentAccount(this, username, password, firstName, lastName, major1, major2, minor1, minor2);

        // Set the EditTexts to empty and uncheck RadioGroups.
        usernameField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        passwordRetryField.setText("");
        major1RadioGroup.clearCheck();
        major2RadioGroup.clearCheck();
        minor1RadioGroup.clearCheck();
        minor2RadioGroup.clearCheck();
    }

}
