package com.teamrocket.majorizer.Student;

import static com.teamrocket.majorizer.Account.AccountType.GRAD;

public class GradStudent extends Student {

    // DATA MEMBERS
    private String major = null;

    public GradStudent(String firstName, String lastName, String username) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setUserName(username);
        setAccountType(GRAD);
    }

    public GradStudent() {
        super();
        setAccountType(GRAD);
    }


    // SET METHODS
    public void setMajor(String major) {
        this.major = major;
    }

    // GET METHODS
    public String getMajor() {
        return this.major;
    }

}
