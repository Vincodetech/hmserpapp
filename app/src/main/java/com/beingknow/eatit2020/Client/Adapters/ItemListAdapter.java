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
import com.beingknow.eatit2020.Client.Activities.Food_DetailActivity;
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
        public TextView name, price;
        public ImageView thumbnail;
        public RelativeLayout viewForeground;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            viewForeground = (RelativeLayout)itemView.findViewById(R.id.view_foreground);
           viewForeground.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (itemList != null) {

                       System.out.println("Position: " + itemList.get(getAdapterPosition()).getId());
                       Intent intent = new Intent(itemView.getContext(), FoodDetailsActivity.class);
                       intent.putExtra(Intent.EXTRA_TEXT, itemList.get(getAdapterPosition()).getId());

                       Toast.makeText(itemView.getContext(), "Position:" + itemList.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                       v.getContext().startActivity(intent);

                       if (mOnItemClickInterface != null) {
                           mOnItemClickInterface.onClick(v, getAdapterPosition(), true);
                       }
                   }
               }
           });

        }
    }


    public ItemListAdapter(Context context, ArrayList<Item> itemList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemList = itemList;
        this.recyclerView = recyclerView;
    }

    public ItemListAdapter(Context context, ArrayList<Item> itemList, RecyclerView recyclerView, ItemClickListener mOnItemClickInterface) {
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


        final Item item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("₹" + item.getPrice());

        Picasso.get().load(item.getServer_url_image())
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