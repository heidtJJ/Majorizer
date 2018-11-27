package com.teamrocket.majorizer.AppUtility;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Student.CourseRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MasterCourseListUtil {
    public final List<Course> masterCourseList = new ArrayList<>();

    public MasterCourseListUtil(final Context context, final RecyclerView classesTakenRecyclerView) {
        final Resources resources = context.getResources();
        // Make asynchronous query to Firebase database fill master course list.
        FirebaseDatabase.getInstance().getReference("/MasterCourseList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot courseList) {
                // Iterate through courseList pulling data for each course.
                for (DataSnapshot course : courseList.getChildren()) {
                    String courseCode = course.getKey();
                    String courseName = course.child("Name").getValue().toString();
                    Integer numCredits = Integer.valueOf(course.child("Credits").getValue().toString());
                    masterCourseList.add(new Course(courseName, courseCode, numCredits));
                }

                RecyclerView.Adapter cAdapter = new CourseRecycleAdapter(masterCourseList);
                classesTakenRecyclerView.setAdapter(cAdapter);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                System.err.println("Could not access database in MasterCourseList");
            }

        });
    }

    public Course getMasterCourseListItem(int index) {
        return masterCourseList.get(index);
    }

    public void addMasterCourseListItem(Course course) {
        masterCourseList.add(course);
    }


}
