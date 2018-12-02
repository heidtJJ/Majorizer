package com.teamrocket.majorizer.Advisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.teamrocket.majorizer.Adapters.DropAdviseeRecycleAdapter;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.ArrayList;
import java.util.Map;

public class AdvisorDropAdviseeTileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_drop_advisee_tile);
        getSupportActionBar().hide();

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

        RecyclerView.Adapter classAdapter = new DropAdviseeRecycleAdapter(students, getSupportFragmentManager(), this);
        cRecyclerView.setAdapter(classAdapter);
    }
}
