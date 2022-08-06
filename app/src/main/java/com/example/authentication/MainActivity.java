package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){ //if the user is currently signed in, we go to the MainActivity4
            Toast.makeText(this, "You are already signed in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainActivity4.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.button_login);
        Button signup = findViewById(R.id.button_signup);
        TextView textView = findViewById(R.id.textView);

        textView.setText("Welcome to my first authentication app :)");

        //on clicking the signup button, the activity sign up
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we are changing the intent of the current activity
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

        //on pressing the login button, the user is redirected to the login page
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we are changing the current activity
                startActivity(new Intent(MainActivity.this, MainActivity3.class));
                //Toast.makeText(MainActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
    }
}