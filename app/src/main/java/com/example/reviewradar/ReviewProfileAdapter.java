package com.example.reviewradar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewProfileAdapter extends RecyclerView.Adapter<ReviewProfileAdapter.ReviewProfileHolder> {

    private List<RestaurantReview> reviews;

    public ReviewProfileAdapter(List<RestaurantReview> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewProfileAdapter.ReviewProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diner_profile_review_post, parent, false);
        return new ReviewProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewProfileAdapter.ReviewProfileHolder holder, int position) {
        RestaurantReview review = reviews.get(position);
        holder.bind(review);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewRestaurantPage.class);
                intent.putExtra("restaurantName", review.getRestaurantName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewProfileHolder extends RecyclerView.ViewHolder {

        private TextView restaurantNameTV;
        private RatingBar ratingRB;
        private TextView reviewTV;

        public ReviewProfileHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTV = itemView.findViewById(R.id.profileReviewRestaurant);
            ratingRB = itemView.findViewById(R.id.profileReviewRating);
            reviewTV = itemView.findViewById(R.id.profileReviewDescription);
        }

        public void bind(RestaurantReview review) {
            restaurantNameTV.setText(review.getRestaurantName());
            ratingRB.setRating(review.getRating());

            //Only show first 150 characters of the review
            String reviewText = review.getComment();
            if (reviewText.length() > 150) {
                reviewText = reviewText.substring(0, 150) + "...";
            }
            reviewTV.setText(reviewText);
        }
    }
}
