package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;






public class ViewHomePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;

    List<Restaurant> restaurantList;

    private static boolean isFirstInstance = true;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(ViewHomePage.this, ViewHomePage.class));
                return true;
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(ViewHomePage.this, ViewDinerProfilePage.class));
                return true;
            } else if (item.getItemId() == R.id.post_nav) {
                startActivity(new Intent(ViewHomePage.this, PostARestaurantReview.class));
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

                //only parse the csv during the first time the home page is run
                if (isFirstInstance) {
                    try {
                        InputStream inputStream = getAssets().open("restaurantsERHP.txt");
                        parseCSV(inputStream);
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isFirstInstance = false;
                }

                restaurantList = new ArrayList<>();
                for (Restaurant restaurant : AccessData.restaurantMap.values()) {
                    restaurantList.add(restaurant);
                }

                recyclerView = findViewById(R.id.favRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(ViewHomePage.this));
                recyclerView.setHasFixedSize(true); //new
                adapter = new RestaurantAdapter(restaurantList, ViewHomePage.this);
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
                String imagePath = parts[3];

                //delete double quotes at front and end of address
                address = address.replaceAll("^\"|\"$", "");
                //rewrite imagePath to delete .jpg part
                imagePath = imagePath.substring(0, imagePath.lastIndexOf('.'));

                //Check if restaurant already exists in database, add if not
                AccessData resData = new AccessData(restaurantName);

                if (!AccessData.restaurantMap.containsKey(restaurantName)) {
                    Restaurant restaurant = new Restaurant(restaurantName, cuisineType, address);
                    restaurant.addImageUrl(imagePath);
                    resData.updateRestaurant(restaurantName, restaurant);
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
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