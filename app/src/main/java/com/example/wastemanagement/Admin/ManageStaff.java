package com.example.wastemanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wastemanagement.Adapters.ManageStaffAdapter;
import com.example.wastemanagement.Models.StaffData;
import com.example.wastemanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManageStaff extends AppCompatActivity {
    RecyclerView staffrecycler;
    Button addstaff;
    List stafflist = new ArrayList();
    ImageView backbutton;
    FirebaseFirestore dbroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);
        dbroot = FirebaseFirestore.getInstance();
        staffrecycler = findViewById(R.id.staffrecycler);
        addstaff = findViewById(R.id.addstaff);
        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CollectionReference collectionReference= dbroot.collection("users");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getString("role").equals("staff")){
                            String name = document.getString("name").toString();
                            String phone = document.getString("phone").toString();
                            Log.d("data1", "onComplete:  var = "+ name + " "+ phone);

                            StaffData staffData = new StaffData(name ,phone );
                            stafflist.add(staffData);
                            Log.d("data1", "onComplete: "+document.getString("name")+" "+document.getString("phone"));
                        }
                    }
                    ManageStaffAdapter manageStaffAdapter = new ManageStaffAdapter(stafflist,getApplicationContext());
                    staffrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    staffrecycler.setAdapter(manageStaffAdapter);
                    Log.d("data2", "onCreate: "+ stafflist);
                } else {
                    Log.d("data1", "Error getting documents: ", task.getException());
                }
            }
        });

        addstaff = findViewById(R.id.addstaff);
        addstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(ManageStaff.this,AddStaff.class);
                startActivity(it);
            }
        });



    }

    private void dummydata(String name, String phone) {
        Log.d("data1", "dummydata: cane into method call" + name+" "+phone);
        StaffData staffData = new StaffData("123" ,"sdfsdf" );
        stafflist.add(staffData);
    }
}
