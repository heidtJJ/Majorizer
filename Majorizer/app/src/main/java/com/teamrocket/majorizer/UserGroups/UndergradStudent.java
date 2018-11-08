package com.teamrocket.majorizer.UserGroups;

public class UndergradStudent extends Student {

    // DATA MEMBERS
    private String major1 = null;
    private String major2 = null;
    private String minor1 = null;
    private String minor2 = null;


    // SET METHODS
    void setMajor1(String major1) {
        this.major1 = major1;
    }

    void setMajor2(String major2) {
        this.major2 = major2;
    }

    void setMinor1(String minor1) {
        this.minor1 = minor1;
    }

    void setMinor2(String minor2) {
        this.minor2 = minor2;
    }

    // GET METHODS
    String getMajor1() {
        return this.major1;
    }

    String getMajor2() {
        return this.major2;

    }

    String getMinor1() {
        return this.minor1;
    }

    String getMinor2() {
        return this.minor2;
    }
}

