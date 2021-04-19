package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Client.Activities.CafeDetailActivity;
import com.beingknow.eatit2020.Client.Activities.CafeDetailsActivity;
import com.beingknow.eatit2020.Models.CafeItem;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class CafeItemAdapter extends RecyclerView.Adapter<CafeItemAdapter.MyViewHolder>
{
    Context context;
    LayoutInflater inflater;
    private ArrayList<CafeItem> cartList =  new ArrayList();
    RecyclerView recyclerView;
    CardView cardView;

    public CafeItemAdapter(Context context, ArrayList<CafeItem> cartList, RecyclerView recyclerView) {
        this.context = context;
        this.cartList = cartList;
        this.recyclerView = recyclerView;
    }

    public CafeItemAdapter(Context context, ArrayList<CafeItem> itemList) {
//        inflater = LayoutInflater.from(context);
        this.context = context;
        this.cartList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cafe_list, parent, false);

        return new CafeItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CafeItem item = cartList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText("₹" + item.getPrice());

//        Glide.with(context)
//                .load(item.getThumbnail())
//                .into(holder.thumbnail);
        holder.thumbnail.setImageResource(item.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewForeground;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cafe_name);
            description = itemView.findViewById(R.id.cafe_description);
            price = itemView.findViewById(R.id.cafe_price);
            thumbnail = itemView.findViewById(R.id.cafe_thumbnail);
            viewForeground = (RelativeLayout)itemView.findViewById(R.id.cafe_view_foreground);
            cardView = (CardView)itemView.findViewById(R.id.cafe_cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemView.getContext() != null)
                    {
                        Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(itemView.getContext(), CafeDetailsActivity.class);
                        v.getContext().startActivity(intent);
                    }

                }
            });

        }
    }
}
