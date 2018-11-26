package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Student;

import java.util.ArrayList;

public class UndergradTile2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_tile2);

        // Retrieve needed Textviews to show user information.
        TextView classesRemainingView = findViewById(R.id.classesRemainingView);
        TextView creditsRemaingingView = findViewById(R.id.creditsRemainingView);

        // Retrieve the Account object passed from the LoginManager.
        Student student = (Student) getIntent().getSerializableExtra("MyClass");

        ArrayList<ClassData> userClassList = new ArrayList<>();

        // Set all classes taken in the recycler view.
        RecyclerView cRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        for (int i = 0; i < student.numCoursesTaken(); ++i) userClassList.add(student.getCourseInformation(i));

        RecyclerView.Adapter cAdapter = new ClassRecycleAdapter(userClassList);
        cRecyclerView.setAdapter(cAdapter);

        classesRemainingView.setText(String.valueOf(userClassList.size()));
    }
}
