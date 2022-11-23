package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity4 extends AppCompatActivity {

    private long timeBack;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        TextView textView = findViewById(R.id.textView2);
        assert user != null;
        String name = user.getDisplayName();
        textView.setText(name);
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                //changing the layout
                Toast.makeText(MainActivity4.this, "Successfully sign out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity4.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - timeBack > 1000) {
            Toast.makeText(this, "Press back again to exit...", Toast.LENGTH_SHORT).show();
            timeBack = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }
}