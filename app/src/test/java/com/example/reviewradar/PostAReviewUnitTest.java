package com.example.reviewradar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 30)
public class PostAReviewUnitTest {
    private PostAReview postAReview;

    @Before
    public void setUp() {
        postAReview = Robolectric.buildActivity(PostAReview.class).create().get();
        AccessData.restaurantMap = new HashMap<>();
    }

    @Test
    public void validateReview_ValidReview() {
        Restaurant restaurant = new Restaurant("TestRestaurant", "Italian", "123 Test St.");
        AccessData.restaurantMap.put("TestRestaurant", restaurant);

        String restaurantName = "TestRestaurant";
        String reviewText = "A good place!";

        boolean isValid = postAReview.validateReview(restaurantName, reviewText);
        assertTrue(isValid);
    }

    @Test
    public void validateReview_InvalidRestaurant() {
        String restaurantName = "NonExistentRestaurant";
        String reviewText = "Great place!";

        boolean isValid = postAReview.validateReview(restaurantName, reviewText);
        assertFalse(isValid);
    }

    @Test
    public void validateReview_TooLongDescription() {
        Restaurant restaurant = new Restaurant("TestRestaurant", "Mexican", "123 Test Ave.");
        AccessData.restaurantMap.put("TestRestaurant", restaurant);

        String restaurantName = "TestRestaurant";
        String longReview = new String(new char[251]).replace('\0', 'A');

        boolean isValid = postAReview.validateReview(restaurantName, longReview);
        assertFalse(isValid);
    }

    @Test
    public void validateReview_EmptyDescription() {
        Restaurant restaurant = new Restaurant("TestRestaurant", "Indian", "456 Test Blvd.");
        AccessData.restaurantMap.put("TestRestaurant", restaurant);

        String restaurantName = "TestRestaurant";
        String emptyReview = "";

        boolean isValid = postAReview.validateReview(restaurantName, emptyReview);
        assertFalse(isValid);
    }
}

