package com.teamrocket.majorizer.Advisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.teamrocket.majorizer.Adapters.AdviseeRecycleAdapter;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvisorMyAdviseesTileActivity extends AppCompatActivity {
    private Advisor advisor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_my_advisees_tile);
        getSupportActionBar().hide();

        TextView numberAdviseesView = findViewById(R.id.numberAdviseesView);

        advisor = (Advisor) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        final Map<String, Student> studentsMap = advisor.getStudents();

        final List<Student> students = new ArrayList<>();

        for (Map.Entry<String, Student> student : studentsMap.entrySet()) {
            Student advisee = student.getValue();
            students.add(advisee);
        }

        // Set all classes taken in the recycler view.
        RecyclerView cRecyclerView = findViewById(R.id.adviseesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        RecyclerView.Adapter classAdapter = new AdviseeRecycleAdapter(students, getSupportFragmentManager(), advisor);
        cRecyclerView.setAdapter(classAdapter);

        String circleText = advisor.getNumAdvisees() + "\nadvisees";
        SpannableString ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(4).length(), 0);
        numberAdviseesView.setText(ss);
    }
}
