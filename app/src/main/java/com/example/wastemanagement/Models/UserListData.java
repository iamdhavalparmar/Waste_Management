package com.example.wastemanagement.Models;

public class UserListData {
    String name="";
    String email="";

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserListData(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
