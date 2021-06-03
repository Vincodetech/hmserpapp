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

import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;

import java.util.ArrayList;

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<Item> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;



    public ItemDetailAdapter(Context context, ArrayList<Item> cartList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public ItemDetailAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.cartList = itemList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_detail, parent, false);

        return new ItemDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Item item = cartList.get(position);
        holder.name.setText(item.getName());
        holder.quantity.setText(item.getQuantity());
      //  holder.description.setText(item.getDescription());
        String desc = Jsoup.parse(item.getDescription()).text();
        holder.description.setText(desc);
        holder.price.setText(String.valueOf(item.getPrice()));

//        Glide.with(context)
//                .load(item.getThumbnail())
//                .into(holder.thumbnail);
        Picasso.get().load(item.getServer_url_image())
                .fit()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {

        if(cartList != null) {
            return cartList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, description, price, quantity;
        public ImageView thumbnail,plus,minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name1);
            quantity = itemView.findViewById(R.id.food_quantity1);
            description = itemView.findViewById(R.id.food_description);
            price = itemView.findViewById(R.id.food_price);
            thumbnail = itemView.findViewById(R.id.food_image1);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);


            if (plus != null)
            {
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String q = quantity.getText().toString();
                        String p = price.getText().toString();
                        int qty = Integer.parseInt(q);
                        double pr = Double.parseDouble(p);
                        int qty1 = qty + 1;
                        double after_price = (qty1 * pr);
                        quantity.setText(String.valueOf(qty1));
                        price.setText(String.valueOf(after_price));
                    }
                });
             }

            if(minus != null) {
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(quantity.getText().toString()) > 0)
                        {
                            String q = quantity.getText().toString();
                            String p = price.getText().toString();
                            int qty = Integer.parseInt(q);
                            double pr = Double.parseDouble(p);
                            int qty1 = qty - 1;
                            double after_price = (qty1 * pr);
                            quantity.setText(String.valueOf(qty1));
                            price.setText(String.valueOf(after_price));

                        }
                        else if(Integer.parseInt(quantity.getText().toString()) == 0)
                        {
                            int qty1 = 1;
                            quantity.setText(String.valueOf(qty1));
                        }
                        else
                        {
                            int qty1 = 1;
                            quantity.setText(String.valueOf(qty1));
                        }
                    }
                });
            }
//            if(numberButton != null) {
//                numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String number = numberButton.getNumber();
//                        quantity.setText(number);
//                        int qty = Integer.parseInt(quantity.getText().toString());
//                        double price1 = Double.parseDouble(price.getText().toString());
//                        double after_price = (qty * price1);
//                        price.setText(Double.toString(after_price));
//
//                    }
//                });
//            }
        }
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
