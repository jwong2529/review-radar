package com.example.reviewradar;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class LoginDiner extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    MediaPlayer loginSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if the user is logged in
        setContentView(R.layout.login_diner_page);

        loginSound = MediaPlayer.create(this, R.raw.loginsound);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        Button loginPageButton = findViewById(R.id.loginPageButton);
        loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSound.start();
                EditText loginPageEmail = findViewById(R.id.loginPageEmail);
                EditText loginPagePassword = findViewById(R.id.loginPagePassword);
                String emailText = loginPageEmail.getText().toString();
                String passwordText = loginPagePassword.getText().toString();

                signIn(emailText, passwordText);
            }
        });

        FloatingActionButton passwordFAB = findViewById(R.id.passwordButton);
        passwordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginPagePassword = findViewById(R.id.loginPagePassword);
                if (loginPagePassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                    loginPagePassword.setTransformationMethod(null);
                } else {
                    loginPagePassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        TextView signUpTV = findViewById(R.id.loginSignUp);
        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginDiner.this, CreateDinerAccount.class);
                LoginDiner.this.startActivity(intent);
            }
        });
    }


    //Handle user login
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                //Login success
                FirebaseUser user = mAuth.getCurrentUser();
                //find user object and make it static in accessdata

                Toast.makeText(LoginDiner.this, "Login successful.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginDiner.this, ViewHomePage.class);
                LoginDiner.this.startActivity(intent);

            } else {
                //Login failed
                Toast.makeText(LoginDiner.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
