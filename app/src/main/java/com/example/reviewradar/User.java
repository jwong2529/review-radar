package com.example.reviewradar;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    private List<RestaurantReview> userReviews;

    private List<RestaurantReview> userFavorites;

    //Firebase needs no argument constructor
    public User() {
        this.userReviews = new ArrayList<>();
        this.userFavorites = new ArrayList<>();
    }

    // Constructor
    public User(String email, String password) {
        //should add username to list of arguments

        this.password = password;
        this.email = email;
        this.userReviews = new ArrayList<>();
        this.userFavorites = new ArrayList<>();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    // toString() method for debugging
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

