package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

public class DepartmentSelectionActivity extends AppCompatActivity {
    // Objects from previous activity.
    private Administrator administrator = null;
    private String adminAction = null;
    private String courseType = null;
    private String adminActionType = null;

    // UI elements
    private TextView departmentSelectionTitle = null;
    private RadioGroup courseDepartmentRadioGroup = null;
    private RadioGroup majorOrMinorRadioGroup = null;
    private GridLayout majorOrMinorGridLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_selection);

        // Get TextViews from the UI.
        departmentSelectionTitle = findViewById(R.id.departmentSelectionTitle);
        courseDepartmentRadioGroup = findViewById(R.id.courseDepartment);
        majorOrMinorRadioGroup = findViewById(R.id.majorOrMinor);
        majorOrMinorGridLayout = findViewById(R.id.majorOrMinorGridlayout);

        // Retrieve the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either MasterCourseList or Curriculum
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());

        // adminActionType is either grad or undergrad.
        courseType = (String) getIntent().getSerializableExtra(getText(R.string.CourseType).toString());

        // Allow the user to select whether this course is for a major or minor.
        if (courseType.equals(getText(R.string.Undergrad))) {
            majorOrMinorGridLayout.setVisibility(View.VISIBLE);
        }

        // Construct title dynamically.
        departmentSelectionTitle.setText(getPageTitle(courseType, adminAction));
    }

    public void changeCurriculum(final View view) {
        if (courseDepartmentRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getText(R.string.MissingCheckBox), Toast.LENGTH_LONG).show();
            return;
        }

        // Retrieve the selected second minor option.
        int department = courseDepartmentRadioGroup.getCheckedRadioButtonId();
        String departmentName = null;
        switch (department) {
            case R.id.radioDepartment_PH:
                departmentName = getText(R.string.Physics).toString();
                break;
            case R.id.radioDepartment_MA:
                departmentName = getText(R.string.Mathematics).toString();
                break;
            case R.id.radioDepartment_CS:
                departmentName = getText(R.string.ComputerScience).toString();
                break;
            default:
                // Department was not selected. Alert the user and leave this method.
                Toast.makeText(this, getText(R.string.MissingCheckBox), Toast.LENGTH_LONG).show();
                return;
        }


        int majorOrMinor = majorOrMinorRadioGroup.getCheckedRadioButtonId();
        String courseLevel = null;
        if (majorOrMinorGridLayout.getVisibility() == View.VISIBLE) {
            switch (majorOrMinor) {
                case R.id.major:
                    courseLevel = getText(R.string.UndergradMajors).toString();
                    break;
                case R.id.minor:
                    courseLevel = getText(R.string.Minors).toString();
                    break;
                default:
                    // Department was not selected. Alert the user and leave this method.
                    Toast.makeText(this, getText(R.string.MissingCheckBox), Toast.LENGTH_LONG).show();
                    return;
            }
        } else
            courseLevel = getText(R.string.GradMajors).toString();

        // Send administrator to the next activity.
        final Intent changeCurriculumIntent = new Intent(view.getContext(), ChangeCurriculumActivity.class);

        changeCurriculumIntent.putExtra(getText(R.string.AccountObject).toString(), administrator);
        changeCurriculumIntent.putExtra(getText(R.string.AdminAction).toString(), adminAction);
        changeCurriculumIntent.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
        changeCurriculumIntent.putExtra(getText(R.string.CourseType).toString(), courseType);
        changeCurriculumIntent.putExtra(getText(R.string.CourseLevel).toString(), courseLevel);
        changeCurriculumIntent.putExtra(getText(R.string.Department).toString(), departmentName);
        view.getContext().startActivity(changeCurriculumIntent);
    }


    private String getPageTitle(final String courseType, final String adminAction) {
        String pageTitle = "Enter ";
        if (courseType.equals(getText(R.string.Undergrad))) {
            pageTitle += "an Undergraduate department to ";
        } else {
            pageTitle += "a Graduate department to ";
        }

        if (adminAction.equals(getText(R.string.AddCourse))) {
            pageTitle += "add courses to.";
        } else {
            pageTitle += "remove courses from.";
        }
        return pageTitle;
    }
}
