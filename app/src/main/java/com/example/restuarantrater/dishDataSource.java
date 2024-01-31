package com.example.restuarantrater;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class dishDataSource {

    private SQLiteDatabase database;
    private dishDBHelper dbHelper;

    public dishDataSource (Context context) {
        dbHelper = new dishDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //inserts a new contact to the database
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


}
