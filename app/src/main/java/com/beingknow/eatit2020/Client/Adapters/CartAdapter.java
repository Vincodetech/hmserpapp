package com.beingknow.eatit2020.Client.Adapters;

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
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Client.Activities.CartActivity;
import com.beingknow.eatit2020.Client.Activities.FoodDetailsActivity;
import com.beingknow.eatit2020.Client.Activities.OrderStatusActivity;
import com.beingknow.eatit2020.Client.Activities.OrderTypeActivity;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.NavFragment.DeliveryFragment;
import com.beingknow.eatit2020.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<Item1> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;
    CheckBox checkBox;
    ImageView imageView;
    double tot = 0.0;
    private DatabaseHelper databaseHelper;
    private static String total_sum = null;
    private ItemClickListener mOnItemClickInterface;

    public CartAdapter(Context context, ArrayList<Item1> cartList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public CartAdapter(Context context, ArrayList<Item1> itemList) {
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
                .inflate(R.layout.cart_layout, parent, false);

        return new CartAdapter.MyViewHolder(itemView);
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
     //   holder.cart_amount.setText(String.valueOf(item.getPrice()));
        tot = item.getPrice();
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

            btnPlace = (Button) itemView.findViewById(R.id.btn_place_order);
//            if(btnPlace != null) {
//                btnPlace.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (itemView.getContext() != null) {      // check if activity not null
//                            if (cartList != null) {
//                                System.out.println("Position in cart: " + cartList.get(getAdapterPosition()).getId());
//                                final Intent intent = new Intent(itemView.getContext(), OrderTypeActivity.class);
//                                intent.putExtra("id", cartList.get(getAdapterPosition()).getId());
//                                Toast.makeText(itemView.getContext(), "Place Order...!", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(itemView.getContext(), "Position in cart:" + cartList.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
//                                v.getContext().startActivity(intent);
//
//                                if (mOnItemClickInterface != null) {
//                                    mOnItemClickInterface.onClick(itemView, getAdapterPosition(), true);
//                                }
//                            }
//                        }
//                    }
//                });
//            }

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

                            cartList.remove(getAdapterPosition());
                            databaseHelper.deleteCartItem(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            Toast.makeText(context, "Cart Item Deleted Successfully...!", Toast.LENGTH_SHORT).show();
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
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
