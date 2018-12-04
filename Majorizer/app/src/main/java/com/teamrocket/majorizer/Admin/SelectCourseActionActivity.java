package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamrocket.majorizer.R;

public class SelectCourseActionActivity extends AppCompatActivity {
    private Administrator administrator = null;
    private String adminActionType = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_curriculum_course_action);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminActionType is either curriculum or master course list.
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());
    }

    public void addCourseToCurriculum(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), SelectCourseTypeActivity.class);

        // Send administrator to the next activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);

        // Pass two other variables to next activity to determine next steps.
        selectAccountActivity.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
        selectAccountActivity.putExtra(getText(R.string.AdminAction).toString(), getText(R.string.AddCourse));

        view.getContext().startActivity(selectAccountActivity);
    }

    public void removeCourseFromCurriculum(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), SelectCourseTypeActivity.class);

        // Pass this Account object to the main activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);

        // Pass two other variables to next activity to determine next steps.
        selectAccountActivity.putExtra(getText(R.string.AdminActionType).toString(), adminActionType);
        selectAccountActivity.putExtra(getText(R.string.AdminAction).toString(), getText(R.string.RemoveCourse));

        view.getContext().startActivity(selectAccountActivity);
    }
}
