package com.example.reviewradar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.util.Log;


import java.util.List;




public class CreateAccount extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    //Firebase authentication
    private FirebaseAuth mAuth;


    //Firebase realtime database
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_create_account_old);
        mediaPlayer = MediaPlayer.create(this,R.raw.welcome);


        mAuth = FirebaseAuth.getInstance();
         mDatabase = FirebaseDatabase.getInstance();

         //onClickListeners stuff
         Button createAccountButton = findViewById(R.id.createAccountPageButton);
         createAccountButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mediaPlayer.start();
                 EditText createAccountUsername = findViewById(R.id.createAccountUsername);
                 EditText createAccountEmail = findViewById(R.id.createAccountEmail);
                 EditText createAccountPassword = findViewById(R.id.createAccountPassword);

                 String usernameText = createAccountUsername.getText().toString();
                 String emailText = createAccountEmail.getText().toString();
                 String passwordText = createAccountPassword.getText().toString();

                 if (!usernameText.isEmpty()) {
                     if (!emailText.isEmpty()) {
                         if (passwordText.length() >= 6) {
                             createAccount(usernameText, emailText, passwordText);
                         } else {
                             Toast.makeText(CreateAccount.this, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
                         }
                     } else {
                         Toast.makeText(CreateAccount.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                     }
                 } else {
                     Toast.makeText(CreateAccount.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                 }
             }
         });

         Button loginButton = findViewById(R.id.createAccountPageLoginButton);
         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(v.getContext(), LoginUser.class);
                 v.getContext().startActivity(intent);

             }
         });
    }


    //Handle account creation
    private void createAccount(String username, String email, String password) {
        //Check if user already exists in database
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // User created in authentication database
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    // Generate a unique key for the new user node
//                    String userId = mDatabase.getReference("users").push().getKey();
                    String userId = firebaseUser.getUid();
                    // Set the user object as the value of the new user node
                    User user = new User(userId, username, email, password);

                    mDatabase.getReference("userInfo").child(userId).setValue(user);

                    Toast.makeText(CreateAccount.this, "Account created.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAccount.this, LoginUser.class);
                    CreateAccount.this.startActivity(intent);

                } else {
                    // Error creating user in authentication database
                    Toast.makeText(CreateAccount.this, "Error creating user. Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
