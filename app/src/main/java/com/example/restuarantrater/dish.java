package com.example.restuarantrater;

public class dish {

    private int dishID;
    private String name;
    private String type;
    private String rating;
    private int restaurantID;

    public dish() {
        dishID = -1;
    }


    public int getRestaurantID() {
        return restaurantID;
    }
    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public int getDishID() {
        return dishID;
    }
    public void setDishID(int dishID) {
        this.dishID = dishID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
