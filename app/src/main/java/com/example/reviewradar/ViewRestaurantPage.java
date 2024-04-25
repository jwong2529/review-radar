package com.example.reviewradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewRestaurantPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;

    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_page);

        Intent intent = getIntent();
        if (intent != null) {
            restaurantName = intent.getStringExtra("restaurantName");
            updatePageFields();
        }

        recyclerView = findViewById(R.id.recycler_view_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AccessData resData = new AccessData(restaurantName);
//        Restaurant restaurant = resData.retrieveRestaurant();
//        adapter = new ReviewAdapter(restaurant.getReviews());
        // TESTING HERE
//        Restaurant restaurant = RestaurantData.restaurantMap.get(restaurantName);
//        adapter = new ReviewAdapter(restaurant.getReviews());
//        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewHomePage.class);
                v.getContext().startActivity(intent);
            }
        });

        Button postReviewButton = findViewById(R.id.postReviewButton);
        postReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostAReview.class);
                intent.putExtra("restaurantName", restaurantName);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void updatePageFields() {
        AccessData checkData = new AccessData(restaurantName);

        checkData.retrieveRestaurant(new AccessData.RestaurantObjectCallback() {
            @Override
            public void onDataLoaded(Restaurant restaurant) {
                TextView restaurantNameTV = findViewById(R.id.restaurantPageTitle);
                TextView cuisineTypeTV = findViewById(R.id.restaurantPageCuisineType);
                TextView addressTV = findViewById(R.id.restaurantPageAddress);

                String cuisineType = restaurant.getCuisineType();
                String address = restaurant.getAddress();


                restaurantNameTV.setText(restaurantName);
                cuisineTypeTV.setText(cuisineType);
                addressTV.setText(address);
            }
        });


    }
}