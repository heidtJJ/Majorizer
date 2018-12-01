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
import java.util.ArrayList;
import java.util.Map;

public class AdvisorMyAdviseesTileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_my_advisees_tile);
        getSupportActionBar().hide();

        TextView numberAdviseesView = findViewById(R.id.numberAdviseesView);
        TextView averageAdviseeGpaView = findViewById(R.id.averageAdviseeGpaView);

        Advisor advisor = (Advisor) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        Map<String, String> studentsMap = advisor.getStudents();
        if (studentsMap.isEmpty()) { // when getStudents() works will not need to populate
            studentsMap.put("heidtjj", "Jared Heidt");
            studentsMap.put("dewejm", "Jeremy Dewey");
            studentsMap.put("shawah", "Andrew Shaw");
        }

        ArrayList<Student> students = new ArrayList<>();

        for (Map.Entry<String,String> student : studentsMap.entrySet()) {
            Student s = new Student();
            s.setUserName(student.getKey());
            String name = student.getValue();
            s.setFirstName(name.substring(0, name.indexOf(" ")));
            s.setLastName(name.substring(name.indexOf(" ") + 1, name.length()));
            students.add(s);
        }

        // Set all classes taken in the recycler view.
        RecyclerView cRecyclerView = findViewById(R.id.adviseesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        RecyclerView.Adapter classAdapter = new AdviseeRecycleAdapter(students, getSupportFragmentManager());
        cRecyclerView.setAdapter(classAdapter);

        String circleText = String.valueOf(4) + "\nadvisees";
        SpannableString ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(4).length(), 0);
        numberAdviseesView.setText(ss);

        circleText = String.valueOf(3.14) + "\navg GPA";
        ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(3.14).length(), 0);
        averageAdviseeGpaView.setText(ss);
    }
}
