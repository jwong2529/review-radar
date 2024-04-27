package com.example.reviewradar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
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


    //Firebase authentication
    private FirebaseAuth mAuth;


    //Firebase realtime authentication
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_create_account_old);


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

                 createAccount(usernameText, emailText, passwordText);

                 Toast.makeText(CreateAccount.this, "Account created.", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(v.getContext(), MainActivity.class);
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
                    Log.i("XXX", "please");
                    // User created in authentication database
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    // Generate a unique key for the new user node
                    String userId = mDatabase.getReference("users").push().getKey();
                    // Set the user object as the value of the new user node
                    User user = new User(userId, username, email, password);
                    mDatabase.getReference("users").child(userId).setValue(user);

                } else {
                    // Error creating user in authentication database
                    Toast.makeText(CreateAccount.this, "Error creating user.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
