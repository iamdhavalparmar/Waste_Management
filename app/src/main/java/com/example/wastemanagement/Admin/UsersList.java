package com.example.wastemanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.wastemanagement.Adapters.UsersListAdapter;
import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.Models.UserListData;
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

public class UsersList extends AppCompatActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    List itemList = new ArrayList();
    FirebaseFirestore dbroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        imageView = findViewById(R.id.backbutton_users);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dbroot = FirebaseFirestore.getInstance();
        recyclerView  = findViewById(R.id.users_list_rec);


        ProgressDialog myDialog= Utils.setupProgressDialog(UsersList.this);
        myDialog.show();
        CollectionReference collectionReference= dbroot.collection("users");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("role").equals("student")) {
                            String name = document.getString("name");
                            String email = document.getString("email");
                            UserListData item = new UserListData(name, email);
                            itemList.add(item);
                        }
                    }
                    UsersListAdapter usersListAdapter = new UsersListAdapter(itemList,getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(usersListAdapter);
                    myDialog.dismiss();
                }else{
                    Log.d("studentdata", "onComplete: Error ! "+task.getException().getMessage());
                }
            }
        });
    }
}