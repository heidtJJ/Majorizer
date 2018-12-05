package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.R;

public class SelectCourseTypeActivity extends AppCompatActivity {
    private Administrator administrator = null;
    private String adminAction = null;
    private String adminActionType = null;
    private TextView activityHeader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_type);

        activityHeader = findViewById(R.id.activityHeader);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either Curriculum or MasterCourseList.
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());

        String header = "Select course type to ";
        if (adminAction.equals(getText(R.string.AddCourse)))
            header += "add";
        else
            header += "remove";
        activityHeader.setText(header);
    }


    // User clicked the "Undergrad" button
    public void undergradSelection(final View view) {
        // Check if user wants to add a course to the master course list.
        if (adminActionType.equals(getText(R.string.ChangeMasterCourseList)) && adminAction.equals(getText(R.string.AddCourse).toString())) {

            // Go to next activity to add an undergraduate course to master course list.
            final Intent createUndergradCourseIntent = new Intent(view.getContext(), CreateUndergradCourseActivity.class);

            // Send administrator to the next activity.
            createUndergradCourseIntent.putExtra(getText(R.string.AccountObject).toString(), administrator);
            view.getContext().startActivity(createUndergradCourseIntent);

        } else {
            // User wants to change curriculum for undergraduate program.
            final Intent selectAccountIntent = new Intent(view.getContext(), DepartmentSelectionActivity.class);

            // Send administrator to the next activity.
            selectAccountIntent.putExtra(getText(R.string.AccountObject).toString(), administrator);
            selectAccountIntent.putExtra(getText(R.string.AdminAction).toString(), adminAction);
            selectAccountIntent.putExtra(getText(R.string.CourseType).toString(), getText(R.string.Undergrad));
            selectAccountIntent.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
            view.getContext().startActivity(selectAccountIntent);
        }
    }

    // User clicked the "Graduate" button
    public void graduateSelection(final View view) {
        // Check if making change to master course list or a curriculum.
        if (adminActionType.equals(getText(R.string.ChangeMasterCourseList))) {
            // Go to next activity to add a graduate course to master course list.
            final Intent createGradCourseIntent = new Intent(view.getContext(), CreateGradCourseActivity.class);
            // Send administrator to the next activity.
            createGradCourseIntent.putExtra(getText(R.string.AccountObject).toString(), administrator);
            view.getContext().startActivity(createGradCourseIntent);
        } else {
            // Change curriculum for graduate program.
            final Intent departmentSelectionIntent = new Intent(view.getContext(), DepartmentSelectionActivity.class);
            // Send administrator to the next activity.
            departmentSelectionIntent.putExtra(getText(R.string.AccountObject).toString(), administrator);
            departmentSelectionIntent.putExtra(getText(R.string.AdminAction).toString(), adminAction);
            departmentSelectionIntent.putExtra(getText(R.string.CourseType).toString(), getText(R.string.Grad));
            departmentSelectionIntent.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
            view.getContext().startActivity(departmentSelectionIntent);
        }
    }

}
