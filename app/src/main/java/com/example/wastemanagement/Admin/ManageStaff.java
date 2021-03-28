package com.example.wastemanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wastemanagement.Adapters.ManageStaffAdapter;
import com.example.wastemanagement.Models.StaffData;
import com.example.wastemanagement.R;
import com.example.wastemanagement.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ManageStaff extends AppCompatActivity implements ManageStaffAdapter.MyStaffListener {
    RecyclerView staffrecycler;
    Button addstaff;
    List stafflist = new ArrayList();
    ImageView backbutton;
    FirebaseFirestore dbroot;
    AlertDialog.Builder builder;
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
        ProgressDialog myDialog= Utils.setupProgressDialog(ManageStaff.this);
        myDialog.show();
        CollectionReference collectionReference= dbroot.collection("users");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if(document.getString("role").equals("staff")){
                            String name = document.getString("name");
                            String phone = document.getString("phone");
                            String email = document.getString("email");
                            Timber.d("onComplete:  var = " + name + " " + phone);

                            StaffData staffData = new StaffData(name ,phone,email );
                            staffData.setId(document.getId());
                            stafflist.add(staffData);
                            Timber.d("onComplete: " + document.getString("name") + " " + document.getString("phone"));
                        }
                    }
                    ManageStaffAdapter manageStaffAdapter = new ManageStaffAdapter(stafflist,getApplicationContext(),ManageStaff.this);
                    staffrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    staffrecycler.setAdapter(manageStaffAdapter);
                    myDialog.dismiss();
                    Timber.d("onCreate: " + stafflist);
                } else {
                    Timber.d(task.getException(), "Error getting documents: ");
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
//
//    private void dummydata(String name, String phone) {
//        Log.d("data1", "dummydata: cane into method call" + name+" "+phone);
//        StaffData staffData = new StaffData("123" ,"sdfsdf" );
//        stafflist.add(staffData);
//    }
    private void delete(String id) {

        CollectionReference collectionReference= dbroot.collection("users");
        collectionReference.document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ManageStaff.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onItemClick(StaffData staffData) {


            builder = new AlertDialog.Builder(ManageStaff.this,R.style.CustomDialog);
        builder.setTitle("Are You Sure To Delete ");
            builder.setMessage(staffData.name);


            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    delete(staffData.getId());
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();







    }
}
