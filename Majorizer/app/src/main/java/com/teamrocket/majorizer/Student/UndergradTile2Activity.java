package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.AppUtility.MasterCourseListManager;
import com.teamrocket.majorizer.UserGroups.Student;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;

public class UndergradTile2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_tile2);


        // Retrieve the Account object passed from the LoginManager and populate classesTaken list.
        Student student = (Student) getIntent().getSerializableExtra("MyClass");
        ArrayList<ClassData> classesTakenList = new ArrayList<>();
        for (int i = 0; i < student.numCoursesTaken(); ++i)
            classesTakenList.add(student.getCourseInformation(i));

        // Set all classes taken in the recycler view.
        RecyclerView classesTakenRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        classesTakenRecyclerView.setLayoutManager(layoutManager);

        // The masterCourseList pulls the master course list from the database and populates the classesTakenRecyclerView based on classes taken.
        MasterCourseListManager masterCourseList = new MasterCourseListManager(this, classesTakenRecyclerView, classesTakenList);

        // Set classesRemaining textview based on list data.
        TextView classesRemainingView = findViewById(R.id.classesRemainingView);
        String circleText = String.valueOf(classesTakenList.size()) + "\ncourses";
        SpannableString ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(classesTakenList.size()).length(), 0);
        classesRemainingView.setText(ss);

        // Set creditsRemaining textview based on list data.
        TextView creditsRemainingView = findViewById(R.id.creditsRemainingView);
        circleText = String.valueOf(classesTakenList.size()) + "\ncredits";
        ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(classesTakenList.size()).length(), 0);
        creditsRemainingView.setText(ss);
    }
}
