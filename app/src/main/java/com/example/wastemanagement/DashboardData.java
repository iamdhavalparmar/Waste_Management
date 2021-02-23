package com.example.wastemanagement;

import android.widget.ImageView;

public class DashboardData {
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
