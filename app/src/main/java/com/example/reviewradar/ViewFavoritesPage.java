package com.example.reviewradar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class ViewFavoritesPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_restaurant_page);

        recyclerView = findViewById(R.id.favRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AccessData.retrieveUserObject(new AccessData.UserObjectCallback() {
            @Override
            public void onDataLoaded(User user) {
                List<String> userFavList = user.getUserFavorites();
                Collections.reverse(userFavList);
                adapter = new FavoritesAdapter(userFavList, ViewFavoritesPage.this);
                recyclerView.setAdapter(adapter);
            }
        });

        FloatingActionButton fab = findViewById(R.id.favPageBackButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Profile.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}
