package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);


        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    Toast.makeText(CreateAccount.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccount.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

}