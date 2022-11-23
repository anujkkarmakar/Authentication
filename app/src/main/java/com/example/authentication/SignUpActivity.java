package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

//Sign Up

public class SignUpActivity extends AppCompatActivity {

    private EditText email, password, name;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textView100);
        email = findViewById(R.id.email100);
        password = findViewById(R.id.password100);
        name = findViewById(R.id.name);
        Button signup = findViewById(R.id.button100);
        TextView login = findViewById(R.id.textView8);

        textView.setText("Welcome to Sign Up Activity");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString().trim();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                regis(mail, pass, uname);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    public void regis(String mail, String pass, String name) {
        auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();

                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;
                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //show nothing
                                        //because we show the message to the user on the next activity
                                        //TODO: make the changes in the same activity. "Not" another activity
                                        User user1 = new User(name, mail);
                                        String userId = user.getUid();
                                        DocumentReference myRef = firestore.collection("users").document(userId);
                                        myRef.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SignUpActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this, "Profile cannot be created", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                    startActivity(new Intent(SignUpActivity.this, Main5Activity.class));
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
