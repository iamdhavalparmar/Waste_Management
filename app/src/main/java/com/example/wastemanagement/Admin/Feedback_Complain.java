package com.example.wastemanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wastemanagement.Adapters.FeedbackComplainAdapter;
import com.example.wastemanagement.Adapters.ManageBinsAdapter;
import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.Models.FeedbackComplainData;
import com.example.wastemanagement.R;
import com.example.wastemanagement.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Feedback_Complain extends AppCompatActivity implements FeedbackComplainAdapter.ComplaintListener{
    ImageView backbutton;
    RecyclerView recyclerView;
    TextView textView;
    List itemList = new ArrayList();
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_complain);
        backbutton = findViewById(R.id.backbutton_feedback);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.feedback_complain_rec);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ProgressDialog myDialog= Utils.setupProgressDialog(Feedback_Complain.this);
        myDialog.show();
        CollectionReference collectionReference = firestore.collection("complaints");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshots :task.getResult()){
                        String id = documentSnapshots.getId();
                        String message = documentSnapshots.getString("message").toString();
                        FeedbackComplainData item = new FeedbackComplainData(message);
                        item.setId(id);
                        itemList.add(item);
                    }
                    FeedbackComplainAdapter fc_adapter = new FeedbackComplainAdapter(itemList,getApplicationContext(),Feedback_Complain.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(fc_adapter);
                    myDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Feedback_Complain.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnItemClick(FeedbackComplainData complainData) {
        Toast.makeText(this, "ID = "+complainData.getId(), Toast.LENGTH_SHORT).show();
    }
}