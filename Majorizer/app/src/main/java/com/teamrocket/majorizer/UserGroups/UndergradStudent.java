package com.teamrocket.majorizer.UserGroups;

public class UndergradStudent extends Student {

    // DATA MEMBERS
    private String major1 = null;
    private String major2 = null;
    private String minor1 = null;
    private String minor2 = null;


    // SET METHODS
    public void setMajor1(String major1) {
        this.major1 = major1;
    }

    public void setMajor2(String major2) {
        this.major2 = major2;
    }

    public void setMinor1(String minor1) {
        this.minor1 = minor1;
    }

    public void setMinor2(String minor2) {
        this.minor2 = minor2;
    }

    // GET METHODS
    public String getMajor1() {
        return this.major1;
    }

    public String getMajor2() {
        return this.major2;

    }

    public String getMinor1() {
        return this.minor1;
    }

    public String getMinor2() {
        return this.minor2;
    }
}

