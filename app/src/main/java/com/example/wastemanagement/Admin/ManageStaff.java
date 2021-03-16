package com.example.wastemanagement.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wastemanagement.Adapters.ManageBinsAdapter;
import com.example.wastemanagement.Adapters.ManageStaffAdapter;
import com.example.wastemanagement.Models.StaffData;
import com.example.wastemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ManageStaff extends AppCompatActivity {
    RecyclerView staffrecycler;
    Button addstaff;
    List stafflist = new ArrayList();
    ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);

        staffrecycler = findViewById(R.id.staffrecycler);
        addstaff = findViewById(R.id.addstaff);
        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dummydata();
        ManageStaffAdapter manageStaffAdapter = new ManageStaffAdapter(stafflist,getApplicationContext());
        staffrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        staffrecycler.setAdapter(manageStaffAdapter);
        addstaff = findViewById(R.id.addstaff);
        addstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(ManageStaff.this,AddStaff.class);
                startActivity(it);
            }
        });

    }

    private void dummydata() {
        StaffData staffData = new StaffData("1234","asdffg");
        stafflist.add(staffData);

        StaffData staffData1 = new StaffData("1234","asdffg");
        stafflist.add(staffData1);
        StaffData staffData2 = new StaffData("1234","asdffg");
        stafflist.add(staffData2);
        StaffData staffData3 = new StaffData("1234","asdffg");
        stafflist.add(staffData3);
    }
}
