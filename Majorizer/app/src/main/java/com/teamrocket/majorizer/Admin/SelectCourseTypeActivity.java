package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.teamrocket.majorizer.R;

public class SelectCourseTypeActivity extends AppCompatActivity {
    private Administrator administrator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_type);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());
    }


    public void createUndergradCourse(final View view) {
        final Intent selectAccountActivity = new Intent(view.getContext(), CreateUndergradCourseActivity.class);
        // Pass this Account object to the next activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void createGradCourse(final View view) {
        final Intent selectAccountActivity = new Intent(view.getContext(), CreateGradCourseActivity.class);
        // Pass this Account object to the next activity.
        selectAccountActivity.putExtra(getText(R.string.AccountObject).toString(), administrator);
        view.getContext().startActivity(selectAccountActivity);
    }

}
