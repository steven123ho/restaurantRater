package com.example.restuarantrater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.WatchEvent;

public class RateDish extends AppCompatActivity {

    private dish currentDish;
    private int restaurantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_dish);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initDish(extras.getInt("dishID"));
        } else {
            currentDish = new dish();
        }

        initTextChangedEvents();
        initSubmitButton();
        initBack();
    }

    private void initSubmitButton () {

        Button submitButton = findViewById(R.id.submitBtn);
        TextView results = findViewById(R.id.resultsLabel);
        EditText type = findViewById(R.id.dishTypeInput);
        EditText name = findViewById(R.id.dishNameInput);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful;
                restaurantDataSource ds = new restaurantDataSource(RateDish.this);
                try {
                    ds.open();
                    if(currentDish.getDishID() == -1) {
                        currentDish.setRestaurantID(getRestaurant());
                        wasSuccessful = ds.insertDish(currentDish);
                        results.setText("Your meal has been added");
                        name.setText("");
                        type.setText("");
                    }
                    ds.close();
                } catch (Exception e) {
                    wasSuccessful = false;
                }
            }
        });
    }


    private void initTextChangedEvents() {

        final EditText etName = findViewById(R.id.dishNameInput);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentDish.setName(etName.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                // autogenerated method for Text Watcher
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // autogenerated method for Text Watcher
            }
        });

        final EditText etType = findViewById(R.id.dishTypeInput);
        etType.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentDish.setType(etType.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                // autogenerated method for Text Watcher
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // autogenerated method for Text Watcher
            }
        });

        final RatingBar etRating = findViewById(R.id.ratingBar);
        etRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                currentDish.setRating(String.valueOf(ratingBar.getRating()));
            }
        });

        currentDish.setRestaurantID(restaurantID);

    }

    private void initBack() {
        TextView home = findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RateDish.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //clears the stack trace
                startActivity(intent);
            }
        });
    }

    private int getRestaurant(){
        Intent intent = getIntent();
        if (intent != null) {
                restaurantID = intent.getIntExtra("currentRestaurant", 0);
        }
        return restaurantID;
    }

    //Gets specific contact from ContactDataSource and uses it to populate the fields
    private void initDish (int id) {
        restaurantDataSource ds = new restaurantDataSource(RateDish.this);
        try {
            ds.open();
            currentDish = ds.getSpecificDish(id);
            ds.close();
        } catch (Exception e) {
            Toast.makeText(this,"Load Dishes Failed", Toast.LENGTH_LONG).show();
        }

        EditText editName = findViewById(R.id.dishNameInput);
        EditText editType = findViewById(R.id.dishTypeInput);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        editName.setText(currentDish.getName());
        editType.setText(currentDish.getType());
        ratingBar.setRating(Float.valueOf(currentDish.getRating()));
    }


}