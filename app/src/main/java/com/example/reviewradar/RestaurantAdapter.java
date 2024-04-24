package com.example.reviewradar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private Map<String, Restaurant> restaurantMap;
    private List<String> restaurantNames;

//    private Context context;

    public RestaurantAdapter(Map<String, Restaurant> restaurantMap) {
        this.restaurantMap = restaurantMap;
        this.restaurantNames = new ArrayList<>(restaurantMap.keySet());
//        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_post, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        String restaurantName = restaurantNames.get(position);
        String cuisineType = restaurantMap.get(restaurantName).getCuisineType();
        holder.bind(restaurantName, cuisineType);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewRestaurantPage.class);
                intent.putExtra("restaurantName", restaurantName); //i added this, mb del
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantMap.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantNameTextView;
        private TextView cuisineTypeTextView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.reviewPostUsername);
            cuisineTypeTextView = itemView.findViewById(R.id.restaurant_cuisine_type);
        }

        public void bind(String restaurantName, String cuisineType) {
            restaurantNameTextView.setText(restaurantName);
            cuisineTypeTextView.setText(cuisineType);
        }
    }
}

