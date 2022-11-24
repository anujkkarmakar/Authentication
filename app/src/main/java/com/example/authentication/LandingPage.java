package com.example.authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LandingPage extends AppCompatActivity {

    private long timeBack;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseFirestore firestore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        TextView textView1 = findViewById(R.id.textView2);
        TextView textView2 = findViewById(R.id.textView9);
        firestore = FirebaseFirestore.getInstance();

        assert user != null;
        userId = user.getUid();
        DocumentReference documentReference = firestore.collection("users").document(userId);
         documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
             @Override
             public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                 assert value != null;
                 textView1.setText(value.getString("username"));
                 textView2.setText(value.getString("email"));
             }
         });


        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                //changing the layout
                Toast.makeText(LandingPage.this, "Successfully sign out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LandingPage.this, MainActivity.class));
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