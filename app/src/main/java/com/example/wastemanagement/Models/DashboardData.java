package com.example.wastemanagement.Models;

import android.widget.ImageView;

import java.io.Serializable;

public class DashboardData implements Serializable {
    int image;
    String text;

    public DashboardData(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
