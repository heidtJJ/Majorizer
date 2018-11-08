package com.teamrocket.majorizer.UserGroups;

import java.io.Serializable;

public class Account implements Serializable {

    // Enum for all types of accounts.
    public enum AccountType implements Serializable {
        ERROR, UNDERGRAD, GRAD, ADVISOR, ADMIN;
    }

    // DATA MEMBERS
    private String id = null;
    private String firstName = null;
    private String lastName = null;
    private String username = null;
    private AccountType accountType;

    // SET METHODS
    public void setId(final String id) {
        this.id = id;
    }

    public void setUserName(final String username) {
        this.username = username;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }


    // GET METHODS
    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }


    // SYSTEM METHODS
    public void receiveNotification() {

    }

    public void sendNotification() {

    }


}
