package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

//Sign In

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        TextView textView = findViewById(R.id.textView100);
        email = findViewById(R.id.email100);
        password = findViewById(R.id.password100);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        TextView signup = findViewById(R.id.textView6);
        Button login = findViewById(R.id.button100);

        textView.setText("Welcome to Log In Activity");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                regis(mail, pass);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To implement Forgot Password feature in Android Application
                String mail = email.getText().toString();
                sendPasswordResetEmail(mail);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Switching to Login Activity", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void sendPasswordResetEmail(String mail) {
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Password reset link has been sent! Please follow the instructions", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Password reset link could not be send. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void regis(String mail, String pass) {
        auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, LandingPage.class));
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Please first verify your email id", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}