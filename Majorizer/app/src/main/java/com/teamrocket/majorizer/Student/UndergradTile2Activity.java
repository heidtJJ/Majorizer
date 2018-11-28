package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.MasterCourseListManager;
import com.teamrocket.majorizer.UserGroups.Student;
import com.teamrocket.majorizer.R;

public class UndergradTile2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_tile2);

        // Retrieve needed Textviews to show user information.
        TextView classesRemainingView = findViewById(R.id.classesRemainingView);

        // Retrieve the Account object passed from the LoginManager.
        Student student = (Student) getIntent().getSerializableExtra("MyClass");

        // Set all classes taken in the recycler view.
        RecyclerView classesTakenRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        classesTakenRecyclerView.setLayoutManager(layoutManager);

        // The masterCourseList pulls the master course list from the database and populates the classesTakenRecyclerView.
        MasterCourseListManager masterCourseList = new MasterCourseListManager(this, classesTakenRecyclerView);

        classesRemainingView.setText(String.valueOf(student.numCoursesTaken()));
    }
}
