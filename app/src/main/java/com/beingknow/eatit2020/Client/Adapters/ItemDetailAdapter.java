package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<Item> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;


    public ItemDetailAdapter(Context context, ArrayList<Item> cartList, RecyclerView recyclerView) {
        this.context = context;
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public ItemDetailAdapter(Context context, ArrayList<Item> itemList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartList = itemList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_food_details, parent, false);

        return new ItemDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Item item = cartList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText("₹" + item.getPrice());

//        Glide.with(context)
//                .load(item.getThumbnail())
//                .into(holder.thumbnail);
        Picasso.get().load(cartList.get(position).getServer_url_image())
                .fit()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {

        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, description, price;
        public ImageView thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
            description = itemView.findViewById(R.id.food_description);
            price = itemView.findViewById(R.id.food_price);
            thumbnail = itemView.findViewById(R.id.food_image);
        }
    }
}
