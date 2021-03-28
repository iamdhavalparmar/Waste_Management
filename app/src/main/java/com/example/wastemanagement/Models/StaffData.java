package com.example.wastemanagement.Models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class StaffData implements Serializable {
    @Exclude public String id;
   public String name="";
   public String phone="";
   public String email="";

    public StaffData(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
