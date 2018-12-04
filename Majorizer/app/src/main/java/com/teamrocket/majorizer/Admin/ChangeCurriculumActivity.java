package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Adapters.CourseSearchAdapter;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChangeCurriculumActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    // Data objects from previous activity.
    private Administrator administrator = null;
    private String adminAction = null;
    private String courseType = null;

    // Views in UI
    private SearchView courseSearchView = null;
    private ListView courseListView = null;
    private List<Course> coursesToSearch = new ArrayList<>();
    private CourseSearchAdapter courseSearchViewAdapter = null;
    private Filter filter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_curriculum);

        // Retrieve the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either grad or undergrad.
        courseType = (String) getIntent().getSerializableExtra(getText(R.string.CourseType).toString());

        // Create a search-view adapter object for searching through master course list.
        courseSearchViewAdapter = new CourseSearchAdapter(this, administrator, coursesToSearch, courseType, adminAction);

        // Populate the coursesToSearch list with the master course list.
        populateCoursesToSearch(courseSearchViewAdapter);

        // Retrieve views from UI.
        courseSearchView = findViewById(R.id.coursesSearchView);
        courseListView = findViewById(R.id.coursesRecyclerView);

        courseListView.setAdapter(courseSearchViewAdapter);
        courseListView.setTextFilterEnabled(false);
        courseListView.setDivider(null);
        filter = courseSearchViewAdapter.getFilter();
        filter.filter(null);
        setupSearchView();
    }

    private void populateCoursesToSearch(final CourseSearchAdapter courseSearchAdapter) {
        final String COURSE_NAME = getText(R.string.CourseName).toString();
        final String CREDITS = getText(R.string.Credits).toString();
        final String PRE_REQUISITES = getText(R.string.Prerequisites).toString();
        // Make asynchronous query to Firebase database to fill classes needed in UI.
        FirebaseDatabase.getInstance().getReference("/" + getText(R.string.MasterCourseList).toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot courseList) {
                // Iterate through courseList pulling data for each course.
                for (DataSnapshot course : courseList.getChildren()) {
                    // Pull information from database.
                    String courseCode = course.getKey();
                    String courseName = course.child(COURSE_NAME).getValue().toString();
                    Integer numCredits = Integer.valueOf(course.child(CREDITS).getValue().toString());

                    // Add prerequisite information to each course.
                    Set<Course> preReqs = new HashSet<>();
                    DataSnapshot preReqSnapshot = course.child(PRE_REQUISITES);
                    for (DataSnapshot preRequisite : preReqSnapshot.getChildren()) {
                        String preReqCourseCode = preRequisite.getValue().toString();
                        preReqs.add(new Course(null, preReqCourseCode, 4, new HashSet<Course>()));
                    }

                    coursesToSearch.add(new Course(courseName, courseCode, numCredits, preReqs));
                    courseSearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                System.err.println("Could not access database in MasterCourseList");
            }

        });

    }

    private void setupSearchView() {
        courseSearchView.setIconifiedByDefault(false);
        courseSearchView.setOnQueryTextListener(this);
        courseSearchView.setQueryHint("Search students");
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
