package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.AppUtility.Utility;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class CreateGradCourseActivity extends AppCompatActivity {
    private EditText courseNumberEditText = null;
    private EditText courseNameEditText = null;
    private EditText numCreditsEditText = null;
    private RadioGroup departmentRadioGroup = null;

    private Administrator administrator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grad_course);

        // Retreive the Account object passed from the previous activity.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        courseNumberEditText = findViewById(R.id.courseNumberEditText);
        courseNameEditText = findViewById(R.id.courseNameEditText);
        numCreditsEditText = findViewById(R.id.numCreditsEditText);
        departmentRadioGroup = findViewById(R.id.courseDepartment);
    }

    public void createNewGradCourse(final View view) {
        String courseNumber = courseNumberEditText.getText().toString();
        String courseName = courseNameEditText.getText().toString();
        String numCredits = numCreditsEditText.getText().toString();

        // Check if any of the text-inputs are empty.
        if (courseNumber.isEmpty() || courseName.isEmpty() || numCredits.isEmpty()) {
            Toast.makeText(this, getText(R.string.MissingInput).toString(), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if any of the radio groups are unchecked.
        if (departmentRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getText(R.string.MissingCheckBox).toString(), Toast.LENGTH_LONG).show();
            return;
        }

        // Check that courseNumberString is a number between 500 and 999.
        if (!Utility.isValidGradCourseNumber(courseNumber)) {
            Toast.makeText(this, getText(R.string.InvalidCourseNumber).toString(), Toast.LENGTH_LONG).show();
            return;
        }

        // Check that numCreditsString is a number between 3 and 4 (no decimal).
        if (!Utility.isValidNumberCredits(numCredits)) {
            Toast.makeText(this, getText(R.string.InvalidNumberCredits).toString(), Toast.LENGTH_LONG).show();
            return;
        }

        // Retreive the selected second minor option.
        int department = departmentRadioGroup.getCheckedRadioButtonId();
        String classCodePrefix = null;
        switch (department) {
            case R.id.radioDepartment_PH:
                classCodePrefix = getText(R.string.PhysicsCode).toString();
                break;
            case R.id.radioDepartment_MA:
                classCodePrefix = getText(R.string.MathematicsCode).toString();
                break;
            case R.id.radioDepartment_CS:
                classCodePrefix = getText(R.string.ComputerScienceCode).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.MissingCheckBox), Toast.LENGTH_LONG).show();
                return;
        }

        // Input is valid. Proceed to add class to database.
        final String courseId = classCodePrefix + courseNumber;
        final Course course = new Course(courseName, courseId, Integer.valueOf(numCredits), new HashSet<Course>());

        // Construct URL to new course location in database.
        String masterCourseListURL = getText(R.string.MasterCourseListURL).toString() + "/" + courseId;
        administrator.addCourseToDatabase(new ArrayList<>(Arrays.asList(course)), 0, null, masterCourseListURL, this);
        Utility.hideKeyboard(this, view);
    }
}
