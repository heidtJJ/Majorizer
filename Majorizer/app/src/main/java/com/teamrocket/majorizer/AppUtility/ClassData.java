package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;

public class ClassData implements Serializable {
    private String courseName = null;
    private String courseCode = null;
    private String grade = null;
    private int credits;

    public ClassData(final String courseName, final String courseCode, final String grade, final int credits) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.grade = grade;
        this.credits = credits;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }
}
