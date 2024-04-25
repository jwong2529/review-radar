package com.example.reviewradar;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccessData {

    private String restaurantName;
    private boolean doesRestaurantExist;
    private Restaurant restaurant;

    public static Map<String, Restaurant> restaurantMap;

    public AccessData(String restaurantName) {
        this.restaurantName = restaurantName;
        this.doesRestaurantExist = false;
        this.restaurant = new Restaurant(null, null, null);

        //testing
//        this.restaurantMap = new HashMap<>();
    }

    public boolean lookForRestaurant() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);

        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    doesRestaurantExist = true;
                } else {
                    doesRestaurantExist = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return doesRestaurantExist;
    }

    public void retrieveRestaurant(RestaurantObjectCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);

        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    restaurant = snapshot.getValue(Restaurant.class);
                } else {
                    restaurant = null;
                }
                callback.onDataLoaded(restaurant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        return restaurant;
    }

    public interface RestaurantObjectCallback {
        void onDataLoaded(Restaurant restaurant);
    }

    public DatabaseReference retrieveRestaurantRef () {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);
        return restaurantRef;
    }

    public static void retrieveAllRestaurants(RestaurantDataCallback callback) {
//        Map<String, Restaurant> restaurantMap = new HashMap<>();
        AccessData.restaurantMap = new HashMap<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantsRef = database.getReference();

        restaurantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Iterate through the dataSnapshot to retrieve restaurant data
                for (DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                    String restaurantName = restaurantSnapshot.getKey();
                    if (!restaurantName.equals("users") && !restaurantName.equals("reviews")) {
                        Restaurant restaurant = restaurantSnapshot.getValue(Restaurant.class);
//                        restaurantMap.put(restaurantName, restaurant);
//                        RestaurantData.restaurantMap.put(restaurantName, restaurant);
                        AccessData.restaurantMap.put(restaurantName, restaurant);
                    }

                }

                callback.onDataLoaded(restaurantMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        return restaurantMap;

    }

    //idk what this is
    public interface RestaurantDataCallback {
        void onDataLoaded(Map<String, Restaurant> restaurantMap);
    }

    public void updateRestaurant(String restaurantName, Restaurant restaurant) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);
        restaurantRef.setValue(restaurant);

        AccessData.restaurantMap.put(restaurantName, restaurant);

//        if (!AccessData.restaurantMap.containsKey(restaurantName)) {
//            AccessData.restaurantMap.put(restaurantName, restaurant);
//        }

    }

    public static void printRestaurantData() {
        for (Map.Entry<String, Restaurant> entry : AccessData.restaurantMap.entrySet()) {
            String restaurantName = entry.getKey();
            Restaurant restaurant = entry.getValue();
            Log.d("HashMapContents", "Restaurant Name: " + restaurantName + ", Restaurant: " + restaurant);
        }
    }

    public void addReviewToRestaurant(String restaurantName, RestaurantReview review) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);

        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                restaurant.addReview(review);
                Log.i("another", String.valueOf(restaurant.getReviews().size()));
                restaurantRef.child("reviews").setValue(restaurant.getReviews());

//                Log.i("XXX", String.valueOf(restaurant.getReviews().get(0)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //make methods for retrieving user data
}
