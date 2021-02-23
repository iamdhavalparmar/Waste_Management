package com.example.wastemanagement;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wastemanagement.Adapters.DashboardAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navigation;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List itemList = new ArrayList();
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String role = getIntent().getStringExtra("Role");
        if(role.equals("admin"))
            dummydata();
        else if(role.equals("staff"))
            staffdummydata();
        else if(role.equals("student"))
            studentdummydata();
//        textView = findViewById(R.id.temp_text);
//        textView.setText(role);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        navigation = findViewById(R.id.navigation);
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT,true);
            }
        });

        recyclerView = findViewById(R.id.dashboard_cardview_rec);
        DashboardAdapter dashboardAdapter = new DashboardAdapter(itemList,getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(dashboardAdapter);

    }

    private void studentdummydata() {
        int[] image = new int[]{
                R.drawable.ic_manage_alerts,R.drawable.ic_bins,R.drawable.ic_manage_profile,
                R.drawable.ic_complaint};

        DashboardData item = new DashboardData(image[0],"My Alerts");
        itemList.add(item);

        DashboardData item1 = new DashboardData(image[1],"Search Bins");
        itemList.add(item1);

        DashboardData item2 = new DashboardData(image[2],"Manage Profile");
        itemList.add(item2);

        DashboardData item3 = new DashboardData(image[3],"Complaint");
        itemList.add(item3);
    }

    private void staffdummydata() {
        int[] image = new int[]{
                R.drawable.ic_manage_alerts,R.drawable.ic_bins,R.drawable.ic_manage_profile};

        DashboardData item = new DashboardData(image[0],"My Alerts");
        itemList.add(item);

        DashboardData item1 = new DashboardData(image[1],"In Progress");
        itemList.add(item1);

        DashboardData item2 = new DashboardData(image[2],"Manage Profile");
        itemList.add(item2);

    }

    private void dummydata() {
        int[] image = new int[]{
                R.drawable.ic_manage_alerts,R.drawable.ic_bins,R.drawable.ic_staff_list,
                R.drawable.ic_user_list,R.drawable.ic_feedback,R.drawable.ic_complaint};

        DashboardData item = new DashboardData(image[0],"Manage Alerts");
        itemList.add(item);

        DashboardData item1 = new DashboardData(image[1],"Manage Bins");
        itemList.add(item1);

        DashboardData item2 = new DashboardData(image[2],"Manage Staff");
        itemList.add(item2);

        DashboardData item3 = new DashboardData(image[3],"Users List");
        itemList.add(item3);

        DashboardData item4 = new DashboardData(image[4],"Feedback");
        itemList.add(item4);

        DashboardData item5 = new DashboardData(image[5],"Complaints");
        itemList.add(item5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;

    }
}