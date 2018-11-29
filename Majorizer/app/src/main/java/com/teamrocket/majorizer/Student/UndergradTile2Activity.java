package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.RequiredCourseListManager;
import com.teamrocket.majorizer.R;

public class UndergradTile2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_tile2);
        getSupportActionBar().hide();

        // Retrieve the Account object passed from the LoginManager and populate classesTaken list.
        Student student = (Student) getIntent().getSerializableExtra("MyClass");

        // Set all classes taken in the recycler view.
        RecyclerView classesTakenRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        classesTakenRecyclerView.setLayoutManager(layoutManager);

        TextView classesRemainingView = findViewById(R.id.classesRemainingView);
        TextView creditsRemainingView = findViewById(R.id.creditsRemainingView);
        RequiredCourseListManager masterCourseList = new RequiredCourseListManager(this, classesTakenRecyclerView, student, classesRemainingView, creditsRemainingView);
    }
}
