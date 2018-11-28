package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;

public class Notification implements Serializable {
    private String header = null;
    private String message = null;

    public Notification(String header, String message) {
        this.header = header;
        this.message = message;
    }

    public String getHeader() {
        return this.header;
    }

    public String getMessage() {
        return this.message;
    }

}
