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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

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
        Restaurant restaurant = AccessData.restaurantMap.get(restaurantName);

        //reverse list so newest reviews are shown at top
        List<RestaurantReview> restaurantReviews = restaurant.getReviews();
        Collections.reverse(restaurantReviews);

        adapter = new ReviewAdapter(restaurantReviews);
        recyclerView.setAdapter(adapter);

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

                setAverageRating(restaurant);
            }
        });
    }

    public void setAverageRating(Restaurant restaurant) {

        if (restaurant.getReviews().isEmpty()) {
            showToast("Be the first to leave a review!");
        }

        RatingBar restaurantPageRB = findViewById(R.id.restaurantPageRating);
        restaurantPageRB.setRating(restaurant.getAverageRating());
    }

    private void showToast(String message) {
        Toast.makeText(ViewRestaurantPage.this, message, Toast.LENGTH_SHORT).show();
    }
}