package com.example.wastemanagement.Models;

import java.io.Serializable;

public class StaffData implements Serializable {

    String staffid="";
    String name = "";

    public StaffData(String staffid, String name) {
        this.staffid = staffid;
        this.name = name;
    }
}
