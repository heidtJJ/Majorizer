package com.teamrocket.majorizer;

public class Account {
    public enum AccountType {
        UNDERGRAD, GRAD, ADVISOR, ADMIN;
    }

    private String id = null;
    private String username = null;
    private String password = null;// PROBABLY A BAD IDEA TO HAVE THIS IN MEMORY
    private AccountType accountType;
    private boolean accountLocked = false;

    String getId() {
        return this.id;
    }

    String getUsername() {
        return this.username;
    }

    AccountType getAccountType(){
        return this.accountType;
    }

    boolean isLocked(){
        return this.accountLocked;
    }

    void receiveNotification(){

    }

    void sendNotification(){

    }
}
