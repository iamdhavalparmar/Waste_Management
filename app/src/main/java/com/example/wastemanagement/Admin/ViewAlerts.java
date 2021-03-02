package com.example.wastemanagement.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.wastemanagement.Adapters.ManageBinsAdapter;
import com.example.wastemanagement.Adapters.ViewAlertsAdapter;
import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAlerts extends AppCompatActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    List itemList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alerts);
        imageView = findViewById(R.id.backbutton_view_alerts);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish();} });
        dummydata();
        recyclerView = findViewById(R.id.view_alerts_rec);
        ViewAlertsAdapter viewAlertsAdapter = new ViewAlertsAdapter(itemList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(viewAlertsAdapter);
    }

    private void dummydata() {
        BinsData item = new BinsData("1230","NITC",1,1,"80%");
        itemList.add(item);

        BinsData item1 = new BinsData("1230","NITC",0,1,"90%");
        itemList.add(item1);

        BinsData item2 = new BinsData("1230","NITC",2,1,"85%");
        itemList.add(item2);

        }
}