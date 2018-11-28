package com.teamrocket.majorizer.UserGroups;

import com.teamrocket.majorizer.AppUtility.Notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

    // Enum for all types of accounts.
    public enum AccountType implements Serializable {
        ERROR, UNDERGRAD, GRAD, ADVISOR, ADMIN;
    }

    // DATA MEMBERS
    private String id = null;
    private String firstName = null;
    private String lastName = null;
    private String userName = null;
    private AccountType accountType = null;
    private List<Notification> notifications = new ArrayList<>();

    // SET METHODS
    public void setId(final String id) {
        this.id = id;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
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

    public String getUserName() {
        return this.userName;
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
    public void addNotification(final Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getNotifications() {
        return this.notifications;
    }

}
