package com.kes.app022_journal.models;

public class User  {
    private String username;
    private String email;
    private String userID;

    public User() {}

    public User(String username, String email, String userID) {
        this.username = username;
        this.email = email;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
