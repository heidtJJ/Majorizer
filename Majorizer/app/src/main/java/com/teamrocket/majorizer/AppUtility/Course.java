package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseName = null;
    private String courseCode = null;
    private int credits;

    public Course(final String courseName, final String courseCode, final int credits) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getCredits() {
        return credits;
    }
}
