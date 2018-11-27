package com.teamrocket.majorizer.AppUtility;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterCourseList {
    private final List<Course> masterCourseList = new ArrayList<>();

    public MasterCourseList(final Context context) {
        final Resources resources = context.getResources();
        // Make query to Firebase database fill master course list
        FirebaseDatabase.getInstance().getReference("/MasterCourseList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot courseList) {
                for (DataSnapshot course : courseList.getChildren()) {
                    String courseCode = course.getKey();
                    String courseName = course.child("Name").getValue().toString();
                    Integer numCredits = Integer.valueOf(course.child("Credits").getValue().toString());
                    masterCourseList.add(new Course(courseCode, courseName, numCredits));
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                System.err.println("Could not access database in MasterCourseList");
            }

        });
    }

    public List<Course> getMasterCourseList() {
        return masterCourseList;
    }
}
