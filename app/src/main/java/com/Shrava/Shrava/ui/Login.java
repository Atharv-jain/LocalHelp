package com.Shrava.Shrava.ui;

import android.content.Intent;
import android.os.Looper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;

import com.Shrava.Shrava.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.Shrava.Shrava.R;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView signupBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = findViewById(R.id.editTextLogEmail);
        password = findViewById(R.id.editTextLogPassword);
        loginBtn = findViewById(R.id.LogButton);
        signupBtn = findViewById(R.id.SignupButton);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> loginUser());

        signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Signup.class));
        });
    }
    private void loginUser() {

        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();

        if(TextUtils.isEmpty(userEmail)){
            email.setError("Email required");
            return;
        }

        if(TextUtils.isEmpty(userPass)){
            password.setError("Password required");
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(Login.this, "User not registered!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
