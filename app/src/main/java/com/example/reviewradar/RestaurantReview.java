package com.example.reviewradar;

public class RestaurantReview {
    private User user;
    private float rating;
    private String comment;
    private String imageUrl;

    public RestaurantReview() {

    }

    public RestaurantReview(User user, float rating, String comment) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.imageUrl = "";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
