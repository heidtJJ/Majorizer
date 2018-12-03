package com.teamrocket.majorizer.Student;


import com.teamrocket.majorizer.Advisor.Advisor;
import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.AppUtility.Schedule;
import com.teamrocket.majorizer.Account;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Student extends Account {

    // DATA MEMBERS
    private Schedule schedule = null;

    // First string is Advisor username, second string is full name.
    private Map<String, String> advisors = new HashMap<>();

    // COURSE HISTORY (DATA MEMBERS)
    private final List<ClassData> coursesPrevTakenList = new ArrayList<>();
    private final List<Course> coursesCurTakingList = new ArrayList<>();

    // GET METHODS
    public String getAdvisorName(String username) {
        return advisors.get(username);
    }

    public Set<String> getAdvisors() {
        return new HashSet<>(advisors.values());
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public int numCoursesTaken() {
        return coursesPrevTakenList.size();
    }

    public int numCurCoursesTaking() {
        return coursesCurTakingList.size();
    }

    public ClassData getPrevCourseInformation(int courseIndex) {
        // Check for error case.
        if (courseIndex < 0 || courseIndex >= coursesPrevTakenList.size())
            return null;
        return coursesPrevTakenList.get(courseIndex);
    }

    public Course getCurCourseInformation(int courseIndex) {
        // Check for error case.
        if (courseIndex < 0 || courseIndex >= coursesCurTakingList.size())
            return null;
        return coursesCurTakingList.get(courseIndex);
    }

    public int getCreditsTaken() {
        int totalCredits = 0;
        for (Course course : coursesPrevTakenList)
            totalCredits += course.getCredits();
        return totalCredits;
    }

    public int getCreditsTaking() {
        int totalCredits = 0;
        for (Course course : coursesCurTakingList)
            totalCredits += course.getCredits();
        return totalCredits;
    }

    public String getGPA() {
        double scores = 0;
        for (ClassData course : coursesPrevTakenList) {
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
    public void addAdvisor(final String username, final String fullName) {
        advisors.put(username, fullName);
    }


    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;
    }

    public void addCoursePrevTaken(final String courseName, final String courseCode, final String courseGrade, final Integer numCredits) {
        coursesPrevTakenList.add(new ClassData(courseName, courseCode, courseGrade, numCredits));
    }

    public void addCourseCurTaking(final String courseName, final String courseCode, final Integer numCredits) {
        coursesCurTakingList.add(new Course(courseName, courseCode, numCredits, new HashSet<Course>()));
    }
}
