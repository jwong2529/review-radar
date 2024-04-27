package com.example.reviewradar;


import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginUser extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //check if the user is logged in
        setContentView(R.layout.activity_login_user);


        mAuth = FirebaseAuth.getInstance();


        //onClickListeners stuff
    }


    //Handle user login (move this to loginuser class)
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                //Login success
                FirebaseUser user = mAuth.getCurrentUser();
                //find user object and make it static in accessdata
            } else {
                //Login failed
                Toast.makeText(LoginUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
