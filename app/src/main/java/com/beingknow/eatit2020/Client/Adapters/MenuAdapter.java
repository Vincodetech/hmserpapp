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

import com.beingknow.eatit2020.Client.Activities.Food_DetailActivity;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Category;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private LayoutInflater inflater;
    Context context;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    private ArrayList<Category> categories = new ArrayList();
    private ItemClickListener mOnItemClickInterface;

    public MenuAdapter(Context context, ArrayList<Category> categories, RecyclerView recyclerView, ItemClickListener mOnItemClickInterface) {
        this.context = context;
        this.categories = categories;
        this.recyclerView = recyclerView;
        this.mOnItemClickInterface = mOnItemClickInterface;
    }

    public MenuAdapter(Context context, ArrayList<Category> imageModelArrayList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.categories = imageModelArrayList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
//        return new MenuViewHolder(view);

        View view = inflater.inflate(R.layout.menu_item, parent, false);
        MenuViewHolder holder = new MenuViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.foodName.setText(categories.get(position).getName());
      //  Picasso.get().load(categories.get(position).getImage()).into(holder.foodImage);
        holder.foodImage.setImageResource(categories.get(position).getImage());


//        holder.foodImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(context != null) {
//                    v.getContext().startActivity(new Intent(v.getContext(), Food_DetailActivity.class));
//                }
//                }
//
//        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(context != null) {
//                    v.getContext().startActivity(new Intent(v.getContext(), Food_DetailActivity.class));
//                }
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage;
        TextView foodName;
        private RelativeLayout main;
        private RelativeLayout main1;
        public MenuViewHolder(@NonNull final View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.menu_name);
            foodImage = itemView.findViewById(R.id.menu_image);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler);
            main = (RelativeLayout) itemView.findViewById(R.id.relative);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    if(itemView.getContext() != null) {
                       Intent intent = new Intent(itemView.getContext(), Food_DetailActivity.class);
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


    public void setItemClickListener(ItemClickListener mOnItemClickInterface){
        this.mOnItemClickInterface = mOnItemClickInterface;
    }
}
