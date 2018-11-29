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
import com.teamrocket.majorizer.UserGroups.GradStudent;
import com.teamrocket.majorizer.UserGroups.Student;
import com.teamrocket.majorizer.UserGroups.UndergradStudent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequiredCourseListManager {
    public final List<Course> classesNeededList = new ArrayList<>();
    int courseCount = 0, creditsCount = 0;

    public RequiredCourseListManager(final Context context, final RecyclerView classesTakenRecyclerView,
                                     final Student student, final TextView coursesRemainingView, final TextView creditsRemainingView) {

        // Retrieve the list of classes already taken.
        final List<ClassData> classesTakenList = getCoursesTaken(student);

        // Get list of the student's major and minors
        Set<String> majors = getStudentMajors(student);

        final Lock mutexLock = new ReentrantLock();

        // Iterate through list of majors finding needed classes.
        for (final String major : majors) {
            // Make asynchronous query to Firebase database to fill classesNeededList.
            FirebaseDatabase.getInstance().getReference("/Majors/" + major).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot courseList) {
                    // Iterate through courseList pulling data for each course.
                    for (DataSnapshot course : courseList.getChildren()) {
                        String courseCode = course.getKey();
                        String courseName = course.child("Name").getValue().toString();
                        Integer numCredits = Integer.valueOf(course.child("Credits").getValue().toString());
                        if (!classesTakenList.contains(courseCode)) {
                            mutexLock.lock();
                            classesNeededList.add(new Course(courseName, courseCode, numCredits));
                            courseCount++;
                            creditsCount += numCredits;
                            mutexLock.unlock();
                        }
                    }

                    RecyclerView.Adapter cAdapter = new CourseRecycleAdapter(classesNeededList);
                    classesTakenRecyclerView.setAdapter(cAdapter);

                    mutexLock.lock();
                    String circleText = String.valueOf(courseCount) + "\ncourses";
                    SpannableString ss = new SpannableString(circleText);
                    ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(courseCount).length(), 0);
                    coursesRemainingView.setText(ss);

                    circleText = String.valueOf(creditsCount) + "\ncredits";
                    ss = new SpannableString(circleText);
                    ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(creditsCount).length(), 0);
                    creditsRemainingView.setText(ss);
                    mutexLock.unlock();
                }

                @Override
                public void onCancelled(final DatabaseError databaseError) {
                    // Database query was not successful.
                    System.err.println("Could not access database in MasterCourseList");
                }

            });
        }

        // Get list of the student's major and minors
        Set<String> minors = getStudentMinors(student);

        // Iterate through list of majors finding needed classes.
        for (final String minor : minors) {
            // Make asynchronous query to Firebase database to fill classesNeededList.
            FirebaseDatabase.getInstance().getReference("/Minors/" + minor).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot courseList) {
                    // Iterate through courseList pulling data for each course.
                    for (DataSnapshot course : courseList.getChildren()) {
                        String courseCode = course.getKey();
                        String courseName = course.child("Name").getValue().toString();
                        Integer numCredits = Integer.valueOf(course.child("Credits").getValue().toString());
                        if (!classesTakenList.contains(courseCode)) {
                            mutexLock.lock();
                            classesNeededList.add(new Course(courseName, courseCode, numCredits));
                            courseCount++;
                            creditsCount += numCredits;
                            mutexLock.unlock();
                        }
                    }

                    RecyclerView.Adapter cAdapter = new CourseRecycleAdapter(classesNeededList);
                    classesTakenRecyclerView.setAdapter(cAdapter);

                    mutexLock.lock();
                    String circleText = String.valueOf(courseCount) + "\ncourses";
                    SpannableString ss = new SpannableString(circleText);
                    ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(courseCount).length(), 0);
                    coursesRemainingView.setText(ss);
                    mutexLock.unlock();

                    mutexLock.lock();
                    circleText = String.valueOf(creditsCount) + "\ncredits";
                    ss = new SpannableString(circleText);
                    ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(creditsCount).length(), 0);
                    creditsRemainingView.setText(ss);
                    mutexLock.unlock();
                }

                @Override
                public void onCancelled(final DatabaseError databaseError) {
                    // Database query was not successful.
                    System.err.println("Could not access database in MasterCourseList");
                }

            });

        }
    }



    private List<ClassData> getCoursesTaken(Student student) {
        List<ClassData> coursesTaken = new ArrayList<>();
        ArrayList<ClassData> classesTakenList = new ArrayList<>();
        for (int i = 0; i < student.numCoursesTaken(); ++i)
            coursesTaken.add(student.getCourseInformation(i));
        return coursesTaken;
    }


    private Set<String> getStudentMajors(Student student) {
        // Get list of the student's major and minors
        Set<String> majors = new HashSet<>();
        if (student instanceof UndergradStudent) {
            UndergradStudent undergradStudent = (UndergradStudent) student;
            if (undergradStudent.getMajor1() != null) {
                majors.add(undergradStudent.getMajor1());
            }
            if (undergradStudent.getMajor2() != null) {
                majors.add(undergradStudent.getMajor2());
            }
        } else {
            majors.add(((GradStudent) student).getMajor());
        }
        return majors;
    }

    private Set<String> getStudentMinors(Student student) {
        // Get list of the student's major and minors
        Set<String> minors = new HashSet<>();
        if (student instanceof UndergradStudent) {
            UndergradStudent undergradStudent = (UndergradStudent) student;

            System.out.println(undergradStudent.getMinor1());
            System.out.println(undergradStudent.getMinor2());

            if (undergradStudent.getMinor1() != null) {
                minors.add(undergradStudent.getMinor1());
            }
            if (undergradStudent.getMinor2() != null) {
                minors.add(undergradStudent.getMinor2());
            }
        }
        return minors;
    }

    public Course getMasterCourseListItem(int index) {
        return classesNeededList.get(index);
    }

    public void addMasterCourseListItem(Course course) {
        classesNeededList.add(course);
    }

}
