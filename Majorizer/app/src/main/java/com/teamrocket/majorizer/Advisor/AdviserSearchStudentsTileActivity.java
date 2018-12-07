package com.teamrocket.majorizer.Advisor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.teamrocket.majorizer.Adapters.StudentSearchAdapter;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        adviseeSearchView = findViewById(R.id.studentsSearchView);
        adviseeListView = findViewById(R.id.studentsRecyclerView);

        Iterator it = advisor.getStudents().entrySet().iterator();
        final ArrayList<String> currentStudents = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            currentStudents.add((((UndergradStudent) pair.getValue()).getLastName()) + " " + ((UndergradStudent) pair.getValue()).getFirstName());
        }
        System.out.println("Fsf " +  currentStudents);
        setupSearchView();

        final Context context = this;
        FirebaseDatabase.getInstance().getReference("/Accounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> accounts = new HashMap<>();
                for (DataSnapshot acccount : dataSnapshot.getChildren()) {
                    String name = "";
                    for (DataSnapshot accountInfo : acccount.getChildren()) {
                        if (accountInfo.getKey().equals("FirstName")) {
                            name = (String) accountInfo.getValue();
                        }
                        else if (accountInfo.getKey().equals("LastName")) {
                            name += " " + accountInfo.getValue();
                        }
                        else if (accountInfo.getKey().equals("Type")) {
                            if (accountInfo.getValue().equals("undergrad")) {
                                accounts.put(acccount.getKey(), name);
                            }
                        }
                    }
                }
                ArrayList<Student> students = new ArrayList<>();
                for (Map.Entry<String, String> entry : accounts.entrySet()) {
                    UndergradStudent student = new UndergradStudent();
                    student.setUserName(entry.getKey());
                    student.setFirstName(entry.getValue().substring(0, entry.getValue().indexOf(" ")));
                    student.setLastName(entry.getValue().substring(entry.getValue().indexOf(" ") + 1, entry.getValue().length()));
                    boolean already = false;
                    for (String currentAdvisee : currentStudents) {
                        if (currentAdvisee.toLowerCase().contains(student.getLastName().toLowerCase()) &&
                                currentAdvisee.toLowerCase().contains(student.getFirstName().toLowerCase())) already = true;
                        else already = false;
                    }
                    if (!already) students.add(student);
                }
                adviseeSearchViewAdapter = new StudentSearchAdapter(context, advisor, students);
                adviseeListView.setAdapter(adviseeSearchViewAdapter);
                adviseeListView.setTextFilterEnabled(false);
                adviseeListView.setDivider(null);
                filter = adviseeSearchViewAdapter.getFilter();
                filter.filter(null);

                advisor.getSearchableAccounts(studentsToSearch, adviseeSearchViewAdapter, context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
