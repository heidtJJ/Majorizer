package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.teamrocket.majorizer.Adapters.ClassDataRecycleAdapter;
import com.teamrocket.majorizer.Adapters.CurrentClassDataRecycleAdapter;
import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;

public class UndergradCurrentTileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_current_tile);
        getSupportActionBar().hide();

        // Retrieve needed Textviews to show user information.
        TextView classesTakenView = findViewById(R.id.classesTakingView);
        TextView creditsTakenView = findViewById(R.id.creditsTakingView);

        // Retrieve the Account object passed from the LoginManager.
        Student student = (Student) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        ArrayList<ClassData> classesTakenList = new ArrayList<>();

        // Set all classes taken in the recycler view.
        RecyclerView cRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        for (int i = 0; i < student.numCoursesTaken(); ++i)
            classesTakenList.add(student.getCourseInformation(i));

        // Set the text for the user's number of courses/classes taken and GPA.
        String circleText = String.valueOf(classesTakenList.size()) + "\ncourses";
        SpannableString ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(classesTakenList.size()).length(), 0);
        classesTakenView.setText(ss);

        circleText = student.getCreditsTaken() + "\ncredits";
        ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(student.getCreditsTaken()).length(), 0);
        creditsTakenView.setText(ss);

        // Create RecyclerView with data.
        RecyclerView.Adapter classAdapter = new CurrentClassDataRecycleAdapter(classesTakenList, getSupportFragmentManager());
        cRecyclerView.setAdapter(classAdapter);
    }
}
