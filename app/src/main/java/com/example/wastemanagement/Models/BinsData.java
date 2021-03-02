package com.example.wastemanagement.Models;

import java.io.Serializable;

public class BinsData implements Serializable {
    String binId="";
    String binAddress = "";
    int status;
    int adapterstatus;
    String bin_level;

    public BinsData(String binId, String binAddress, int status, int adapterstatus, String bin_level) {
        this.binId = binId;
        this.binAddress = binAddress;
        this.status = status;
        this.adapterstatus = adapterstatus;
        this.bin_level = bin_level;
    }

    public BinsData(String binId, String binAddress, String bin_level) {
        this.binId = binId;
        this.binAddress = binAddress;
        this.bin_level = bin_level;
    }


    public String getBin_level() { return bin_level; }

    public String getBinId() {
        return binId;
    }

    public int getStatus() {
        return status;
    }

    public int getAdapterstatus() {
        return adapterstatus;
    }

    public String getBinAddress() {
        return binAddress;
    }
}
