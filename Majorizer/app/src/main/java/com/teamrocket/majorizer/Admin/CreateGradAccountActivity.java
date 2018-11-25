package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.AccountCreationManager;
import com.teamrocket.majorizer.R;

public class CreateGradAccountActivity extends AppCompatActivity {
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

        AccountCreationManager.createNewGradStudentAccount(this, usernameField, passwordField, passwordRetryField, firstNameField, lastNameField, major);
    }
}
