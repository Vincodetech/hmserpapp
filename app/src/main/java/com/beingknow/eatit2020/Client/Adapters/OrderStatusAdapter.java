package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.Models.Request;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<Request> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;
    private ItemClickListener itemClickListener;

    public OrderStatusAdapter(Context context, ArrayList<Request> cartList, RecyclerView recyclerView) {
        this.context = context;
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public OrderStatusAdapter(Context context, ArrayList<Request> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_layout, parent, false);

        return new OrderStatusAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Request request = cartList.get(position);
        holder.txtOrderID.setText("" + request.getOrderid());
        holder.txtOrderStatus.setText(request.getStatus());
        holder.txtOrderPhone.setText("" + request.getPhone());
        holder.txtOrderAddress.setText(request.getAddress());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtOrderID, txtOrderStatus, txtOrderPhone, txtOrderAddress;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtOrderID = itemView.findViewById(R.id.order_id);
            txtOrderStatus = itemView.findViewById(R.id.order_status);
            txtOrderPhone = itemView.findViewById(R.id.order_phone);
            txtOrderAddress = itemView.findViewById(R.id.order_address);

            cardView = (CardView) itemView.findViewById(R.id.order_card);
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());
                    alertDialog.setTitle("Are You Want To Sure Delete Order Summary...!");
                    alertDialog.setIcon(R.drawable.ic_baseline_delete_24);

                    alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            cartList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            Toast.makeText(context, "Order Summary Deleted Successfully...!", Toast.LENGTH_SHORT).show();
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

        }

    }
//    private String convertCodeToStatus(String status) {
//
//        if (status.equals("0"))
//            return "Your Order has Placed...!";
//        else if (status.equals("1"))
//            return "Your Order is on the Way...!";
//        else
//            return "Your Order has Shipped...!";
//    }
}
