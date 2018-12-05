package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.core.view.Change;
import com.teamrocket.majorizer.R;

public class SelectCourseActionActivity extends AppCompatActivity {
    private Administrator administrator = null;
    private String adminActionType = null;
    private final String MASTER_COURSE_LIST = "master course list";
    private final String CURRICULUM = "curriculum";

    private TextView addCourseTextView = null;
    private TextView removeCourseTextView = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_curriculum_course_action);

        addCourseTextView = findViewById(R.id.addCourseTextView);
        removeCourseTextView = findViewById(R.id.removeCourseTextView);

        // Retrieve the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminActionType is either curriculum or master course list.
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());

        String addCourseTitle = "Add course to ";
        String removeCourseTitle = "Remove course from ";
        if (adminActionType.equals(getText(R.string.ChangeMasterCourseList))) {
            addCourseTitle += "the " + MASTER_COURSE_LIST;
            removeCourseTitle += "the " + MASTER_COURSE_LIST;
        } else {
            addCourseTitle += "a " + CURRICULUM;
            removeCourseTitle += "a " + CURRICULUM;
        }
        addCourseTextView.setText(addCourseTitle);
        removeCourseTextView.setText(removeCourseTitle);
    }

    public void addCourse(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), SelectCourseTypeActivity.class);

        // Send administrator to the next activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);

        // Pass two other variables to next activity to determine next steps.
        selectAccountActivity.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
        selectAccountActivity.putExtra(getText(R.string.AdminAction).toString(), getText(R.string.AddCourse));

        view.getContext().startActivity(selectAccountActivity);
    }

    public void removeCourse(final View view) {

        if (adminActionType.equals(getText(R.string.ChangeMasterCourseList))) {
            // User wants to remove a class from master course list.
            // Don't need to ask them if it's an undergrad or grad course.
            Intent changeCurriculumIntent = new Intent(view.getContext(), ChangeCurriculumActivity.class);

            // Pass this Account object to the main activity.
            changeCurriculumIntent.putExtra(getText(R.string.AccountObject).toString(), administrator);

            // Pass two other variables to next activity to determine next steps.
            changeCurriculumIntent.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
            changeCurriculumIntent.putExtra(getText(R.string.AdminAction).toString(), getText(R.string.RemoveCourse));

            view.getContext().startActivity(changeCurriculumIntent);
        } else {
            // User wants to remove a class from a curriculum.
            Intent selectCourseTypeIntent = new Intent(view.getContext(), SelectCourseTypeActivity.class);

            // Pass this Account object to the main activity.
            selectCourseTypeIntent.putExtra(getText(R.string.AccountObject).toString(), administrator);

            // Pass two other variables to next activity to determine next steps.
            selectCourseTypeIntent.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
            selectCourseTypeIntent.putExtra(getText(R.string.AdminAction).toString(), getText(R.string.RemoveCourse));

            view.getContext().startActivity(selectCourseTypeIntent);
        }
    }
}
