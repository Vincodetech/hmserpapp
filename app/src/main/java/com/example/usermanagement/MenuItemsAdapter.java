package com.example.usermanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class MenuItemsAdapter<MenuItems> extends RecyclerView.Adapter<MenuItemsAdapter.MenuItemsViewHolder> {
    private Context context;
    private List<MenuItems> menuItemsList;

    public MenuItemsAdapter(Context context, List<MenuItems> menuItemsList) {
        this.context = context;
        this.menuItemsList = menuItemsList;
    }

    @NonNull
    @Override
    public MenuItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitems_layout, null);
        return new MenuItemsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemsViewHolder holder, int position) {
        holder.textView.setText(menuItemsList.get(position).toString());
        Glide.with(context).load(menuItemsList.get(position).toString()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return menuItemsList.size();
    }

    public static class MenuItemsViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
            textView = itemView.findViewById(R.id.textView6);

        }
    }
}
