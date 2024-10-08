package com.example.reviewradar;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String cuisineType;
    private String address;
    private List<RestaurantReview> reviews;
    private List<String> imageUrls;


    public Restaurant() {
        this.reviews = new ArrayList<>();
        this.imageUrls = new ArrayList<>();
    }
    public Restaurant(String name, String cuisineType, String address) {
        this.name = name;
        this.cuisineType = cuisineType;
        this.address = address;
        this.reviews = new ArrayList<>();
        this.imageUrls = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<RestaurantReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<RestaurantReview> reviews) {
        this.reviews = reviews;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void addReview(RestaurantReview review) {
        reviews.add(review);
    }

    public void addImageUrl(String imageUrl) {
        imageUrls.add(imageUrl);
    }

    public float getAverageRating() {
        List<RestaurantReview> restaurantReviews = getReviews();
        int reviewsSize = restaurantReviews.size();

        float averageRating = 0;
        for (int i = 0; i < reviewsSize; i++) {
            float rating = restaurantReviews.get(i).getRating();
            if (rating < 0 || rating > 5) {
                throw new IllegalArgumentException("Invalid review rating");
            }
            averageRating += rating;
        }

        averageRating = averageRating / reviewsSize;
        //round the average rating to nearest 0.5 increment
        float roundedAverageRating = (float)Math.round(averageRating * 2)/2.0f;
        return roundedAverageRating;
    }

}
