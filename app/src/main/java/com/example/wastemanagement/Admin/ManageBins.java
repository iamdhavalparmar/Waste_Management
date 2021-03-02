package com.example.wastemanagement.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wastemanagement.Adapters.DashboardAdapter;
import com.example.wastemanagement.Adapters.ManageBinsAdapter;
import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.Models.DashboardData;
import com.example.wastemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ManageBins extends AppCompatActivity implements ManageBinsAdapter.MyitemOnClickLister {
    RecyclerView binsrecycler;
    Button addbins;
    List itemList = new ArrayList();
    ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bins);
        binsrecycler = findViewById(R.id.binsrecycler);
        addbins = findViewById(R.id.addbins);
        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dummydata();
        ManageBinsAdapter manageBinsAdapter = new ManageBinsAdapter(itemList,getApplicationContext());
        binsrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binsrecycler.setAdapter(manageBinsAdapter);
        addbins = findViewById(R.id.addbins);
        addbins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(ManageBins.this,AddBins.class);
                startActivity(it);
            }
        });

    }

    private void dummydata() {
        BinsData item = new BinsData("1230","NITC","70%");
        itemList.add(item);

        BinsData item1 = new BinsData("1230","NITC","60%");
        itemList.add(item1);

        BinsData item2 = new BinsData("1230","NITC","70%");
        itemList.add(item2);

    }

    @Override
    public void onItemClick(BinsData item) {
        Toast.makeText(this, item.getBinAddress(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ManageBins.this,AddBins.class);
        startActivity(intent);
    }
}