package com.example.wastemanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wastemanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddStaff extends AppCompatActivity {
    EditText username,email,phone,password;
    Button submit;
    ImageView back;
    Boolean isvalid=false;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });
        username = findViewById(R.id.staff_username);
        email = findViewById(R.id.staff_email);
        phone = findViewById(R.id.staff_phone);
        password = findViewById(R.id.staff_password);
        submit = findViewById(R.id.staff_submit_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDataValid(username);
                isDataValid(phone);
                isDataValid(email);
                isDataValid(password);
                if(!(phone.getText().toString().length() == 10)){
                    isvalid=false;
                    phone.setError("Must 10 Digits Only");
                    return;
                }else {
                    isvalid = true;
                }
                if(password.getText().toString().length()<8){
                    isvalid=false;
                    password.setError("Password Must Be 8 Characters Long");
                    return;
                }else {
                    isvalid = true;
                }
                if(isvalid){
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddStaff.this, "User Added", Toast.LENGTH_SHORT).show();
                                firebaseUser = firebaseAuth.getCurrentUser();
                                firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddStaff.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddStaff.this, "Error !"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                String userId = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("users").document(userId);
                                Map<String,Object> user = new HashMap<>();
                                user.put("username",username.getText().toString().trim());
                                user.put("phone",phone.getText().toString().trim());
                                user.put("role","staff");
                                user.put("email",email.getText().toString().trim());
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddStaff.this, "User data saved", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddStaff.this, "Error ! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                finish();
                            }else{
                                Toast.makeText(AddStaff.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void isDataValid(EditText field) {
        if(field.getText().toString().isEmpty()){
            field.setError("Required Field");
            isvalid = false;
            return;
        }else
            isvalid = true;
    }
}