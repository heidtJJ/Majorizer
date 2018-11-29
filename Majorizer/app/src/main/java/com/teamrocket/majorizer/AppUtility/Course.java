package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String courseName = null;
    private String courseCode = null;
    private int credits;
    private ArrayList<String> preReq;

    public Course(final String courseName, final String courseCode, final int credits, final ArrayList<String> preReq) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.preReq = preReq;
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

    public ArrayList<String> getPreReq() {
        return preReq;
    }
}
