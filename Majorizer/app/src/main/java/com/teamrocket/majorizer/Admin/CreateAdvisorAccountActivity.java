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

public class CreateAdvisorAccountActivity extends AppCompatActivity {

    private EditText usernameField = null;
    private EditText passwordField = null;
    private EditText passwordRetryField = null;
    private EditText firstNameField = null;
    private EditText lastNameField = null;
    private RadioGroup departmentRadioGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advisor_account);

        usernameField = findViewById(R.id.accountUsername);
        passwordField = findViewById(R.id.accountPassword);
        passwordRetryField = findViewById(R.id.accountPasswordRetry);
        firstNameField = findViewById(R.id.accountFirstName);
        lastNameField = findViewById(R.id.accountLastName);
        departmentRadioGroup = findViewById(R.id.accountDepartment);
    }


    public void createNewAdvisorAccount(final View view) {
        // Retrieve the code of the department selected (i.e. CS, MA, PH)
        int radioButtonId = departmentRadioGroup.getCheckedRadioButtonId();
        String department = null;
        switch (radioButtonId) {
            case R.id.radio_CS:
                department = getText(R.string.ComputerScienceCode).toString();
                break;
            case R.id.radio_math:
                department = getText(R.string.MathematicsCode).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.DepartmentNotSelected), Toast.LENGTH_LONG).show();
                return;
        }

        AccountCreationManager.createNewAdvisorAccount(this, usernameField, passwordField, passwordRetryField, firstNameField, lastNameField, department);
    }
}
