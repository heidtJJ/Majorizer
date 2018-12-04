package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.teamrocket.majorizer.Adapters.StudentSearchAdapter;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.ArrayList;
import java.util.List;

public class ChangeCurriculumActivity extends AppCompatActivity {
    // Data objects from previous activity.
    private Administrator administrator = null;
    private String adminAction = null;
    private String adminActionType = null;
    private String studentType = null;

    // Views in UI
    private SearchView courseSearchView = null;
    private ListView courseListView = null;
    private List<Course> coursesToSearch = new ArrayList<>();
    private StudentSearchAdapter courseSearchViewAdapter = null;
    private Filter filter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_curriculum);

        // Retreive the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either Curriculum or MasterCourseList.
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());

        // adminActionType is either grad or undergrad.
        studentType = (String) getIntent().getSerializableExtra(getText(R.string.StudentType).toString());
    }
}
