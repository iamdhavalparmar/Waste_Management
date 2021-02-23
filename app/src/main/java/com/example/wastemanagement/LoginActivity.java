package com.example.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Button button_signup;
    EditText login,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        button_signup = (Button)findViewById(R.id.login_button);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = findViewById(R.id.login_email);
                password = findViewById(R.id.login_password);
                String id = login.getText().toString();
                String pass = password.getText().toString();
                if(!id.isEmpty() && !pass.isEmpty()) {
                    if (id.equals("admin") && pass.equals("admin")) {
                        Intent it = new Intent(LoginActivity.this, Dashboard.class);
                        it.putExtra("Role", "admin");
                        startActivity(it);
                    } else if (id.equals("staff") && pass.equals("staff")) {
                        Intent it = new Intent(LoginActivity.this, Dashboard.class);
                        it.putExtra("Role", "staff");
                        startActivity(it);
                    } else if (id.equals("student") && pass.equals("student")) {
                        Intent it = new Intent(LoginActivity.this, Dashboard.class);
                        it.putExtra("Role", "student");
                        startActivity(it);
                    }
                }
            }
        });

    }
}