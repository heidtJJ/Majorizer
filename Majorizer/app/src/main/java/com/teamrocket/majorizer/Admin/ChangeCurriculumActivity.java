package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.R;

public class ChangeCurriculumActivity extends AppCompatActivity {
    Administrator administrator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_curriculum);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra("MyClass");

    }
}
