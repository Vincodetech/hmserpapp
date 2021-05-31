package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.ModelResponse.SliderData;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private LayoutInflater inflater;
    private Context context;
    private final ArrayList<SliderData> mSliderItems;

    // Constructor
    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList) {
       this.context = context;
        this.mSliderItems = sliderDataArrayList;
    }



    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.slider_layout, parent, false);
        SliderAdapter.SliderAdapterViewHolder holder = new SliderAdapter.SliderAdapterViewHolder(view);

        return holder;
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

      //  final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Picasso.get().load(mSliderItems.get(position).getImgUrl())
                .fit()
                .into(viewHolder.imageViewBackground);
//        Glide.with(viewHolder.itemView)
//                .load(sliderItem.getImgUrl())
//                .fitCenter()
//                .into(viewHolder.imageViewBackground);
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        if(mSliderItems != null) {
            return mSliderItems.size();
        }
        return 0;
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;
        }
    }
}