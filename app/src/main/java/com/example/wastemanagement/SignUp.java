package com.example.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignUp extends AppCompatActivity {
    Button button_login,button_signup;
    EditText name,email,password,repeatpassword;
    Boolean isDataValid = false;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button_login = (Button)findViewById(R.id.login_button);
        button_signup = findViewById(R.id.signup_button);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(SignUp.this,LoginActivity.class);
                startActivity(it);
            }
        });
        name = findViewById(R.id.name);
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        repeatpassword = findViewById(R.id.repeat_password);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData(name);
                validateData(email);
                validateData(password);
                validateData(repeatpassword);
                if(email.getText().toString().contains("nitc.ac.in")){
                    isDataValid = true;
                }else{
                    isDataValid = false;
                    email.setError("Use NITC Id Only");

                }
                if(password.getText().toString().length()<8){
                    isDataValid = false;
                    password.setError("Use Atleast 8 Characters");
                }else{
                    if(password.getText().toString().equals(repeatpassword.getText().toString())){
                        isDataValid=true;
                    }else{
                        isDataValid= false;
                        repeatpassword.setError("Password Does Not Match");
                    }
                }
                if(isDataValid){
                   firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                               firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Toast.makeText(SignUp.this, "Verification Eamil Has Sent", Toast.LENGTH_SHORT).show();
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Log.d("Email Verification", "onFailure: ");
                                   }
                               });
                               userID = firebaseAuth.getCurrentUser().getUid() ;
                               DocumentReference documentReference = firestore.collection("users").document(userID);
                               Map<String,Object> user = new HashMap<>();
                               user.put("name",name.getText().toString().trim());
                               user.put("role","admin");
                               documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Log.d("User Data", "onSuccess: Data stored");
                                   }
                               });
                               Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(SignUp.this,LoginActivity.class));
                               finish();
                           }else{
                               Toast.makeText(SignUp.this, "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }
            }
        });

    }

    private void validateData(EditText field) {
        if(field.getText().toString().isEmpty()) {
            isDataValid = false;
            field.setError("Required Field");
        }
        else
            isDataValid= true;
    }
}