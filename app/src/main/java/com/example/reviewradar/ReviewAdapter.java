package com.example.reviewradar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<RestaurantReview> reviews;


    public ReviewAdapter(List<RestaurantReview> reviews) {
        this.reviews = reviews;
    }
    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_post, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        RestaurantReview review = reviews.get(position);
//        holder.bind(review.getReviewerName(), review.getComment(), review.getRating());
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private TextView userReviewTextView;
        private RatingBar userRatingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.reviewPostUsername);
            userReviewTextView = itemView.findViewById(R.id.reviewPostReview);
            userRatingBar = itemView.findViewById(R.id.reviewPostRating);
        }

        public void bind(RestaurantReview review) {
            usernameTextView.setText(review.getReviewerName());
            userReviewTextView.setText(review.getComment());
            userRatingBar.setRating(review.getRating());
        }
    }

}
