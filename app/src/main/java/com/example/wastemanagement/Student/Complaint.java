package com.example.wastemanagement.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wastemanagement.R;
import com.example.wastemanagement.Utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Complaint extends AppCompatActivity {
    ImageView back;
    Button camera,gallery,upload;
    TextView message;
    StorageReference storage;
    byte[] image = null;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        back = findViewById(R.id.backbutton);
        storage = FirebaseStorage.getInstance().getReference();
        upload = findViewById(R.id.send_complaint);
        message = findViewById(R.id.complaint_message);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(message.getText().toString().trim())){
                    message.setError("Required field");
                    return;
                }else{
                    uploadToFirebase();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish();} });
        camera = findViewById(R.id.camera);
        gallery = findViewById(R.id.gallery);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(it,101);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AppCompatActivity.RESULT_OK){
            if(requestCode == 101){
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG,100,bytes);
//        int size = thumbnail.getRowBytes() * thumbnail.getHeight();
//        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
//        thumbnail.copyPixelsToBuffer(byteBuffer);
//        image = byteBuffer.array();
        image = bytes.toByteArray();
    }

    private void uploadToFirebase() {
        String randomUUID = null;
        CollectionReference ref = firestore.collection("complaints");
        Map<String,Object> complaints = new HashMap<>();

        Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dt.format(newDate);


        String email = firebaseAuth.getCurrentUser().getEmail().toString();
        ProgressDialog myDialog= Utils.setupProgressDialog(Complaint.this);
        myDialog.show();

        if(image!=null){
            randomUUID = UUID.randomUUID().toString();
            StorageReference sr = storage.child("complaints/"+ randomUUID);
            sr.putBytes(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("run1", "onFailure: "+e.getMessage());
                    Toast.makeText(Complaint.this, "Error ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{

            Log.d("run1", "uploadToFirebase: error uploading");
        }

        complaints.put("date",strDate);
        complaints.put("message",message.getText().toString().trim());
        complaints.put("email",email);
        if(randomUUID != null){
            complaints.put("imageid",randomUUID);
        }

        ref.add(complaints).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                message.setText("");
                Toast.makeText(Complaint.this, "Complaint Registered.", Toast.LENGTH_SHORT).show();
                myDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Complaint.this, "Error ! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                myDialog.dismiss();
            }
        });
    }
}