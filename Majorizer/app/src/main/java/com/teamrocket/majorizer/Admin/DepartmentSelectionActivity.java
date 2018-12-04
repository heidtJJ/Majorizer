package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

public class DepartmentSelectionActivity extends AppCompatActivity {
    private Administrator administrator = null;
    private String adminAction = null;
    private String courseType = null;
    private TextView departmentSelectionTitle = null;
    private TextView courseTitle = null;
    private Course course = null;
    private RadioGroup courseDepartment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_selection);

        // Get TextViews from the UI.
        departmentSelectionTitle = findViewById(R.id.departmentSelectionTitle);
        courseTitle = findViewById(R.id.courseTitle);
        courseDepartment = findViewById(R.id.courseDepartment);

        // Retrieve the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either grad or undergrad.
        courseType = (String) getIntent().getSerializableExtra(getText(R.string.CourseType).toString());

        // Retrieve course object from previous activity (Search activity).
        course = (Course) getIntent().getSerializableExtra(getText(R.string.Course).toString());

        // Construct title dynamically.
        courseTitle.setText(course.getCourseCode() + ": " + course.getCourseName());


        departmentSelectionTitle.setText(getPageTitle(courseType, adminAction));
    }

    public void changeCurriculum(final View view) {
        String sucessMessage = "";

        if (courseDepartment.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getText(R.string.MissingCheckBox), Toast.LENGTH_LONG).show();
            return;
        }

        // Retreive the selected second minor option.
        int department = courseDepartment.getCheckedRadioButtonId();
        String classCodePrefix = null;
        String departmentName = null;
        switch (department) {
            case R.id.radioDepartment_PH:
                classCodePrefix = getText(R.string.PhysicsCode).toString();
                departmentName = getText(R.string.Physics).toString();
                break;
            case R.id.radioDepartment_MA:
                classCodePrefix = getText(R.string.MathematicsCode).toString();
                departmentName = getText(R.string.Mathematics).toString();
                break;
            case R.id.radioDepartment_CS:
                classCodePrefix = getText(R.string.ComputerScienceCode).toString();
                departmentName = getText(R.string.ComputerScience).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.MissingCheckBox), Toast.LENGTH_LONG).show();
                return;
        }

        Toast.makeText(this, getSuccessMessage(courseType, adminAction, departmentName), Toast.LENGTH_LONG).show();
        finish();
    }

    private String getSuccessMessage(final String courseType, final String adminAction, final String departmentName) {
        String pageTitle = "Successfully ";
        if (adminAction.equals(getText(R.string.AddCourse))) {
            pageTitle += "added this course to ";
        } else {
            pageTitle += "removed this course from ";
        }
        if (courseType.equals(getText(R.string.Undergrad))) {
            pageTitle += "the Undergraduate department of ";
        } else {
            pageTitle += "the Graduate department of ";
        }
        return pageTitle + departmentName;
    }

    private String getPageTitle(final String courseType, final String adminAction) {
        String pageTitle = "Enter ";
        if (courseType.equals(getText(R.string.Undergrad))) {
            pageTitle += "an Undergraduate department to ";
        } else {
            pageTitle += "a Graduate department to ";
        }

        if (adminAction.equals(getText(R.string.AddCourse))) {
            pageTitle += "add this course to.";
        } else {
            pageTitle += "remove this course from.";
        }
        return pageTitle;
    }
}
