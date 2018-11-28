package com.teamrocket.majorizer.AppUtility;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Adapters.CourseRecycleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseListManager {
    public final List<Course> masterCourseList = new ArrayList<>();
    int courseCount = 0, creditsCount = 0;
    public CourseListManager(final Context context, final RecyclerView classesTakenRecyclerView,
                             final ArrayList<ClassData> classesTakenList, final TextView coursesRemainingView, final TextView creditsRemainingView) {
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
                    if (!checkCourseCode(courseCode, classesTakenList)) {
                        masterCourseList.add(new Course(courseName, courseCode, numCredits));
                        courseCount++;
                        creditsCount += numCredits;
                    }
                }

                RecyclerView.Adapter cAdapter = new CourseRecycleAdapter(masterCourseList);
                classesTakenRecyclerView.setAdapter(cAdapter);

                String circleText = String.valueOf(courseCount) + "\ncourses";
                SpannableString ss = new SpannableString(circleText);
                ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(courseCount).length(), 0);
                coursesRemainingView.setText(ss);

                circleText = String.valueOf(creditsCount) + "\ncredits";
                ss = new SpannableString(circleText);
                ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(creditsCount).length(), 0);
                creditsRemainingView.setText(ss);
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

    public boolean checkCourseCode(String code, ArrayList<ClassData> classesTaken) {
        for (ClassData cd : classesTaken) if (cd.getCourseCode().equals(code)) return true;
        return false;
    }
}
