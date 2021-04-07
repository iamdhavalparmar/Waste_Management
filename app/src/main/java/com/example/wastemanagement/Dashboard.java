package com.example.wastemanagement;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wastemanagement.Adapters.DashboardAdapter;
import com.example.wastemanagement.Admin.AddBins;
import com.example.wastemanagement.Admin.Feedback_Complain;
import com.example.wastemanagement.Admin.ManageBins;
import com.example.wastemanagement.Admin.ManageStaff;
import com.example.wastemanagement.Admin.UsersList;
import com.example.wastemanagement.Admin.ViewAlerts;
import com.example.wastemanagement.Models.DashboardData;
import com.example.wastemanagement.Student.Complaint;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements DashboardAdapter.MyItemonClickLister {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navigation,logout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List itemList = new ArrayList();
    TextView textView;
    String role;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        role = getIntent().getStringExtra("Role");
        firebaseAuth = FirebaseAuth.getInstance();
        if(role.equals("admin"))
            dummydata();
        else if(role.equals("staff"))
            staffdummydata();
        else if(role.equals("student"))
            studentdummydata();
//        textView = findViewById(R.id.temp_text);
//        textView.setText(role);
        drawerLayout = findViewById(R.id.drawer_layout);
        
        logout = findViewById(R.id.logout_image);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences =getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                firebaseAuth.signOut();
                Intent it = new Intent(Dashboard.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
        recyclerView = findViewById(R.id.dashboard_cardview_rec);
        DashboardAdapter dashboardAdapter = new DashboardAdapter(itemList,getApplicationContext(),Dashboard.this);
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

        DashboardData item = new DashboardData(image[0],"View Alerts");
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
    public void onItemClick(DashboardData item) {
        if(role.equals("admin")) {
            if (item.getText().toString().equals("Manage Bins")) {
                Intent intent = new Intent(Dashboard.this, ManageBins.class);
                startActivity(intent);
            } else if (item.getText().toString().equals("Manage Staff")) {
                Intent intent = new Intent(Dashboard.this, ManageStaff.class);
                startActivity(intent);
            }
            else if (item.getText().toString().equals("Feedback")) {
                Intent intent = new Intent(Dashboard.this, Feedback_Complain.class);
                startActivity(intent);
            }else if (item.getText().toString().equals("Complaints")) {
                Intent intent = new Intent(Dashboard.this, Feedback_Complain.class);
                startActivity(intent);
            } else if (item.getText().toString().equals("Users List")) {
                Intent intent = new Intent(Dashboard.this, UsersList.class);
                startActivity(intent);
            } else if (item.getText().toString().equals("View Alerts")) {
                Intent intent = new Intent(Dashboard.this, ViewAlerts.class);
                startActivity(intent);
            }
        }
        else if(role.equals("student")){
            if(item.getText().toString().equals("Complaint")){
               Intent it = new Intent(Dashboard.this,Complaint.class);
               startActivity(it);
            }
        }
    }
}