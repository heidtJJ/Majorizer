package com.teamrocket.majorizer.AppUtility;

import java.io.Serializable;

public class Notification implements Serializable {
    private String header = null;
    private String message = null;
    private String id = null;

    public Notification(String header, String message, final String id) {
        this.header = header;
        this.message = message;
        this.id = id;
    }

    public String getHeader() {
        return this.header;
    }

    public String getMessage() {
        return this.message;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return header + ": " + message;
    }
}
