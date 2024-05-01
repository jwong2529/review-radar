package com.example.reviewradar;


import java.util.ArrayList;
import java.util.List;


public class Diner {
    private String userKey;
    private String username;
    private String email;
    private String password;


    private List<RestaurantReview> userReviews;
    private List<String> userFavorites;


    //Firebase needs no argument constructor
    public Diner() {
        this.userReviews = new ArrayList<>();
        this.userFavorites = new ArrayList<>();
    }


    // Constructor
    public Diner(String userKey, String username, String email, String password) {
        this.userKey = userKey;
        this.username = username;
        this.email = email;
        this.password = password;

        this.userReviews = new ArrayList<>();
        this.userFavorites = new ArrayList<>();
    }


    // Getters and setters
    public String getUserKey() {
        return userKey;
    }


    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public List<RestaurantReview> getUserReviews() {
        return userReviews;
    }


    public void setUserReviews(List<RestaurantReview> userReviews) {
        this.userReviews = userReviews;
    }


    public void addUserReview(RestaurantReview userReview) {
        userReviews.add(userReview);
    }


    public List<String> getUserFavorites() {
        return userFavorites;
    }


    public void setUserFavorites(List<String> userFavorites) {
        this.userFavorites = userFavorites;
    }


    public void addUserFavorite(String restaurantName) {
        userFavorites.add(restaurantName);
    }

    public void removeUserFavorite(String restaurantName) {
        userFavorites.remove(restaurantName);
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "User{" +
                "user key=" + userKey +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


