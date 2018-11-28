package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.AccountCreationManager;
import com.teamrocket.majorizer.R;

public class CreateUndergradActivity extends AppCompatActivity {
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
        // Retreive the selected first student major option.
        int major1RadioGroup = this.major1RadioGroup.getCheckedRadioButtonId();
        String major1 = null;
        switch (major1RadioGroup) {
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
        int major2RadioGroup = this.major2RadioGroup.getCheckedRadioButtonId();
        String major2 = null;
        switch (major2RadioGroup) {
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
        int minor1RadioGroup = this.minor1RadioGroup.getCheckedRadioButtonId();
        String minor1 = null;
        switch (minor1RadioGroup) {
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
        int minor2RadioGroup = this.minor2RadioGroup.getCheckedRadioButtonId();
        String minor2 = null;
        switch (minor2RadioGroup) {
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


        AccountCreationManager.createNewUndergradStudentAccount(this, usernameField, passwordField, passwordRetryField, firstNameField, lastNameField, major1, major2, minor1, minor2);
    }

}
