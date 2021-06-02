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
import com.beingknow.eatit2020.Client.Activities.Food_DetailActivity;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.CafeCategory;
import com.beingknow.eatit2020.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CafeMenuAdapter extends RecyclerView.Adapter<CafeMenuAdapter.CafeMenuViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    RecyclerView recyclerView1;
    private ArrayList<CafeCategory> category = new ArrayList();
    private ItemClickListener mOnItemClickInterface;

    public CafeMenuAdapter(Context context, ArrayList<CafeCategory> category,  RecyclerView recyclerView1, ItemClickListener mOnItemClickInterface) {
        this.context = context;
        this.category = category;
        this.recyclerView1 = recyclerView1;
        this.mOnItemClickInterface = mOnItemClickInterface;
    }

    public CafeMenuAdapter(Context context, ArrayList<CafeCategory> imageModelArrayList, RecyclerView recyclerView1) {
        inflater = LayoutInflater.from(context);
        this.category = imageModelArrayList;
        this.recyclerView1 = recyclerView1;
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
        Picasso.get().load(category.get(position).getServer_url_image())
                .fit()
                .into(holder.foodImage);

    }

    @Override
    public int getItemCount() {
        if(category != null) {
            return category.size();
        }
        return 0;
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
                    if (category != null) {

                        System.out.println("Position: " + category.get(getAdapterPosition()).getId());
                        Intent intent = new Intent(itemView.getContext(), Food_DetailActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, category.get(getAdapterPosition()).getId());

                        Toast.makeText(itemView.getContext(), "Position:" + category.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(intent);

                        if (mOnItemClickInterface != null) {
                            mOnItemClickInterface.onClick(v, getAdapterPosition(), true);
                        }
                    }
                }
            });

        }

    }


}
