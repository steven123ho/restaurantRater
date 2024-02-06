package com.example.restuarantrater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class dishList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);

        initButton();

        restaurantDataSource ds = new restaurantDataSource(this);
        ArrayList<dish> dishes;

        try {
            ds.open();
            dishes = ds.getDishes();
            ds.close();
            RecyclerView dishList;
            DishAdapter dishAdapter = new DishAdapter(dishes, dishList.this);

            dishList = findViewById(R.id.rvDishes);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            dishList.setLayoutManager(layoutManager);
            dishList.setAdapter(dishAdapter);


            // Added the ItemClickListener here for the class contact to have been initialized
            //Book said to add it before the onCreate method
            View.OnClickListener onItemClickListener = new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                    int position = viewHolder.getAdapterPosition();
                    int dishId = dishes.get(position).getDishID();
                    Intent intent = new Intent(dishList.this, RateDish.class);
                    intent.putExtra("dishID", dishId);
                    startActivity(intent);
                }
            };
            dishAdapter.setmOnClickListener(onItemClickListener);

            //Switch turns delete buttons on and off
            //Book did not say to put here but the contactAdapter is here
            Switch s = findViewById(R.id.switchDelete);
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    Boolean status = compoundButton.isChecked();
                    dishAdapter.setDelete(status);
                    dishAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e){
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }

    private void initButton() {
        Button addButton = findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(dishList.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //clears the stack trace
                startActivity(intent);
            }
        });
    }

}