package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Activities.FoodDetailsActivity;
import com.beingknow.eatit2020.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Models.Item;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    private ArrayList<Item> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewForeground;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            viewForeground = (RelativeLayout)itemView.findViewById(R.id.view_foreground);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemView.getContext() != null)
                    {
                        Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(itemView.getContext(), FoodDetailsActivity.class);
                        v.getContext().startActivity(intent);
                    }

                }
            });

        }
    }


    public ItemListAdapter(Context context, ArrayList<Item> cartList, RecyclerView recyclerView) {
        this.context = context;
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public ItemListAdapter(Context context, ArrayList<Item> itemList) {
//        inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartList = itemList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.food_list, parent, false);
//
//        return new MyViewHolder(itemView);

//        View view = inflater.inflate(R.layout.food_list, parent, false);
//        MyViewHolder holder = new MyViewHolder(view);
//
//        return holder;

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Item item = cartList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText("₹" + item.getPrice());

//        Glide.with(context)
//                .load(item.getThumbnail())
//                .into(holder.thumbnail);
        holder.thumbnail.setImageResource(item.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Item item, int position) {
        cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}