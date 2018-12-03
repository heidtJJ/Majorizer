package com.teamrocket.majorizer.Advisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.teamrocket.majorizer.Adapters.StudentSearchAdapter;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.ArrayList;
import java.util.List;

public class AdviserSearchStudentsTileActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView adviseeSearchView = null;
    private ListView adviseeListView = null;
    private List<Student> studentsToSearch = new ArrayList<>();
    private StudentSearchAdapter adviseeSearchViewAdapter = null;
    private Filter filter = null;
    private Advisor advisor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_search_students_tile);
        getSupportActionBar().hide();

        // Retrieve Advisor from previous activity.
        advisor = (Advisor) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        // Retrieve views from UI.
        adviseeSearchView = findViewById(R.id.adviseeSearchView);
        adviseeListView = findViewById(R.id.adviseesRecyclerView);

        adviseeSearchViewAdapter = new StudentSearchAdapter(this, advisor, studentsToSearch);
        adviseeListView.setAdapter(adviseeSearchViewAdapter);
        adviseeListView.setTextFilterEnabled(false);
        adviseeListView.setDivider(null);
        filter = adviseeSearchViewAdapter.getFilter();
        filter.filter(null);
        setupSearchView();

        advisor.getSearchableAccounts(studentsToSearch, adviseeSearchViewAdapter, this);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(getText(R.string.AccountObject).toString(), advisor);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra(getText(R.string.AccountObject).toString(), advisor);
        setResult(RESULT_OK, intent);
        finish();
        super.onDestroy();
    }
}
