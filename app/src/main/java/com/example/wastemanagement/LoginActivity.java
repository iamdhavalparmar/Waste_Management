package com.example.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    Button button_signup,button_login,resend;
    EditText login,password;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    String role;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        button_login = findViewById(R.id.login_button_log);
        firebaseAuth = firebaseAuth.getInstance();
        resend = findViewById(R.id.resend_link);
        firestore = FirebaseFirestore.getInstance();
        Encryption encryption = new Encryption();
        encryption.encrypt("aakash",getApplicationContext());
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);

        if(sharedPreferences.contains("username")){
            Log.d("sharedpref", "onCreate: came into sharedpref");
            role = sharedPreferences.getString("role","");
            Log.d("sharedpref", "onCreate: role = "+role);
            if(role.equals("admin")){
                Intent it = new Intent(LoginActivity.this, Dashboard.class);
                it.putExtra("Role", "admin");
                startActivity(it);
                finish();
            }else if(role.equals("student")){
                Intent it = new Intent(LoginActivity.this, Dashboard.class);
                it.putExtra("Role", "student");
                startActivity(it);
                finish();
            }
            else if(role.equals("staff")){
                Intent it = new Intent(LoginActivity.this, Dashboard.class);
                it.putExtra("Role", "staff");
                startActivity(it);
                finish();
            }
        }else{
            Log.d("sharedpref", "onCreate: NO shared pref");
        }
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
                            if (task.isSuccessful()) {
                                firebaseUser = firebaseAuth.getCurrentUser();
                                String adminemail = firebaseUser.getEmail().toString();
                                if(firebaseUser.isEmailVerified()) {
                                    if(adminemail.contains("nitc")){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username",id);
                                        editor.putString("password",pass);
                                        editor.putString("role","student");
                                        editor.commit();
                                        Intent it = new Intent(LoginActivity.this, Dashboard.class);
                                        it.putExtra("Role", "student");
                                        startActivity(it);
                                        finish();
                                    }else if(adminemail.contains("gmail")){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username",id);
                                        editor.putString("password",pass);
                                        editor.putString("role","staff");
                                        editor.commit();
                                        Intent it = new Intent(LoginActivity.this, Dashboard.class);
                                        it.putExtra("Role", "staff");
                                        startActivity(it);
                                        finish();
                                    }
                                }
                                else if(adminemail.equals("admin@nitc.ac.in")){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username",id);
                                    editor.putString("password",pass);
                                    editor.putString("role","admin");
                                    editor.commit();
                                    Intent it = new Intent(LoginActivity.this, Dashboard.class);
                                    it.putExtra("Role", "admin");
                                    startActivity(it);
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Verify Your Email", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(LoginActivity.this, "Click On Resend To Get Link", Toast.LENGTH_SHORT).show();
                                    resend.setVisibility(View.VISIBLE);
                                    resend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(LoginActivity.this, "Verification Email Has Been Sent.", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(LoginActivity.this, "Error ! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                finish();
            }
        });
    }
}