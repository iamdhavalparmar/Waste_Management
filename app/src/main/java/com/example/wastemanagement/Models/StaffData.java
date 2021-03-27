package com.example.wastemanagement.Models;

import java.io.Serializable;

public class StaffData implements Serializable {

   public String name="";
   public String phone="";

    public StaffData(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
