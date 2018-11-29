package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class ClassData extends Course {
    private String grade = null;

    public ClassData(final String courseName, final String courseCode, final String grade, final int credits) {
        super(courseName, courseCode, credits, new HashSet<Course>());
        this.grade = grade;
    }

    public String getGrade() {
        return this.grade;
    }
}
