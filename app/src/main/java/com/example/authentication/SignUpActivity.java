package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Sign Up

public class SignUpActivity extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        auth = FirebaseAuth.getInstance();
        TextView textView = findViewById(R.id.textView100);
        email = findViewById(R.id.email100);
        password = findViewById(R.id.password100);
        Button signup = findViewById(R.id.button100);
        TextView login = findViewById(R.id.textView8);

        textView.setText("Welcome to Sign Up Activity");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                regis(mail, pass);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    public void regis(String mail, String pass) {
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
