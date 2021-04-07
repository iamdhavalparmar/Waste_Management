package com.example.wastemanagement.Models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class FeedbackComplainData implements Serializable {
    String text="";

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Exclude public String id;
    public FeedbackComplainData(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
