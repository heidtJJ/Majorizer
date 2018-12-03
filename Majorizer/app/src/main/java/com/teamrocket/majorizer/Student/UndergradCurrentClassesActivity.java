package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.teamrocket.majorizer.Adapters.CurrentClassDataRecycleAdapter;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.List;

public class UndergradCurrentClassesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_current_tile);
        getSupportActionBar().hide();

        // Retrieve needed Textviews to show user information.
        TextView classesTakingView = findViewById(R.id.classesTakingView);
        TextView creditsTakingView = findViewById(R.id.creditsTakingView);

        // Retrieve the Account object passed from the LoginManager.
        Student student = (Student) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        List<Course> classesTakingList = new ArrayList<>();

        // Set all current classes taking in the recycler view.
        RecyclerView cRecyclerView = findViewById(R.id.classesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        for (int i = 0; i < student.numCurCoursesTaking(); ++i)
            classesTakingList.add(student.getCurCourseInformation(i));

        // Set the text for the user's number of courses/classes taking and GPA.
        String circleText = String.valueOf(classesTakingList.size()) + "\ncourses";
        SpannableString ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(classesTakingList.size()).length(), 0);
        classesTakingView.setText(ss);

        circleText = student.getCreditsTaking() + "\ncredits";
        ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(student.getCreditsTaking()).length(), 0);
        creditsTakingView.setText(ss);

        // Create RecyclerView with data.
        RecyclerView.Adapter classAdapter = new CurrentClassDataRecycleAdapter(classesTakingList, getSupportFragmentManager());
        cRecyclerView.setAdapter(classAdapter);
    }
}
