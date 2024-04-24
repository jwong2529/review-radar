package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;






public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
//    private Map<String, Restaurant> restaurantMap;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(MainActivity.this, CreateAccount.class));
                return true;
            } else if (item.getItemId() == R.id.post_nav) {
                startActivity(new Intent(MainActivity.this, PostAReview.class));
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_page);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Parse csv file to obtain restaurant data
        try {
            InputStream inputStream = getAssets().open("restaurantsERHP.txt");
//            restaurantMap = parseCSV(inputStream);
            parseCSV(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new RestaurantAdapter(restaurantMap);
        adapter = new RestaurantAdapter(RestaurantData.restaurantMap);
        recyclerView.setAdapter(adapter);


        //TESTING!
//        RestaurantReview review = new RestaurantReview("Janice", 3, "Good");
//        Restaurant restaurant = restaurantMap.get("The Oinkster");
//        restaurant.addReview(review);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("The Oinkster");
//
//        myRef.setValue(restaurant);

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void parseCSV(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //skips the first line (just category labels)
            reader.readLine();

            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                //checking for three categories for now
                if (parts.length == 3) {
                    String restaurantName = parts[0];
                    String cuisineType = parts[1];
                    String address = parts[2];

                    //Check if the restaurant already exists in map
                    if (!RestaurantData.restaurantMap.containsKey(restaurantName)) {
                        //create a new Restaurant object
                        Restaurant restaurant = new Restaurant(restaurantName, cuisineType, address);
                        RestaurantData.restaurantMap.put(restaurantName, restaurant);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference(restaurantName);

                        myRef.setValue(restaurant);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}