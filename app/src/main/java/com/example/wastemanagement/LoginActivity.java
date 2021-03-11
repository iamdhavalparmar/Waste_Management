package com.example.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    Button button_signup,button_login;
    EditText login,password;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        button_login = findViewById(R.id.login_button_log);
        firebaseAuth = firebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = findViewById(R.id.login_email);
                password = findViewById(R.id.login_password);
                String id = login.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(id)){
                    login.setError("Email Is Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Password is Required");
                    return;
                }
                if(pass.length()<8){
                    password.setError("Password Length Should Be 8 Characters");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        firebaseUser = firebaseAuth.getCurrentUser();
                        String adminemail = firebaseUser.getEmail().toString();
                        if(adminemail.equals("admin@nitc.ac.in")){
                            Intent it = new Intent(LoginActivity.this, Dashboard.class);
                            it.putExtra("Role", "admin");
                            startActivity(it);
                            finish();
                        }
                        else if(firebaseUser.isEmailVerified()) {
                            if (task.isSuccessful()) {
                                Intent it = new Intent(LoginActivity.this, Dashboard.class);
                                it.putExtra("Role", "student");
                                startActivity(it);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "Verify Your Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        button_signup = findViewById(R.id.signup_button_log);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this,SignUp.class);
                startActivity(it);
            }
        });
    }
}