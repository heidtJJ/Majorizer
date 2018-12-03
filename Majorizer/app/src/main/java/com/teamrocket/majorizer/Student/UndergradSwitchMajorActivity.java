package com.teamrocket.majorizer.Student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Adapters.MajorRecycleAdapter;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;

public class UndergradSwitchMajorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_switch_major);
        getSupportActionBar().hide();

        final UndergradStudent student = (UndergradStudent) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());
        final TextView major1View = findViewById(R.id.major1View);

        final ArrayList<String> currentMajors = new ArrayList<>();
        currentMajors.add(student.getMajor1());
        String majorsText;
        if (student.getMajor2() != null) {
            currentMajors.add(student.getMajor2());
            majorsText = "Current majors:\n" + currentMajors.get(0) + "\n" + currentMajors.get(1);
        } else majorsText = "Current major: " + currentMajors.get(0);
        major1View.setText(majorsText);

        final RecyclerView cRecyclerView = findViewById(R.id.majorsRecyclerView);
        final RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        final Context context = this;
        FirebaseDatabase.getInstance().getReference("/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot majorSnapshot = dataSnapshot.child("UndergradMajors");
                Iterable<DataSnapshot> majorSnapshotChildren = majorSnapshot.getChildren();
                ArrayList<String> majors = new ArrayList<>();
                for (DataSnapshot major : majorSnapshotChildren) majors.add(major.getKey());
                final RecyclerView.Adapter classAdapter = new MajorRecycleAdapter(majors, context, currentMajors);
                cRecyclerView.setAdapter(classAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
