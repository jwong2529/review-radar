package com.example.reviewradar;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AccessData {

    private String restaurantName;
    private boolean doesRestaurantExist;
    private Restaurant restaurant;

    public static Map<String, Restaurant> restaurantMap;

    public AccessData(String restaurantName) {
        this.restaurantName = restaurantName;
        this.doesRestaurantExist = false;
    }

    public boolean lookForRestaurant() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantRef = database.getReference(restaurantName);

        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
        AccessData.restaurantMap = new HashMap<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantsRef = database.getReference();

        restaurantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Iterate through the dataSnapshot to retrieve restaurant data
                for (DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                    String restaurantName = restaurantSnapshot.getKey();
                    if (!restaurantName.equals("userInfo")) {
                        Restaurant restaurant = restaurantSnapshot.getValue(Restaurant.class);
                        AccessData.restaurantMap.put(restaurantName, restaurant);
                    }

                }

                callback.onDataLoaded(restaurantMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

        Restaurant restaurant = AccessData.restaurantMap.get(restaurantName);
        restaurant.addReview(review);
        restaurantRef.setValue(restaurant);
    }

    public static void retrieveUserObject(UserObjectCallback callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("userInfo").child(fbUser.getUid());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Diner diner;
                if (snapshot.exists()) {
                    diner = snapshot.getValue(Diner.class);
                } else {
                    diner = null;
                }
                callback.onDataLoaded(diner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface UserObjectCallback {
        void onDataLoaded(Diner diner);
    }

    public static void addReviewToUser(Diner diner, RestaurantReview review) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("userInfo").child(diner.getUserKey());
        diner.addUserReview(review);
        userRef.setValue(diner);
    }

    public static void addFavoriteForUser(Diner diner, String restaurantName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("userInfo").child(diner.getUserKey());

        diner.addUserFavorite(restaurantName);
        userRef.setValue(diner);
    }

    public static void removeFavoriteForUser(Diner diner, String restaurantName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("userInfo").child(diner.getUserKey());

        diner.removeUserFavorite(restaurantName);
        userRef.setValue(diner);
    }
}
