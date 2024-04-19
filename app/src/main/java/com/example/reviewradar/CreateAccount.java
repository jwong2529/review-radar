package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.util.Log;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class CreateAccount extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "CustomAuthActivity";
    private String mCustomToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_old);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        MaterialButton createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String userPassword = password.getText().toString();

                // Create a user object with username and password
                User newUser = new User(email, userPassword);

                // Save user information to Firebase Realtime Database
                String userId = mDatabase.child("users").push().getKey(); // Generate a unique key for the user
                mDatabase.child("users").child(userId).setValue(newUser)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User information saved to database.");
                                Toast.makeText(CreateAccount.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                finish(); // Close the activity after account creation
                            } else {
                                Log.w(TAG, "Failed to save user information to database.", task.getException());
                                Toast.makeText(CreateAccount.this, "Failed to create account.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void startSignIn() {
        // [START sign_in_custom]
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(CreateAccount.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_custom]
    }

    private void updateUI(FirebaseUser user) {

    }

}