package com.teamrocket.majorizer.UserGroups;

import com.teamrocket.majorizer.AppUtility.Schedule;

public class Student extends Account {

    // DATA MEMBERS
    private String advisor1 = null;
    private String advisor2 = null;
    private Schedule schedule = null;

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

    // SET METHODS
    public void setAdvisor1(String advisor1) {
        this.advisor1 = advisor1;
    }

    public void setAdvisor2(String advisor2) {
        this.advisor2 = advisor2;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
