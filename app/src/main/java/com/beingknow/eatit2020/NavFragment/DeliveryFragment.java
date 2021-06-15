package com.beingknow.eatit2020.NavFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Client.Adapters.MenuAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderTypeAdapter;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliveryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemClickListener mOnItemClickInterface;
    private OrderTypeAdapter orderTypeAdapter;
    private OrderAdapter orderAdapter;
    private ArrayList<Item1> itemArrayList;
    private DatabaseHelper databaseHelper;
    private ImageView plus, minus;



    public DeliveryFragment() {

    }



    public static DeliveryFragment newInstance(String param1) {
        DeliveryFragment fragment = new DeliveryFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        // Inflate the layout for this fragment


        databaseHelper = new DatabaseHelper(getContext());
        recyclerView =(RecyclerView) view.findViewById(R.id.listCart);


        itemArrayList = new ArrayList<>();

        if(getActivity() != null) {
            showFoodDelivery();

          //  recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
        
        return view;
    }

    public void showFoodDelivery()
    {


    }
}