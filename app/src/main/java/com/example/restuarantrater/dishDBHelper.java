package com.example.restuarantrater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dishDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_DISH = "mydishes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String Create_Table_DISH =
            "create table dish (dishID integer primary key autoincrement, " +
                    "name text not null, type text, " +
                    "rating text, restaurantID int foreign key);";

    public dishDBHelper(Context context) {
        super(context, DATABASE_DISH, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate (SQLiteDatabase db) {db.execSQL(Create_Table_DISH);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(dishDBHelper.class.getName(), "upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS dish");
        onCreate(db);
    }

}
