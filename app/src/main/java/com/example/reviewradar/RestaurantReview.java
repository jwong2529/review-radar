package com.example.reviewradar;

public class RestaurantReview {

    private String restaurantName;

    private String reviewerName;
    private float rating;
    private String comment;
    private String imageUrl;

    public RestaurantReview() {
    }

    public RestaurantReview(String restaurantName, String reviewerName, float rating, String comment) {
        this.restaurantName = restaurantName;
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;
        this.imageUrl = "";
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
