package com.example.restuarantrater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class restaurantDataSource {

    private SQLiteDatabase database;
    private RestaurantDBHelper dbHelper;

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


}
