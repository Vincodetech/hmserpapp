package com.beingknow.eatit2020.Client.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.beingknow.eatit2020.NavFragment.DeliveryFragment;
import com.beingknow.eatit2020.NavFragment.TakeawayFragment;

public class OrderTypeAdapter extends FragmentPagerAdapter
{
    private Context myContext;
    int totalTabs;

    public OrderTypeAdapter(Context context, @NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TakeawayFragment takeawayFragment = new TakeawayFragment();
                return takeawayFragment;
            case 1:
                DeliveryFragment deliveryFragment = new DeliveryFragment();
                return deliveryFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
