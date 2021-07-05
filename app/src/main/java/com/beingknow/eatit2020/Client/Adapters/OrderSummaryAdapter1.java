package com.beingknow.eatit2020.Client.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.ModelResponse.CartDataResponse;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class OrderSummaryAdapter1 extends BaseAdapter
{
    private LayoutInflater inflater;
    Context context;
    private ListView listView;
    private DatabaseHelper databaseHelper;
    private ArrayList<Item1> cartList =  new ArrayList();
    public ArrayList<CartDataResponse> cartDataResponse = new ArrayList();

    public OrderSummaryAdapter1(Context context, ArrayList<CartDataResponse> cartDataResponse) {
       inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartDataResponse = cartDataResponse;
    }

    @Override
    public int getCount() {
        return this.cartDataResponse.size();
    }

    @Override
    public Object getItem(int position) {
        return cartDataResponse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail, parent, false);

        TextView name = convertView.findViewById(R.id.cart_name);
        TextView price = convertView.findViewById(R.id.cart_price);
        TextView quantity = convertView.findViewById(R.id.quantity);
        listView = convertView.findViewById(R.id.listview);

        CartDataResponse item1 = cartDataResponse.get(position);
        name.setText(item1.getName());
        quantity.setText(item1.getQuantity());
        price.setText(String.valueOf(item1.getPrice()));

        Intent intent = new Intent("message_subject_intent");
        intent.putExtra("name" , item1.getName());
        intent.putExtra("quantity" , item1.getQuantity());
        intent.putExtra("price" , item1.getPrice());


        return convertView;
    }
}
