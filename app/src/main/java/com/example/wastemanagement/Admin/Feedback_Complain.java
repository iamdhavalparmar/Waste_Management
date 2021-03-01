package com.example.wastemanagement.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wastemanagement.Adapters.FeedbackComplainAdapter;
import com.example.wastemanagement.Adapters.ManageBinsAdapter;
import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.Models.FeedbackComplainData;
import com.example.wastemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class Feedback_Complain extends AppCompatActivity {
    ImageView backbutton;
    RecyclerView recyclerView;
    TextView textView;
    List itemList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_complain);
        backbutton = findViewById(R.id.backbutton_feedback);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.feedback_complain_rec);
        textView = findViewById(R.id.feedback_complain_text);
        dummydata();
        FeedbackComplainAdapter fc_adapter = new FeedbackComplainAdapter(itemList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(fc_adapter);
    }

    private void dummydata() {
        FeedbackComplainData item = new FeedbackComplainData("NITC");
        itemList.add(item);

        FeedbackComplainData item1 = new FeedbackComplainData("NITC");
        itemList.add(item1);

        FeedbackComplainData item2 = new FeedbackComplainData("NITC");
        itemList.add(item2);

        FeedbackComplainData item3 = new FeedbackComplainData("NITC");
        itemList.add(item3);

    }
}