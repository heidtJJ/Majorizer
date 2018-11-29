package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.R;

public class ChangeCurriculumActivity extends AppCompatActivity {
    Administrator administrator = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_curriculum);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());
    }

    public void addCourseToCurriculum(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), ChangeCurriculumActivity.class);
        // Pass this Account object to the main activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void removeCourseFromCurriculum(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), ChangeCurriculumActivity.class);
        // Pass this Account object to the main activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
        view.getContext().startActivity(selectAccountActivity);
    }
}
