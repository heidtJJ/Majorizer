package com.teamrocket.majorizer.Advisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamrocket.majorizer.R;

public class AdvisorSearchStudentsTileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_search_students_tile);
        getSupportActionBar().hide();
    }
}
