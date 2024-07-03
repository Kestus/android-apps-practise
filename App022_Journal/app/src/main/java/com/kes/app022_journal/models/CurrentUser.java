package com.kes.app022_journal.models;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CurrentUser extends Application {

    private String username;
    private String userID;

    private static CurrentUser instance;

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }

        return instance;
    }

    public static void setInstance(User user) {
        CurrentUser instance = getInstance();
        instance.setUsername(user.getUsername());
        instance.setUserID(user.getUserID());
    }

    public CurrentUser() {}

    public CurrentUser(User user) {
        this.username = user.getUsername();
        this.userID = user.getUserID();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
