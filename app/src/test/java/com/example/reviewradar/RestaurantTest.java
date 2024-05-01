package com.example.reviewradar;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class RestaurantTest {

    @Test
    public void testGetAverageRating_EmptyList() {
        Restaurant restaurant = new Restaurant();
        float averageRating = restaurant.getAverageRating();
        assertEquals(0.0f, averageRating,0);
    }

    @Test
    public void testGetAverageRating_SingleReview() {
        Restaurant restaurant = new Restaurant();
        restaurant.addReview(new RestaurantReview("testRestaurantName1","testReviewerName1",4.5f,null)); // Add a review with rating 4.5
        float averageRating = restaurant.getAverageRating();
        assertEquals(4.5f, averageRating,0);
    }

    @Test
    public void testGetAverageRating_MultipleReviews() {
        Restaurant restaurant = new Restaurant();
        restaurant.addReview(new RestaurantReview("testRestaurantName2", null, 3.0f, null));
        restaurant.addReview(new RestaurantReview("testRestaurantName2", null, 4.0f, null));
        restaurant.addReview(new RestaurantReview("testRestaurantName2", null, 5.0f, null));
        float averageRating = restaurant.getAverageRating();
        assertEquals(4.0f, averageRating, 0);
    }


    @Test
    public void testGetAverageRating_Rounding() {
        Restaurant restaurant = new Restaurant();
        restaurant.addReview(new RestaurantReview("testRestaurantName3", null, 3.2f, null)); // Average would be 3.2
        restaurant.addReview(new RestaurantReview("testRestaurantName3", null, 4.6f, null));; // Average would be 4.6
        float averageRating = restaurant.getAverageRating();
        assertEquals(4.0f, averageRating,0); // Rounded averages should be 3.5
    }


}