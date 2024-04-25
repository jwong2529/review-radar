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
//    private List<Integer> intPositions;  //testing
    String restaurantName;

    public ReviewAdapter(List<RestaurantReview> reviews) {
        this.reviews = reviews;
//        this.intPositions = new ArrayList<>()(reviews.)
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
        holder.bind(review.getReviewerName(), review.getComment(), review.getRating());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private TextView userReviewTextView;
        private RatingBar userRatingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            userReviewTextView = itemView.findViewById(R.id.reviewPostReview);
            userRatingBar = itemView.findViewById(R.id.reviewPostRating);
        }

        public void bind(String username, String review, float rating) {
            usernameTextView.setText("User");   //need to set up usernames for users
            userReviewTextView.setText(review);
            userRatingBar.setRating(rating);
        }
    }

}
