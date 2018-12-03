package com.teamrocket.majorizer.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.teamrocket.majorizer.R;

public class CreateStudentAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student_account);
    }

    public void createUndergradStudent(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), CreateUndergradAccountActivity.class);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void createGradStudent(final View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), CreateGradAccountActivity.class);
        view.getContext().startActivity(selectAccountActivity);
    }
}
