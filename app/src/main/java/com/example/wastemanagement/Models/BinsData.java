package com.example.wastemanagement.Models;

import java.io.Serializable;

public class BinsData implements Serializable {
    String binId="";
    String binAddress = "";
    int image;

    public BinsData(String binId, String binAddress, int image) {
        this.binId = binId;
        this.binAddress = binAddress;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getBinId() {
        return binId;
    }

    public String getBinAddress() {
        return binAddress;
    }
}
