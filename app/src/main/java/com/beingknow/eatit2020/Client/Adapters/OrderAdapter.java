package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<Item1> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;
    private ItemClickListener mOnItemClickInterface;

    public OrderAdapter(Context context, ArrayList<Item1> cartList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public OrderAdapter(Context context, ArrayList<Item1> itemList) {
        //  inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartList = itemList;
    }


    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_layout, parent, false);

        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        final Item1 item = cartList.get(position);
//        holder.name.setText(order.getProductName());
//        holder.quantity.setText("" + order.getQuantity());
//        holder.price.setText("₹" + order.getPrice());
        holder.name.setText(item.getName());
        holder.quantity.setText(item.getQuantity());
        holder.price.setText(String.valueOf(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        if(cartList != null) {
            return cartList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, quantity,total;
        public ImageView plus,minus;


        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_name);
            price = itemView.findViewById(R.id.cart_price);
            quantity = itemView.findViewById(R.id.quantity);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            total = itemView.findViewById(R.id.total);
           // cardView = itemView.findViewById(R.id.cardview_total);

        }
    }
}
