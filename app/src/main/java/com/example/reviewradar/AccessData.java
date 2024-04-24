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

    String restaurantName;
    boolean doesRestaurantExist;
    Restaurant restaurant;

    public AccessData(String restaurantName) {
        this.restaurantName = restaurantName;
        this.doesRestaurantExist = false;
        this.restaurant = new Restaurant(null, null, null);
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

    public Restaurant retrieveRestaurant() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);

        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    restaurant = snapshot.getValue(Restaurant.class);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return restaurant;
    }

    public DatabaseReference retrieveRestaurantRef () {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);
        return restaurantRef;
    }

    public void retrieveAllRestaurants(RestaurantDataCallback callback) {
        Map<String, Restaurant> restaurantMap = new HashMap<>();

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
                        restaurantMap.put(restaurantName, restaurant);
                    }

                    //testing
//                    Restaurant restaurant = restaurantSnapshot.getValue(Restaurant.class);
//                    restaurantMap.put(restaurantName, restaurant);
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

        if (!RestaurantData.restaurantMap.containsKey(restaurantName)) {
            RestaurantData.restaurantMap.put(restaurantName, restaurant);
        }
    }

    //make methods for retrieving user data
}
