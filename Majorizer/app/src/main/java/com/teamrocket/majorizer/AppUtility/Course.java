package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Course implements Serializable {
    private String courseName = null;
    private String courseCode = null;
    private int credits = -1;
    private Set<Course> preReq = null;

    public Course(final String courseName, final String courseCode, final int credits, final Set<Course> preReq) {
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

    public Set<Course> getPreRequsites() {
        return preReq;
    }

}
