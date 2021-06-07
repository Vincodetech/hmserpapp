package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Client.Activities.CartActivity;
import com.beingknow.eatit2020.Client.Activities.FoodDetailsActivity;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    double tot = 0.0;
    private ItemClickListener mOnItemClickInterface;
    private DatabaseHelper databaseHelper;

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
        tot = item.getPrice();
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
        public FloatingActionButton btnCart;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name1);
            quantity = itemView.findViewById(R.id.food_quantity1);
            description = itemView.findViewById(R.id.food_description);
            price = itemView.findViewById(R.id.food_price);
            thumbnail = itemView.findViewById(R.id.food_image1);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            databaseHelper = new DatabaseHelper(itemView.getContext());
            btnCart = (FloatingActionButton) itemView.findViewById(R.id.btn_cart);
            if(btnCart != null) {
                btnCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (cartList != null) {

                            System.out.println("Position: " + cartList.get(getAdapterPosition()).getId());
                            final Intent intent = new Intent(itemView.getContext(), CartActivity.class);
                            intent.putExtra(Intent.EXTRA_TEXT, cartList.get(getAdapterPosition()).getId());
//                            intent.putExtra(Intent.EXTRA_TEXT, cartList.get(getAdapterPosition()).getName());
//                            intent.putExtra(Intent.EXTRA_TEXT, cartList.get(getAdapterPosition()).getQuantity());
//                            intent.putExtra(Intent.EXTRA_TEXT, cartList.get(getAdapterPosition()).getPrice());
                            if(cartList.get(getAdapterPosition()).getId() > 0 )
                            {
                                databaseHelper.insertCart(name.getText().toString(),quantity.getText().toString(),price.getText().toString(),
                                        cartList.get(getAdapterPosition()).getId());
                            }
//                            if(databaseHelper.getAllreadyItem(cartList.get(getAdapterPosition()).getId()))
//                            {
//                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());
//                                alertDialog.setTitle("This Food Item is Already exist in Your Cart...!");
//                                alertDialog.setIcon(R.drawable.ic_baseline_warning_amber_24);
//
//                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        dialogInterface.dismiss();
//
//                                    }
//                                });
//
//                                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        dialogInterface.dismiss();
//                                    }
//                                });
//                                alertDialog.show();
//                            }
                            Toast.makeText(itemView.getContext(), "Added to Cart...!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(itemView.getContext(), "Position:" + cartList.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(intent);


                            if (mOnItemClickInterface != null) {
                                mOnItemClickInterface.onClick(v, getAdapterPosition(), true);
                            }
                        }
                    }
                });
            }


            if (plus != null)
            {
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String old_qty = quantity.getText().toString().trim();
                        String old_price = price.getText().toString().trim();
                        int qty = Integer.parseInt(old_qty);
                        double pr = Double.parseDouble(old_price);
                        qty++;
                        if(qty == 2) {
                            double pr1 = (qty * pr);
                            price.setText(String.valueOf(pr1));
                        }
                        else {
                            double pr1 = (qty * tot);
                            price.setText(String.valueOf(pr1));
                        }
                        quantity.setText(String.valueOf(qty));

                    }
                });
            }

            if(minus != null) {
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(quantity.getText().toString()) >= 1)
                        {
                            String old_qty = quantity.getText().toString().trim();
                            String old_price = price.getText().toString().trim();
                            int qty = Integer.parseInt(old_qty);
                            double pr = Double.parseDouble(old_price);
                            qty--;
                            if(qty < 1) {
                                qty = 1;
                            }
                            double pr1 = (qty * tot);
                            price.setText(String.valueOf(pr1));
                            quantity.setText(String.valueOf(qty));
                        }
                    }
                });
            }

        }
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
