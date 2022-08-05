package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Button button = findViewById(R.id.button3);
        TextView textView = findViewById(R.id.textView3);
        textView.setText("Thanks for Signing up. You can now continue to login");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Main5Activity.this, "Redirecting to login page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main5Activity.this, MainActivity3.class));
            }
        });
    }
}