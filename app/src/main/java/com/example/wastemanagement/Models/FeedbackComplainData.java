package com.example.wastemanagement.Models;

import java.io.Serializable;

public class FeedbackComplainData implements Serializable {
    String text="";

    public FeedbackComplainData(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
