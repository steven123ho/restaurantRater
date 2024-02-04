package com.example.restuarantrater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RestaurantDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_RESTAURANTS = "myrestaurants.db";
    private static final int DATABASE_VERSION = 1;

    //Database creation sql statement
    private static final String Create_Table_Restaurant =
            "create table restaurant (restaurantId integer primary key autoincrement, " +
                    "name text not null, street text, " +
                    "city text, state text, zipcode text);";

    private static final String Create_Table_DISH =
            "create table dish (dishID integer primary key autoincrement, " +
                    "name text not null, type text, " +
                    "rating text, restaurantID int, " +
                    "foreign key (restaurantID) references restaurant(restaurantId));";

    public RestaurantDBHelper(Context context) {
        super(context, DATABASE_RESTAURANTS, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(Create_Table_Restaurant);
        db.execSQL(Create_Table_DISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RestaurantDBHelper.class.getName(), "upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS restaurant");
        db.execSQL("DROP TABLE IF EXISTS dish");


        onCreate(db);
    }
}
