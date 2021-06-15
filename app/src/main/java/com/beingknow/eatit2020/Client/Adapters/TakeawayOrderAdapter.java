package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.OrderResponse;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class TakeawayOrderAdapter extends RecyclerView.Adapter<TakeawayOrderAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<OrderResponse> orderlist =  new ArrayList();
    RecyclerView recyclerView;
    private ItemClickListener mOnItemClickInterface;

    public TakeawayOrderAdapter(Context context, ArrayList<OrderResponse> orderlist, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.orderlist = orderlist;
        this.recyclerView = recyclerView;
    }

    public TakeawayOrderAdapter(Context context, ArrayList<OrderResponse> orderlist) {
        //  inflater = LayoutInflater.from(context);
        this.context = context;
        this.orderlist = orderlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.takeaway_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final OrderResponse orderResponse = orderlist.get(position);
        holder.order_no.setText(orderResponse.getOrder_no());
    }

    @Override
    public int getItemCount() {
        if(orderlist != null) {
            return orderlist.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView order_no;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            order_no = itemView.findViewById(R.id.order_no);
        }
    }
}
