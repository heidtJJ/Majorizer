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
    private String adminActionType = null;
    private String courseType = null;
    private String courseLevel = null;
    private String departmentName = null;

    // Views in UI
    private SearchView courseSearchView = null;
    private ListView courseListView = null;
    private List<Course> coursesToSearch = new ArrayList<>();
    private CourseSearchAdapter courseSearchViewAdapter = null;
    private Filter filter = null;
    private String searchHint = "Search for ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_curriculum);

        // Retrieve the Account object passed from the LoginManager.
        administrator = (Administrator) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // adminAction is either add course or drop course.
        adminAction = (String) getIntent().getSerializableExtra(getText(R.string.AdminAction).toString());

        // adminActionType is either MasterCourseList or Curriculum
        adminActionType = (String) getIntent().getSerializableExtra(getText(R.string.AdminActionType).toString());

        // Course level is where in the database to insert/remove this data.
        courseLevel = (String) getIntent().getSerializableExtra(getText(R.string.CourseLevel).toString());

        // Department name is name of department.
        departmentName = (String) getIntent().getSerializableExtra(getText(R.string.Department).toString());

        // courseType is either grad or undergrad. This is null if user wants to remove a master course.
        courseType = (String) getIntent().getSerializableExtra(getText(R.string.CourseType).toString());

        if (courseType == null)
            searchHint += "an course";
        else if (courseLevel != null && courseType.equals(getText(R.string.Undergrad).toString()))
            searchHint += "an Undergraduate course";
        else
            searchHint += "a Graduate course";

        // Create a search-view adapter object for searching through master course list.
        courseSearchViewAdapter = new CourseSearchAdapter(this, administrator, coursesToSearch, courseType, adminAction, courseLevel, departmentName);

        // Populate the coursesToSearch list with the correct courses. Construct the proper URL first.
        String URLinDatabase = null;
        if (adminActionType.equals(getText(R.string.ChangeMasterCourseList)) ||
                (adminActionType.equals(getText(R.string.ChangeCurriculum)) && adminAction.equals(getText(R.string.AddCourse))))
            URLinDatabase = "/" + getText(R.string.MasterCourseList).toString();
        else
            URLinDatabase = "/" + courseLevel + "/" + departmentName;
        administrator.populateCoursesForSearch(this, coursesToSearch, courseSearchViewAdapter, URLinDatabase);

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

    private void setupSearchView() {
        courseSearchView.setIconifiedByDefault(false);
        courseSearchView.setOnQueryTextListener(this);
        courseSearchView.setQueryHint(searchHint);
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
