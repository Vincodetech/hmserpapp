package com.beingknow.eatit2020.Client.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Database.DatabaseHelper;
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

    public OrderSummaryAdapter1(Context context, ArrayList<Item1> cartList) {
       inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return this.cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(R.layout.order_detail, parent, false);

        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail, parent, false);

        TextView name = convertView.findViewById(R.id.cart_name);
        TextView price = convertView.findViewById(R.id.cart_price);
        TextView quantity = convertView.findViewById(R.id.quantity);
        listView = convertView.findViewById(R.id.listview);

        Item1 item1 = cartList.get(position);
        name.setText(item1.getName());
        quantity.setText(item1.getQuantity());
        price.setText(String.valueOf(item1.getPrice()));

//        final View finalConvertView = convertView;
//        listView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(finalConvertView.getContext());
//                alertDialog.setTitle("Are You Want To Sure Delete Your Cart Item...!");
//                alertDialog.setIcon(R.drawable.ic_baseline_delete_24);
//
//                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        cartList.remove(position);
//                        databaseHelper.deleteCartItem(position);
//                       // notifyItemRemoved(position);
//                        notifyDataSetChanged();
//                        Toast.makeText(context, "Cart Item Deleted Successfully...!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        dialogInterface.dismiss();
//                    }
//                });
//                alertDialog.show();
//                return false;
//            }
//        });


        return convertView;
    }
}
