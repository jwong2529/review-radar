package com.example.reviewradar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class LoginUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if the user is logged in
        setContentView(R.layout.activity_login_user);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        Button loginPageButton = findViewById(R.id.loginPageButton);
        loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginPageEmail = findViewById(R.id.loginPageEmail);
                EditText loginPagePassword = findViewById(R.id.loginPagePassword);
                String emailText = loginPageEmail.getText().toString();
                String passwordText = loginPagePassword.getText().toString();

                signIn(emailText, passwordText);
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

                Toast.makeText(LoginUser.this, "Login successful.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginUser.this, MainActivity.class);
                LoginUser.this.startActivity(intent);

            } else {
                //Login failed
                Toast.makeText(LoginUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
