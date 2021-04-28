package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Client.Activities.CartActivity;
import com.beingknow.eatit2020.Client.Activities.OrderStatusActivity;
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
    CheckBox checkBox;
    ImageView imageView;

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

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            imageView = (ImageView) itemView.findViewById(R.id.delete);
            checkBox = (CheckBox) itemView.findViewById(R.id.chk_selectitem);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                cartList.remove(getAdapterPosition());
//                                notifyItemRemoved(getAdapterPosition());
//                                Toast.makeText(context, "Item Deleted Successfully...!", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());
                                alertDialog.setTitle("Are You Want To Sure Delete Cart Item...!");
                                alertDialog.setIcon(R.drawable.ic_baseline_delete_24);

                                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        cartList.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                        Toast.makeText(context, "Item Deleted Successfully...!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });
                                alertDialog.show();

                            }
                        });
                    }
                }
            });
        }
    }
}