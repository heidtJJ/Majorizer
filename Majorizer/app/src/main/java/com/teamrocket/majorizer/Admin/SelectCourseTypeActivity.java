package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.teamrocket.majorizer.R;

public class SelectCourseTypeActivity extends AppCompatActivity {
    private Administrator administrator = null;
    private String adminAction = null;
    private String adminActionType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_type);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either Curriculum or MasterCourseList.
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());
    }


    // User clicked the "Undergrad" button
    public void undergradSelection(final View view) {
        // Check if making change to master course list or a curriculum.
        if (adminActionType.equals(getText(R.string.ChangeMasterCourseList)) && adminAction.equals(getText(R.string.AddCourse).toString())) {

            // Go to next activity to add an undergraduate course to master course list.
            final Intent selectAccountActivity = new Intent(view.getContext(), CreateUndergradCourseActivity.class);

            // Send administrator to the next activity.
            selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
            view.getContext().startActivity(selectAccountActivity);

        } else {
            // Change curriculum for undergraduate program.
            final Intent selectAccountActivity = new Intent(view.getContext(), DepartmentSelectionActivity.class);

            // Send administrator to the next activity.
            selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
            selectAccountActivity.putExtra(getText(R.string.AdminAction).toString(), adminAction);
            selectAccountActivity.putExtra(getText(R.string.CourseType).toString(), getText(R.string.Undergrad));
            view.getContext().startActivity(selectAccountActivity);

        }
    }

    // User clicked the "Graduate" button
    public void graduateSelection(final View view) {
        // Check if making change to master course list or a curriculum.
        if (adminActionType.equals(getText(R.string.ChangeMasterCourseList))) {
            // Go to next activity to add a graduate course to master course list.
            final Intent selectAccountActivity = new Intent(view.getContext(), CreateGradCourseActivity.class);
            // Send administrator to the next activity.
            selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
            view.getContext().startActivity(selectAccountActivity);
        } else {
            // Change curriculum for graduate program.
            final Intent selectAccountActivity = new Intent(view.getContext(), DepartmentSelectionActivity.class);

            // Send administrator to the next activity.
            selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
            selectAccountActivity.putExtra(getText(R.string.AdminAction).toString(), adminAction);
            selectAccountActivity.putExtra(getText(R.string.CourseType).toString(), getText(R.string.Grad));
            view.getContext().startActivity(selectAccountActivity);
        }
    }

}
