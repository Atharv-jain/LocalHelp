package com.Shrava.Shrava.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.Shrava.Shrava.R;

public class Signup extends AppCompatActivity {

    EditText username, email, password, rePassword;
    Button signupBtn;
    TextView loginBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.rgUsername);
        email = findViewById(R.id.rgEmail);
        password = findViewById(R.id.rgPassword);
        rePassword = findViewById(R.id.rgRePassword);
        signupBtn = findViewById(R.id.SignupButton);
        loginBtn = findViewById(R.id.loginButton);

        mAuth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(v -> registerUser());

        loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        });
    }
    private void registerUser(){

        String userName = username.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();
        String userRePass = rePassword.getText().toString().trim();

        if(TextUtils.isEmpty(userName)){
            username.setError("Username required");
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            email.setError("Email required");
            return;
        }

        if(TextUtils.isEmpty(userPass)){
            password.setError("Password required");
            return;
        }

        if(!userPass.equals(userRePass)){
            rePassword.setError("Passwords do not match");
            return;
        }

        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        String userId = mAuth.getCurrentUser().getUid();

                        FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(userId)
                                .setValue(userName);

                        Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Signup.this, Login.class));
                        finish();
                    }
                    else{
                        Toast.makeText(Signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}