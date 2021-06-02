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

import com.beingknow.eatit2020.Client.Activities.Food_DetailActivity;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.FoodCategoryResponse;
import com.beingknow.eatit2020.Models.Category;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private LayoutInflater inflater;
    Context context;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    CardView cardView;
    private ArrayList<FoodCategoryResponse> categories = null;
    private ArrayList<Item> itemList = null;
    private ItemClickListener mOnItemClickInterface;

    public MenuAdapter(Context context, ArrayList<FoodCategoryResponse> categories, RecyclerView recyclerView, ItemClickListener mOnItemClickInterface) {
        this.context = context;
        this.categories = categories;
        this.recyclerView = recyclerView;
        this.mOnItemClickInterface = mOnItemClickInterface;
    }

    public MenuAdapter(Context context, ArrayList<FoodCategoryResponse> imageModelArrayList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.categories = imageModelArrayList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.menu_item, parent, false);
        MenuViewHolder holder = new MenuViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.foodName.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getServer_url_image())
                .fit()
                .into(holder.foodImage);

    }

    @Override
    public int getItemCount() {
        if(categories != null) {
            return categories.size();
        }
        return 0;
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
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            main = (RelativeLayout) itemView.findViewById(R.id.relative);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categories != null) {

                            System.out.println("Position: " + categories.get(getAdapterPosition()).getId());
                            Intent intent = new Intent(itemView.getContext(), Food_DetailActivity.class);
                            intent.putExtra(Intent.EXTRA_TEXT, categories.get(getAdapterPosition()).getId());
//                            
                            Toast.makeText(itemView.getContext(), "Position:" + categories.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(intent);

                            if (mOnItemClickInterface != null) {
                                mOnItemClickInterface.onClick(v, getAdapterPosition(), true);
                            }
                    }
                }
            });



        }
        }


    public void setItemClickListener(ItemClickListener mOnItemClickInterface){
        this.mOnItemClickInterface = mOnItemClickInterface;
    }
}
