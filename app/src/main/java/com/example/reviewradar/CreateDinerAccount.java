package com.example.reviewradar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class CreateDinerAccount extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    //Firebase authentication
    private FirebaseAuth mAuth;


    //Firebase realtime database
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.create_diner_account_page);
        mediaPlayer = MediaPlayer.create(this,R.raw.welcome);


        mAuth = FirebaseAuth.getInstance();
         mDatabase = FirebaseDatabase.getInstance();

         //onClickListeners stuff
         Button createAccountButton = findViewById(R.id.createAccountPageButton);
         createAccountButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 EditText createAccountUsername = findViewById(R.id.createAccountUsername);
                 EditText createAccountEmail = findViewById(R.id.createAccountEmail);
                 EditText createAccountPassword = findViewById(R.id.createAccountPassword);

                 String usernameText = createAccountUsername.getText().toString();
                 String emailText = createAccountEmail.getText().toString();
                 String passwordText = createAccountPassword.getText().toString();

                 if (!usernameText.isEmpty()) {
                     if (!emailText.isEmpty()) {
                         if (passwordText.length() >= 6) {
                             mediaPlayer.start();
                             createAccount(usernameText, emailText, passwordText);
                         } else {
                             Toast.makeText(CreateDinerAccount.this, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
                         }
                     } else {
                         Toast.makeText(CreateDinerAccount.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                     }
                 } else {
                     Toast.makeText(CreateDinerAccount.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                 }
             }
         });

        FloatingActionButton passwordButton = findViewById(R.id.showPasswordButton);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText createAccountPassword = findViewById(R.id.createAccountPassword);
                if (createAccountPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                    createAccountPassword.setTransformationMethod(null);
                } else {
                    createAccountPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

         Button loginButton = findViewById(R.id.createAccountPageLoginButton);
         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(v.getContext(), LoginDiner.class);
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
                    String userId = firebaseUser.getUid();
                    // Set the user object as the value of the new user node
                    Diner diner = new Diner(userId, username, email, password);

                    mDatabase.getReference("userInfo").child(userId).setValue(diner);

                    Toast.makeText(CreateDinerAccount.this, "Account created.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateDinerAccount.this, LoginDiner.class);
                    CreateDinerAccount.this.startActivity(intent);

                } else {
                    // Error creating user in authentication database
                    Toast.makeText(CreateDinerAccount.this, "Error creating user. Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
