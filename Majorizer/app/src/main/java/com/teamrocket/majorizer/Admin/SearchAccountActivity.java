package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.teamrocket.majorizer.Adapters.AdviseeSearchAdapter;
import com.teamrocket.majorizer.Advisor.Advisor;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchAccountActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

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

        Administrator administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        students = new ArrayList<>();
        Map<String, String> studentsMap = new HashMap<>();
        studentsMap.put("heidtjj", "Jared Heidt");
        studentsMap.put("dewejm", "Jeremy Dewey");
        studentsMap.put("shawah", "Andrew Shaw");
        studentsMap.put("gilletj", "Josh Gillette");
        studentsMap.put("fawlsT", "Tim Fawls");
        studentsMap.put("pyszcC", "Colton Pyszczysnki");
        studentsMap.put("kubiS", "Stanley Kubis");
        studentsMap.put("smitJ", "John Smith");
        studentsMap.put("cruzB", "Bill Cruz");
        studentsMap.put("marseS", "Sean Mars");
        studentsMap.put("charleR", "Ray Charles");
        studentsMap.put("obamahB", "Barrack Obama");
        studentsMap.put("wayneL", "Lil Wayne");

        for (Map.Entry<String, String> student : studentsMap.entrySet()) {
            Student s = new UndergradStudent();
            s.setUserName(student.getKey());
            String name = student.getValue();
            s.setFirstName(name.substring(0, name.indexOf(" ")));
            s.setLastName(name.substring(name.indexOf(" ") + 1, name.length()));
            students.add(s);
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
