package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;


public class Profile extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewProfileAdapter adapter;

    MediaPlayer favoritePageSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        favoritePageSound = MediaPlayer.create(this, R.raw.favoritepage);

        // showing user reviews
        recyclerView = findViewById(R.id.profileReviewRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AccessData.retrieveUserObject(new AccessData.UserObjectCallback() {
            @Override
            public void onDataLoaded(User user) {
//                currentUser = user;
                List<RestaurantReview> userReviews = user.getUserReviews();
                Collections.reverse(userReviews);
                adapter = new ReviewProfileAdapter(userReviews);
                recyclerView.setAdapter(adapter);

                //setting profile name
                TextView profileTitle = findViewById(R.id.profileUserName);
                profileTitle.setText(user.getUsername());
            }
        });

        Button favoritesButton = findViewById(R.id.favoritesbtn);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritePageSound.start();
                Intent intent = new Intent(v.getContext(), ViewFavoritesPage.class);
                v.getContext().startActivity(intent);
            }
        });

        Button logOutButton = findViewById(R.id.logoutbtn);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(v.getContext(), LoginUser.class);
                v.getContext().startActivity(intent);
            }
        });

        FloatingActionButton backButtonFAB = findViewById(R.id.floatingActionButtonProfile);
        backButtonFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

}