package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamrocket.majorizer.R;

public class ChangeCurriculumActivity extends AppCompatActivity {
    private Administrator administrator = null;
    private String adminAction = null;
    private String adminActionType = null;
    private String studentType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_undergrad_curriculum);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either Curriculum or MasterCourseList.
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());

        // adminActionType is either grad or undergrad.
        studentType = (String) getIntent().getSerializableExtra(getText(R.string.StudentType).toString());
    }
}
