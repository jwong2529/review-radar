package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class PostARestaurantReview extends AppCompatActivity {
    MediaPlayer postSound;
    MediaPlayer trashSound;
    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_a_review_page);
        postSound = MediaPlayer.create(this,R.raw.postsound);
        trashSound = MediaPlayer.create(this, R.raw.trash);


        //Retrieve restaurant name extra from Intent
        restaurantName = getIntent().getStringExtra("restaurantName");
        EditText restaurantNameEditText = findViewById(R.id.postReviewResTitle);

        //Autofill restaurant name field if not null (basically when user presses post
        // review button directly from restaurant page instead of nav bar)
        if (restaurantName != null) {
            restaurantNameEditText.setText(restaurantName);
        }


        Button cancelReviewButton = findViewById(R.id.cancalReviewButton);
        cancelReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trashSound.start();
                Intent intent = new Intent(v.getContext(), ViewHomePage.class);
                v.getContext().startActivity(intent);
            }
        });

        Button postReviewPageButton = findViewById(R.id.postReviewPageButton);
        postReviewPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSound.start();
                handleReview();
            }
        });

    }

    private void handleReview() {
        EditText restaurantNameET = findViewById(R.id.postReviewResTitle);
        String restaurantNameText = restaurantNameET.getText().toString();

        //testing
        AccessData resData = new AccessData(restaurantNameText);

        if (AccessData.restaurantMap.containsKey(restaurantNameText)) {
            if (checkDescription()) {
                RatingBar ratingBar = findViewById(R.id.postReviewRatingBar);
                EditText reviewET = findViewById(R.id.postReviewDescription);

                float rating = ratingBar.getRating();
                String reviewDescription = reviewET.getText().toString();

                AccessData.retrieveUserObject(new AccessData.UserObjectCallback() {
                    @Override
                    public void onDataLoaded(Diner diner) {
//                        RestaurantReview review = new RestaurantReview(user, rating, reviewDescription);

                        RestaurantReview review = new RestaurantReview(restaurantNameText, diner.getUsername(), rating, reviewDescription);

                        resData.addReviewToRestaurant(restaurantNameText, review);

                        AccessData.addReviewToUser(diner, review);


                        showToast("Review posted!");

                        Intent intent = new Intent(PostARestaurantReview.this, ViewRestaurantPage.class);
                        intent.putExtra("restaurantName", restaurantName);
                        startActivity(intent);
                    }
                });
            } else {
                showToast("Review must be between 0 and 250 characters.");
            }
        } else {
            showToast("Restaurant not found. Please enter a valid restaurant name.");
        }
    }


    private boolean checkDescription() {
        TextView reviewTV = findViewById(R.id.postReviewDescription);
        String reviewText = reviewTV.getText().toString();
        if (reviewText.length() > 0 && reviewText.length() <= 250) {
            return true;
        }
        return false;
    }


    private void showToast(String message) {
        Toast.makeText(PostARestaurantReview.this, message, Toast.LENGTH_SHORT).show();
    }
}