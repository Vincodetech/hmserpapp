package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    RecyclerView recyclerView;
    CardView cardView;
    private ArrayList<Item1> cartList =  new ArrayList();
    double tot = 0.0;
    private DatabaseHelper databaseHelper;
    private static String total_sum = null;
    private ItemClickListener mOnItemClickInterface;

    public OrderSummaryAdapter(Context context, ArrayList<Item1> cartList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public OrderSummaryAdapter(Context context, ArrayList<Item1> itemList) {
        //  inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener mOnItemClickInterface) {
        this.mOnItemClickInterface = mOnItemClickInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

        public TextView name, price, quantity,total,cart_amount;
        public ImageView plus,minus;
        public Button btnContinue;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_name);
            price = itemView.findViewById(R.id.cart_price);
            quantity = itemView.findViewById(R.id.quantity);
            //  cart_amount = itemView.findViewById(R.id.cart_amount);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            total = itemView.findViewById(R.id.total);
            cardView = itemView.findViewById(R.id.cardview_total);

            btnContinue = (Button) itemView.findViewById(R.id.btn_continue);
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Continue Order", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
