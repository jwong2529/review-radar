package com.example.reviewradar;

public class RestaurantReview {

    String reviewerName;
    private float rating;
    private String comment;
    private String imageUrl;

    public RestaurantReview() {
    }

    public RestaurantReview(String reviewerName, float rating, String comment) {
//        this.user = user;
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;
        this.imageUrl = "";
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
