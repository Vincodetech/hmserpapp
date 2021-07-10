package com.beingknow.eatit2020.Client.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Client.Activities.CartActivity;
import com.beingknow.eatit2020.Client.Activities.FoodDetailsActivity;
import com.beingknow.eatit2020.Client.Activities.OrderStatusActivity;
import com.beingknow.eatit2020.Client.Activities.OrderTypeActivity;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.CartDataResponse;
import com.beingknow.eatit2020.ModelResponse.DeleteCartDataResponse;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.NavFragment.DeliveryFragment;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    public CartAdapter cartAdapter;
    public ArrayList<Item1> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;
    public ArrayList<CartDataResponse> cartDataResponse = new ArrayList();
    CheckBox checkBox;
    ImageView imageView;
    double tot = 0.0;
    public DatabaseHelper databaseHelper;
    public static String total_sum = null;
    public ItemClickListener mOnItemClickInterface;
    public SharedPrefManager sharedPrefManager;


    public CartAdapter(Context context, ArrayList<CartDataResponse> cartDataResponse, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.cartDataResponse = cartDataResponse;
        this.recyclerView = recyclerView;
    }

    public CartAdapter(Context context, ArrayList<CartDataResponse> cartDataResponse) {
      //  inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartDataResponse = cartDataResponse;
    }
    public void setOnItemClickListener(ItemClickListener mOnItemClickInterface) {
        this.mOnItemClickInterface = mOnItemClickInterface;
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

        final CartDataResponse item = cartDataResponse.get(position);

        holder.name.setText(item.getName());
        holder.quantity.setText(item.getQuantity());
        holder.price.setText(String.valueOf(item.getAmount()));
     //   holder.cart_amount.setText(String.valueOf(item.getPrice()));
        tot = item.getAmount();

        Intent intent = new Intent("message_subject_intent");
        intent.putExtra("quantity" , item.getQuantity());
        intent.putExtra("price" , item.getPrice());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        if(cartDataResponse != null)
        {
            return cartDataResponse.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, price, quantity,total,cart_amount;
        public ImageView plus,minus;
        public Button btnPlace;
       // final long sum = databaseHelper.sum_Of_Price();

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
            sharedPrefManager = new SharedPrefManager(itemView.getContext());
            btnPlace = (Button) itemView.findViewById(R.id.btn_place_order);


            cardView = (CardView) itemView.findViewById(R.id.card_myevent);

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());
                    alertDialog.setTitle("Are You Want To Sure Delete Your Cart Item...!");
                    alertDialog.setIcon(R.drawable.ic_baseline_delete_24);

                    alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteCartData();
                            cartDataResponse.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            Toast.makeText(itemView.getContext(), "Cart Item Deleted Successfully...!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                    return false;
                }
            });

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

        public void deleteCartData()
        {
            final ProgressDialog mDialog = new ProgressDialog(itemView.getContext());
            mDialog.setMessage("Please Waiting...");
            mDialog.show();

            Call<DeleteCartDataResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .deletecartdata(cartDataResponse.get(getAdapterPosition()).getId());

            call.enqueue(new Callback<DeleteCartDataResponse>() {
                @Override
                public void onResponse(Call<DeleteCartDataResponse> call, Response<DeleteCartDataResponse> response) {
                    DeleteCartDataResponse deleteCartDataResponse = response.body();

                    if(response.isSuccessful())
                    {
                        if (deleteCartDataResponse != null)
                        {
                            mDialog.dismiss();
                            new SweetAlertDialog(
                                    itemView.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Cart")
                                    .setContentText("Delete Cart Data Successfully...!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
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
                public void onFailure(Call<DeleteCartDataResponse> call, Throwable t) {
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
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    public boolean isAlreadyInCart(int targetItemId) {
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
