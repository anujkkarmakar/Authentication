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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.Objects;

//Sign In

public class MainActivity3 extends AppCompatActivity {
    private EditText email, password;
    private FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        auth = FirebaseAuth.getInstance();
        TextView textView = findViewById(R.id.textView100);
        email = findViewById(R.id.email100);
        password = findViewById(R.id.password100);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        Button signup = findViewById(R.id.button100);

        textView.setText("Welcome to Log In Activity");
        signup.setOnClickListener(new View.OnClickListener() {
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
    }

    public void sendPasswordResetEmail(String mail) {
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity3.this, "Password reset link has been sent! Please follow the instructions", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity3.this, "Password reset link could not be send. Please try again!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity3.this, "Congratulations!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity3.this, MainActivity4.class));
                    }
                    else {
                        Toast.makeText(MainActivity3.this, "Please first verify your email id", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity3.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}