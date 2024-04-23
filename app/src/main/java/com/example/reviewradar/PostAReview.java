package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PostAReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_areview);

        //Retrieve restaurant name extra from Intent
        String restaurantName = getIntent().getStringExtra("restaurantName");
        EditText restaurantNameEditText = findViewById(R.id.postReviewResTitle);

        //Autofll restaurant name field if not null (basically when user presses post
        // review button directly from restaurant page instead of nav bar)
        if (restaurantName != null) {
            restaurantNameEditText.setText(restaurantName);
        }

        Button cancelReviewButton = findViewById(R.id.cancalReviewButton);
        cancelReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}