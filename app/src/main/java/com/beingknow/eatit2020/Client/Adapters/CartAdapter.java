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
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<Order> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;

    public CartAdapter(Context context, ArrayList<Order> cartList, RecyclerView recyclerView) {
        this.context = context;
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public CartAdapter(Context context, ArrayList<Order> itemList) {
      //  inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_layout, parent, false);

        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Order order = cartList.get(position);
        holder.name.setText(order.getProductName());
        holder.quantity.setText("" + order.getQuantity());
        holder.price.setText("₹" + order.getPrice());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, price, quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
