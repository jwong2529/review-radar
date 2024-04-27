package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ValueEventListener;

import android.view.MenuItem;






public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;

    List<Restaurant> restaurantList;


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


        AccessData.retrieveAllRestaurants(new AccessData.RestaurantDataCallback() {
            @Override
            public void onDataLoaded(Map<String, Restaurant> restaurantMap) {
                try {
                    InputStream inputStream = getAssets().open("restaurantsERHP.txt");
                    parseCSV(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setHasFixedSize(true); //new
                adapter = new RestaurantAdapter(restaurantList);
                recyclerView.setAdapter(adapter);

                //new
                SearchView searchView = findViewById(R.id.searchWidget);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterRestaurants(newText);
                        return true;
                    }
                });

            }
        });

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
                String restaurantName = parts[0];
                String cuisineType = parts[1];
                String address = parts[2];

                //Check if restaurant already exists in database, add if not
                AccessData resData = new AccessData(restaurantName);

                if (!AccessData.restaurantMap.containsKey(restaurantName)) {
                    Restaurant restaurant = new Restaurant(restaurantName, cuisineType, address);
                    resData.updateRestaurant(restaurantName, restaurant);
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        restaurantList = new ArrayList<>();
        for (Restaurant restaurant : AccessData.restaurantMap.values()) {
            restaurantList.add(restaurant);
        }

    }

    private void filterRestaurants(String query) {
        List<Restaurant> filteredRestaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredRestaurants.add(restaurant);
            }
        }
        adapter.filterRestaurants(filteredRestaurants);
    }

}