package com.teamrocket.majorizer.UserGroups;

import java.util.HashMap;
import java.util.Map;

public class Advisor extends Account {

    // This is a map of the Advisor's advisees.
    // The key is the student's username, the value is the student's full name.
    private Map<String, String> studentsMap = new HashMap<>();

    // This string is the Advisors department. Example: CS, PH, CE
    private String department;

    // Student map methods.
    public boolean hasStudent(String studentUserName) {
        if (studentsMap.containsKey(studentUserName))
            return true;
        else
            return false;
    }

    public void addStudent(String studentUserName, String name) {
        studentsMap.put(studentUserName, name);
    }

    public Map<String, String> getStudents() {
        return studentsMap;
    }

    public void removeStudent(String studentUserName) {
        studentsMap.remove(studentUserName);
    }

    // Department methods.
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
