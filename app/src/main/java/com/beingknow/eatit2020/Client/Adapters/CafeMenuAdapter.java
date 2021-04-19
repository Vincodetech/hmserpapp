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
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Client.Activities.CafeDetailActivity;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.CafeCategory;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class CafeMenuAdapter extends RecyclerView.Adapter<CafeMenuAdapter.CafeMenuViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    private ArrayList<CafeCategory> category = new ArrayList();
    private ItemClickListener mOnItemClickInterface;

    public CafeMenuAdapter(Context context, ArrayList<CafeCategory> category, ItemClickListener mOnItemClickInterface) {
        this.context = context;
        this.category = category;
        this.mOnItemClickInterface = mOnItemClickInterface;
    }

    public CafeMenuAdapter(Context context, ArrayList<CafeCategory> imageModelArrayList) {
        inflater = LayoutInflater.from(context);
        this.category = imageModelArrayList;
    }


    @NonNull
    @Override
    public CafeMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.food_item, parent, false);
        CafeMenuAdapter.CafeMenuViewHolder holder = new CafeMenuAdapter.CafeMenuViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CafeMenuAdapter.CafeMenuViewHolder holder, int position) {
        holder.foodName.setText(category.get(position).getName());
        //  Picasso.get().load(categories.get(position).getImage()).into(holder.foodImage);
        holder.foodImage.setImageResource(category.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class CafeMenuViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage;
        TextView foodName;
        RelativeLayout main1;
        public CafeMenuViewHolder(@NonNull final View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodImage = itemView.findViewById(R.id.food_image);

            main1 = (RelativeLayout) itemView.findViewById(R.id.relative1);
            main1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    if(itemView.getContext() != null) {
                        Intent intent = new Intent(itemView.getContext(), CafeDetailActivity.class);
                        intent.putExtra("ItemPosition", getPosition());
//                        context.startActivity(intent);
                        v.getContext().startActivity(intent);

                    }
                    if(mOnItemClickInterface !=null){
                        mOnItemClickInterface.onClick(v,getAdapterPosition(),true);
                    }
                }
            });

        }

    }


}
