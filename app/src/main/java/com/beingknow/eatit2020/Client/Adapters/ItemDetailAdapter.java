package com.beingknow.eatit2020.Client.Adapters;

import android.app.ProgressDialog;
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
import com.beingknow.eatit2020.ModelResponse.CartDataResponse;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<Item> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;
    double tot = 0.0;
    private SharedPrefManager sharedPrefManager;
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
        holder.amount.setText(String.valueOf(item.getPrice()));
        tot = item.getPrice();

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
        public TextView name, description, price, quantity, amount;
        public ImageView thumbnail,plus,minus;
        public FloatingActionButton btnCart;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name1);
            quantity = itemView.findViewById(R.id.food_quantity1);
            description = itemView.findViewById(R.id.food_description);
            price = itemView.findViewById(R.id.food_price);
            amount = itemView.findViewById(R.id.food_amount1);
            thumbnail = itemView.findViewById(R.id.food_image1);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            databaseHelper = new DatabaseHelper(itemView.getContext());
            sharedPrefManager = new SharedPrefManager(itemView.getContext());
            btnCart = (FloatingActionButton) itemView.findViewById(R.id.btn_cart);

            if(btnCart != null)
            {
                btnCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if(cartList != null)
                        {
                            final ProgressDialog mDialog = new ProgressDialog(itemView.getContext());
                            mDialog.setMessage("Please Waiting...");
                            mDialog.show();

                            Call<CartDataResponse> call = RetrofitClient
                                    .getInstance()
                                    .getApi()
                                    .addcartdata(name.getText().toString(),quantity.getText().toString(),Double.parseDouble(price.getText().toString()),
                                            Double.parseDouble(amount.getText().toString()),cartList.get(getAdapterPosition()).getId(),sharedPrefManager.getUser().getId(),1);

                            call.enqueue(new Callback<CartDataResponse>() {
                                @Override
                                public void onResponse(Call<CartDataResponse> call, Response<CartDataResponse> response) {
                                    CartDataResponse cartDataResponse = response.body();

                                    if(response.isSuccessful())
                                    {
                                        if (cartDataResponse != null)
                                        {
                                            // sharedPrefManager.saveOrder(orderResponse1);
                                            mDialog.dismiss();
                                            new SweetAlertDialog(
                                                    itemView.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Cart")
                                                    .setContentText("Added to Cart Successfully...!")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            Intent intent = new Intent(itemView.getContext(), CartActivity.class);
                                                            intent.putExtra(Intent.EXTRA_TEXT, cartList.get(getAdapterPosition()).getId());
                                                            Toast.makeText(itemView.getContext(), "Added to Cart Successfully...!", Toast.LENGTH_SHORT).show();
                                                            v.getContext().startActivity(intent);
                                                            sweetAlertDialog.dismiss();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(itemView.getContext(),"Not Respond",Toast.LENGTH_SHORT).show();
                                        mDialog.dismiss();
                                        new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Something Went Wrong!")
                                                .show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<CartDataResponse> call, Throwable t) {
                                    Toast.makeText(itemView.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                                    mDialog.dismiss();
                                    new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Something Went Wrong!")
                                            .show();
                                }
                            });
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
                            amount.setText(String.valueOf(pr1));
                        }
                        else {
                            double pr1 = (qty * tot);
                            amount.setText(String.valueOf(pr1));
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
                            String old_price = amount.getText().toString().trim();
                            int qty = Integer.parseInt(old_qty);
                            double pr = Double.parseDouble(old_price);
                            qty--;
                            if(qty < 1) {
                                qty = 1;
                            }
                            double pr1 = (qty * tot);
                            amount.setText(String.valueOf(pr1));
                            quantity.setText(String.valueOf(qty));
                        }
                    }
                });
            }

        }
        private boolean isAlreadyInCart(int targetItemId) {
            boolean isAlreadyInCart = false;
            for (int i = 0; i < cartList.size(); i++) {
                if (targetItemId == cartList.get(i).getId()) {
                    isAlreadyInCart = true;
                    break;
                }
            }
            return isAlreadyInCart;
        }
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
