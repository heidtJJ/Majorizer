package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;

public class ClassData extends Course implements Serializable {
    private String grade = null;

    public ClassData(final String courseName, final String courseCode, final String grade, final int credits) {
        super(courseName, courseCode, credits);
        this.grade = grade;
    }

    public String getGrade() {
        return this.grade;
    }
}
