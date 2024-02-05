package com.example.restuarantrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter{


    private ArrayList<dish> dishData;
    private View.OnClickListener mOnClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public class DishViewHolder extends RecyclerView.ViewHolder {
        public TextView textDish;
        public TextView textType;
        public Button deleteButton;
        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            textDish = (TextView) itemView.findViewById(R.id.dishName);
            textType = (TextView) itemView.findViewById(R.id.dishType);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnClickListener);
        }

        public TextView getDishName() {
            return textDish;
        }
        public TextView getType() {return textType;}
        public Button getDeleteButton() {return deleteButton;}
    }

    public DishAdapter (ArrayList<dish> arrayList, Context context) {
        dishData = arrayList;
        parentContext = context;
    }

    public void setmOnClickListener(View.OnClickListener itemClickListener) {
        mOnClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new DishViewHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, final int position) {
        DishViewHolder dvh = (DishViewHolder) holder;
        dvh.getDishName().setText((dishData.get(position)).getName());
        dvh.getType().setText(dishData.get(position).getType());

        if (isDeleting) {
            dvh.getDeleteButton().setVisibility(View.VISIBLE);
            dvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    deleteItem(holder.getAdapterPosition());
                }
            });
        } else {
            dvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }

    public void setDelete(boolean b) {
        isDeleting = b;
    }

    private void deleteItem(int position) {
        dish dish = dishData.get(position);
        restaurantDataSource ds = new restaurantDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteDish(dish.getDishID());
            ds.close();
            if (didDelete) {
                dishData.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return dishData.size();
    }


}
