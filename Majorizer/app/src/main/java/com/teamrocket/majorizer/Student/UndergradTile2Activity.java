package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.AppUtility.MasterCourseList;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Student;

import java.util.ArrayList;

public class UndergradTile2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_tile2);

        MasterCourseList masterCourseList = new MasterCourseList(this);

        // Retrieve needed Textviews to show user information.
        TextView classesRemainingView = findViewById(R.id.classesRemainingView);

        // Retrieve the Account object passed from the LoginManager.
        Student student = (Student) getIntent().getSerializableExtra("MyClass");

        // Create a list for the list of classes taken.
        ArrayList<ClassData> classesTakenList = new ArrayList<>();

        // Set all classes taken in the recycler view.
        RecyclerView cRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        for (int i = 0; i < student.numCoursesTaken(); ++i)
            classesTakenList.add(student.getCourseInformation(i));

        RecyclerView.Adapter cAdapter = new ClassRecycleAdapter(classesTakenList);
        cRecyclerView.setAdapter(cAdapter);

        classesRemainingView.setText(String.valueOf(classesTakenList.size()));
    }
}
