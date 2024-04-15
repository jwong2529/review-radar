package com.example.reviewradar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private Map<String, String> restaurantMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button button1 = findViewById(R.id.button1);
//        Button button2 = findViewById(R.id.button2);
//
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Show text information for button 1
//                showToast("Review posted!");
//            }
//        });
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Show text information for button 2
//                showToast("Draft saved!");
//            }
//        });
        setContentView(R.layout.home_page);

        //Parse csv file to obtain restaurant data
        try {
            InputStream inputStream = getAssets().open("restaurantsERHP.csv");
            restaurantMap = parseCSV(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestaurantAdapter(restaurantMap);
        recyclerView.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> parseCSV(InputStream inputStream) {
        Map<String, String> restaurantMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                //checking for two categories for now
                if (parts.length == 2) {
                    String restaurantName = parts[0];
                    String cuisineType = parts[1];
                    restaurantMap.put(restaurantName, cuisineType);
                }
            }
            reader.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return restaurantMap;
    }

}