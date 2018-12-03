package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.RequiredCourseListManager;
import com.teamrocket.majorizer.R;

public class UndergradClassesNeededActivity extends AppCompatActivity {
    private Student student = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_remaining_tile);
        getSupportActionBar().hide();

        // Retrieve the Account object passed from the LoginManager and populate classesTaken list.
        student = (Student) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // Set all classes taken in the recycler view.
        RecyclerView classesTakenRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        classesTakenRecyclerView.setLayoutManager(layoutManager);

        TextView classesRemainingView = findViewById(R.id.classesRemainingView);
        TextView creditsRemainingView = findViewById(R.id.creditsRemainingView);
        RequiredCourseListManager masterCourseList = new RequiredCourseListManager(this, classesTakenRecyclerView, student, classesRemainingView, creditsRemainingView, getSupportFragmentManager());
    }
}
