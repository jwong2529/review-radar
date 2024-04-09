package com.example.reviewradar;

public class Restaurant {
    private String name;
    private String address;
    private String cuisine;

    public Restaurant(String name, String address, String cuisine) {
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
