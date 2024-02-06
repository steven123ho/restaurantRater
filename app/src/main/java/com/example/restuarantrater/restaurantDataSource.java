package com.example.restuarantrater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class restaurantDataSource {

    private SQLiteDatabase database;
    private RestaurantDBHelper dbHelper;
    private Context parentContext;


    public restaurantDataSource (Context context) {
        dbHelper = new RestaurantDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //inserts a new contact to the database
    public boolean insertRestaurant (restaurant r) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", r.getName());
            initialValues.put("street", r.getStreet());
            initialValues.put("city", r.getCity());
            initialValues.put("state", r.getState());
            initialValues.put("zipcode", r.getZipCode());

            didSucceed = database.insert("restaurant", null, initialValues) > 0;
        } catch (Exception e){
            // Do nothing, will return false if there is an exemption
        }
        return didSucceed;
    }


    public boolean insertDish (dish d) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", d.getName());
            initialValues.put("type", d.getType());
            initialValues.put("rating", d.getRating());
            initialValues.put("restaurantID", d.getRestaurantID());

            didSucceed = database.insert("dish", null, initialValues) > 0;
        } catch (Exception e){
            // Do nothing, will return false if there is an exemption
        }
        return didSucceed;
    }


    // updates an existing contact from the database
    public boolean updateRestaurant (restaurant r) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) r.getRestaurantID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("name", r.getName());
            updateValues.put("street", r.getStreet());
            updateValues.put("city", r.getCity());
            updateValues.put("state", r.getState());
            updateValues.put("zipcode", r.getZipCode());


            didSucceed = database.update("restaurant", updateValues, "restaurantID=" + rowId, null) > 0;

        } catch (Exception e) {
            // Do nothing, will return false if there is an exemption
        }
        return didSucceed;
    }

    public int getLastID() {

        int lastId;
        try {
            String query = "Select Max(restaurantID) from restaurant";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId =cursor.getInt(0);
            cursor.close();
        }  catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }


    public ArrayList<dish> getDishes() {
        ArrayList<dish> dishes = new ArrayList<dish>();

        try {
            String query = "Select * FROM dish";
            Cursor cursor = database.rawQuery(query, null);

            dish newDish;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newDish = new dish();
                newDish.setDishID(cursor.getInt(0));
                newDish.setName(cursor.getString(1));
                newDish.setType(cursor.getString(2));
                newDish.setRating(cursor.getString(3));
                dishes.add(newDish);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            dishes = new ArrayList<dish>();
        }
        return  dishes;
    }


    public dish getSpecificContact(int dishID) {
        dish dish = new dish();
        String query = "Select * FROM dish WHERE dishId =" + dishID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            dish.setDishID(cursor.getInt(0));
            dish.setName(cursor.getString(1));
            dish.setType(cursor.getString(2));
            dish.setRating(cursor.getString(3));
            cursor.close();
        }
        return  dish;
    }


    public dish getSpecificDish(int dishID) {
        dish dish = new dish();
        String query = "Select * FROM dish WHERE dishID =" + dishID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            dish.setName(cursor.getString(1));
            dish.setType(cursor.getString(2));
            dish.setRating(cursor.getString(3));

            cursor.close();
        }
        return  dish;
    }


    //deleting contact from data source
    public boolean deleteDish (int dishId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("dish", "dishID=" + dishId, null) > 0;
        } catch (Exception e) {
            //Do Nothing return value that is already set to false
        }
        return didDelete;
    }

}
