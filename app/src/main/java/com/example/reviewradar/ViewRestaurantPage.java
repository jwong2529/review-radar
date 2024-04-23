package com.example.reviewradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewRestaurantPage extends AppCompatActivity {

    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_page);

        //testing!
        Intent intent = getIntent();
        if (intent != null) {
            restaurantName = intent.getStringExtra("restaurantName");
            updatePageFields();
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        Button postReviewButton = findViewById(R.id.postReviewButton);
        postReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostAReview.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void updatePageFields() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);

        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String cuisineType = dataSnapshot.child("cuisineType").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);

                    TextView restaurantNameTV = findViewById(R.id.restaurantPageTitle);
                    TextView cuisineTypeTV = findViewById(R.id.restaurantPageCuisineType);
                    TextView addressTV = findViewById(R.id.restaurantPageAddress);

                    restaurantNameTV.setText(restaurantName);
                    cuisineTypeTV.setText(cuisineType);
                    addressTV.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
}