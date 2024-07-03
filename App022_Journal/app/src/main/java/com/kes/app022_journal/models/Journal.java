package com.kes.app022_journal.models;

import com.google.firebase.Timestamp;

public class Journal {
    private String title;
    private String content;
    private String imageURL;

    private String userID;
    private String username;
    private Timestamp timestamp;

    public Journal() {}

    public Journal(
            String title,
            String content,
            String imageURL,
            String userID,
            String username,
            Timestamp timestamp
    ) {
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.userID = userID;
        this.username = username;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
