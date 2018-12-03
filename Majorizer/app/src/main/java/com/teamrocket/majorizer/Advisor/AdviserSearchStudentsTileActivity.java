package com.teamrocket.majorizer.Advisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.teamrocket.majorizer.Adapters.AdviseeSearchAdapter;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.util.ArrayList;
import java.util.Map;

public class AdviserSearchStudentsTileActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView adviseeSearchView;
    ListView adviseeListView;
    ArrayList<Student> students;
    AdviseeSearchAdapter adviseeSearchViewAdapter;
    Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_search_students_tile);
        getSupportActionBar().hide();

        Advisor advisor = (Advisor) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        students = new ArrayList<>();
        Map<String, Student> studentsMap = advisor.getStudents(); // will need to get all students

        for (Map.Entry<String, Student> student : studentsMap.entrySet()) {
            Student advisee = student.getValue();
            students.add(advisee);
        }

        adviseeSearchView = findViewById(R.id.adviseeSearchView);
        adviseeListView = findViewById(R.id.adviseesRecyclerView);

        adviseeSearchViewAdapter = new AdviseeSearchAdapter(this, students);
        adviseeListView.setAdapter(adviseeSearchViewAdapter);
        adviseeListView.setTextFilterEnabled(false);
        adviseeListView.setDivider(null);
        filter = adviseeSearchViewAdapter.getFilter();
        filter.filter(null);
        setupSearchView();
    }

    private void setupSearchView() {
        adviseeSearchView.setIconifiedByDefault(false);
        adviseeSearchView.setOnQueryTextListener(this);
        adviseeSearchView.setQueryHint("Search students");
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            filter.filter(null);
        } else {
            filter.filter(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
