package com.teamrocket.majorizer.Student;


import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.AppUtility.Schedule;
import com.teamrocket.majorizer.Account;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Student extends Account {

    // DATA MEMBERS
    private String advisor1 = null;
    private String advisor2 = null;
    private Schedule schedule = null;

    // COURSE HISTORY (DATA MEMBERS)
    // These three arrays will correspond to a student's taken course
    // in each index. These arrays will always be the same length.
    private final List<ClassData> coursesTakenList = new ArrayList<>();

    // GET METHODS
    public String getAdvisor1() {
        return this.advisor1;
    }

    public String getAdvisor2() {
        return this.advisor2;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public int numCoursesTaken() {
        return coursesTakenList.size();
    }

    public ClassData getCourseInformation(int courseIndex) {
        // Check for error case.
        if (courseIndex < 0 || courseIndex >= coursesTakenList.size())
            return null;
        return coursesTakenList.get(courseIndex);
    }

    public int getCreditsTaken() {
        int totalCredits = 0;
        for (ClassData course : coursesTakenList)
            totalCredits += course.getCredits();
        return totalCredits;
    }

    public String getGPA() {
        double scores = 0;
        for (ClassData course : coursesTakenList) {
            double score;
            if (course.getGrade().equals("A+") || course.getGrade().equals("A")) score = 4;
            else if (course.getGrade().equals("A-")) score = 3.6667;
            else if (course.getGrade().equals("B+")) score = 3.3333;
            else if (course.getGrade().equals("B")) score = 3;
            else if (course.getGrade().equals("B-")) score = 2.6667;
            else if (course.getGrade().equals("C+")) score = 2.3333;
            else if (course.getGrade().equals("C")) score = 2;
            else if (course.getGrade().equals("C-")) score = 1.6667;
            else if (course.getGrade().equals("D+")) score = 1.3333;
            else if (course.getGrade().equals("D")) score = 1;
            else if (course.getGrade().equals("D-")) score = .6667;
            else score = 0;
            scores += score * course.getCredits();
        }
        double gpa = scores / getCreditsTaken();
        DecimalFormat df = new DecimalFormat("##.###");
        return df.format(gpa);
    }

    // SET METHODS
    public void setAdvisor1(final String advisor1) {
        this.advisor1 = advisor1;
    }

    public void setAdvisor2(final String advisor2) {
        this.advisor2 = advisor2;
    }

    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;
    }

    public void addCourseTaken(final String courseName, final String courseCode, final String courseGrade, final Integer numCredits) {
        coursesTakenList.add(new ClassData(courseName, courseCode, courseGrade, numCredits));
    }
}
