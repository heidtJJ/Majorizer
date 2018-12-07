package com.teamrocket.majorizer.Student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Adapters.MRecycleAdapter;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;

public class UndergradSwitchMajorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_switch_major);
        getSupportActionBar().hide();

        final UndergradStudent student = (UndergradStudent) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());
        final TextView major1View = findViewById(R.id.majorView);

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
                DataSnapshot advisorUserName = dataSnapshot.child("Accounts/" + student.getUserName() + "/Advisors");
                Iterable<DataSnapshot> advisorSnapshotChildren = advisorUserName.getChildren();
                ArrayList<String> advisors = new ArrayList<>();
                for (DataSnapshot advisor : advisorSnapshotChildren) advisors.add(advisor.getKey());
                String advisor = advisors.get(0);
                final RecyclerView.Adapter classAdapter = new MRecycleAdapter(majors, context, currentMajors, "Major", student, advisor);
                cRecyclerView.setAdapter(classAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TextView minorView = findViewById(R.id.minorView);

        final ArrayList<String> currentMinors = new ArrayList<>();
        String minorText = "";
        if (student.getMinor1() != null) {
            currentMinors.add(student.getMinor1());
            minorText = "Current minor: " + currentMinors.get(0);
        }
        else minorText = "No enrolled minors.";
        System.out.println("M1: " + student.getMinor1());
        System.out.println("M2: " + student.getMinor2());
        if (student.getMinor2() != null) {
            currentMinors.add(student.getMinor2());
            minorText = "Current minors:\n" + currentMinors.get(0) + "\n" + currentMinors.get(1);
        }
        minorView.setText(minorText);

        final RecyclerView cRecyclerViewM = findViewById(R.id.minorRecycleView);
        final RecyclerView.LayoutManager cLayoutManagerM = new LinearLayoutManager(this);
        cRecyclerViewM.setLayoutManager(cLayoutManagerM);

        FirebaseDatabase.getInstance().getReference("/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot minorSnapshot = dataSnapshot.child("Minors");
                Iterable<DataSnapshot> minorSnapshotChildren = minorSnapshot.getChildren();
                ArrayList<String> minors = new ArrayList<>();
                for (DataSnapshot minor : minorSnapshotChildren) minors.add(minor.getKey());
                DataSnapshot advisorUserName = dataSnapshot.child("Accounts/" + student.getUserName() + "/Advisors");
                Iterable<DataSnapshot> advisorSnapshotChildren = advisorUserName.getChildren();
                ArrayList<String> advisors = new ArrayList<>();
                for (DataSnapshot advisor : advisorSnapshotChildren) advisors.add(advisor.getKey());
                String advisor = advisors.get(0);
                final RecyclerView.Adapter classAdapter = new MRecycleAdapter(minors, context, currentMinors, "Minor", student, advisor);
                cRecyclerViewM.setAdapter(classAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
