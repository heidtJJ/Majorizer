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

public class UndergradTile1Activity extends AppCompatActivity {
    private RecyclerView cRecyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager cLayoutManager;
    private ArrayList<ClassData> userClassList = new ArrayList<>();
    private Student student = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_tile1);

        // Retrieve needed Textviews to show user information.
        TextView classesTakenView = findViewById(R.id.classesTakenView);
        TextView creditsTakenView = findViewById(R.id.creditsTakenView);
        TextView gpaView = findViewById(R.id.gpaView);

        // Retreive the Account object passed from the LoginManager.
        student = (Student) getIntent().getSerializableExtra("MyClass");

        // Set the text for the user's GPA, number of credits taken, and classes taken.
        classesTakenView.setText(String.valueOf(userClassList.size()));
        gpaView.setText(student.getGPA());
        creditsTakenView.setText(String.valueOf(student.getCreditsTaken()));

        // Set all classes taken in the recycler view.
        cRecyclerView = findViewById(R.id.classesRecyclerView);
        cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        for (int i = 0; i < student.numCoursesTaken(); ++i)
            userClassList.add(student.getCourseInformation(i));

        cAdapter = new ClassRecycleAdapter(userClassList);
        cRecyclerView.setAdapter(cAdapter);

    }
}
