package com.example.wastemanagement.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.wastemanagement.Adapters.UsersListAdapter;
import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.Models.UserListData;
import com.example.wastemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class UsersList extends AppCompatActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    List itemList = new ArrayList();
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
        recyclerView  = findViewById(R.id.users_list_rec);
        dummydata();
        UsersListAdapter usersListAdapter = new UsersListAdapter(itemList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(usersListAdapter);
    }

    private void dummydata() {

        UserListData item = new UserListData("Dhaval","dhaval@nitc.ac.in");
        itemList.add(item);

        UserListData item2 = new UserListData("Aakash","aakash@nitc.ac.in");
        itemList.add(item2);

        UserListData item3 = new UserListData("Digvijay","digvijay@nitc.ac.in");
        itemList.add(item3);

        UserListData item4 = new UserListData("Abhishek","abhishek@nitc.ac.in");
        itemList.add(item4);

        UserListData item5 = new UserListData("Saikat","saikat@nitc.ac.in");
        itemList.add(item5);

    }
}