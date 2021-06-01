package com.beingknow.eatit2020.Client.Adapters;

import android.app.Activity;
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
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Models.Item;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    private ArrayList<Item> itemList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;
    private ItemClickListener mOnItemClickInterface;

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
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(itemView.getContext() != null)
//                    {
//                        Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(itemView.getContext(), FoodDetailsActivity.class);
//                        v.getContext().startActivity(intent);
//                    }
//
//                }
//            });
//            Intent intent = getIntent();
//              String name = intent.getStringExtra("name");
//              String description = intent.getStringExtra("description");
//              String price = intent.getStringExtra("price");
//              String image  = intent.getStringExtra("server_url_image");


        }
    }


    public ItemListAdapter(Context context, ArrayList<Item> itemList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemList = itemList;
        this.recyclerView = recyclerView;
    }

    public ItemListAdapter(Context context, ArrayList<Item> itemList, RecyclerView recyclerView, ItemClickListener mOnItemClickInterface) {
//        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemList = itemList;
        this.recyclerView = recyclerView;
        this.mOnItemClickInterface = mOnItemClickInterface;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Intent intent = ((Activity) context).getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String server_url_image = intent.getStringExtra("server_url_image");

        final Item item = itemList.get(position);
        holder.name.setText(name);
        holder.description.setText(description);
        holder.price.setText("₹" + price);

        Picasso.get().load(server_url_image)
                .fit()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if(itemList != null) {
            return itemList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public CardView getCardView() {
        return cardView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

}