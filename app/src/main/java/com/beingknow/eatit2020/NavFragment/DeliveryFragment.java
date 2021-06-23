package com.beingknow.eatit2020.NavFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Activities.OrderSummaryActivity;
import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Client.Adapters.MenuAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderTypeAdapter;
import com.beingknow.eatit2020.Client.Adapters.TakeawayOrderAdapter;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.OrderResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse1;
import com.beingknow.eatit2020.ModelResponse.OrderResponse2;
import com.beingknow.eatit2020.ModelResponse.UpdateAddressResponse;
import com.beingknow.eatit2020.ModelResponse.UpdateProfileResponse;
import com.beingknow.eatit2020.ModelResponse.UserProfileResponse;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliveryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemClickListener mOnItemClickInterface;
    private TakeawayOrderAdapter takeawayOrderAdapter;
    private ArrayList<OrderResponse> orderResponses;
    private OrderResponse orderResponse;
    private OrderResponse2 orderResponse2;
    private OrderResponse1 orderResponse1;
    private TextView order_no_text, order_no, order_type_text, order_type,shipping_address_text;
    private TextInputLayout street1, street2, city, state, country, pincode;
    private Button btn_checkout;
    private SharedPrefManager sharedPrefManager;
    private int oid = 0;



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
        order_no_text = (TextView) view.findViewById(R.id.order_no_text);
        order_no = (TextView) view.findViewById(R.id.order_no);
        order_type_text = (TextView) view.findViewById(R.id.order_type_text);
        order_type = (TextView) view.findViewById(R.id.order_type);
        shipping_address_text = (TextView) view.findViewById(R.id.shipping_address_text);
        street1 = (TextInputLayout)view.findViewById(R.id.txtStreet1);
        street2 = (TextInputLayout)view.findViewById(R.id.txtStreet2);
        city = (TextInputLayout)view.findViewById(R.id.txtCity);
        state = (TextInputLayout)view.findViewById(R.id.txtState);
        country = (TextInputLayout)view.findViewById(R.id.txtCountry);
        pincode = (TextInputLayout)view.findViewById(R.id.pincode);
        recyclerView = (RecyclerView) view.findViewById(R.id.listCart);
        btn_checkout = (Button) view.findViewById(R.id.btn_checkout);
        sharedPrefManager = new SharedPrefManager(getContext());
        orderResponses = new ArrayList<>();



        if(getActivity() != null) {

            showFoodDelivery();
            fetchUserAddress();
        }

        if(btn_checkout != null)
        {
            btn_checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkoutOrder();
                    updateUserAddress();
                }
            });

        }
        
        return view;
    }

    private void updateUserAddress()
    {
        String add1 = street1.getEditText().getText().toString().trim();
        String add2 = street2.getEditText().getText().toString().trim();
        String usercity = city.getEditText().getText().toString().trim();
        String userstate = state.getEditText().getText().toString().trim();
        String usercountry = country.getEditText().getText().toString().trim();
        String userpincode = pincode.getEditText().getText().toString().trim();

        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

        Call<UpdateAddressResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateAddress(sharedPrefManager.getUser().getId(),add1, add2, usercity,userstate, usercountry, userpincode);

        call.enqueue(new Callback<UpdateAddressResponse>() {
            @Override
            public void onResponse(Call<UpdateAddressResponse> call, Response<UpdateAddressResponse> response) {
                UpdateAddressResponse updateAddressResponse = response.body();
                if(response.isSuccessful())
                {
                    if(updateAddressResponse != null)
                    {
                        Toast.makeText(getContext(), "Address Update Successfully..!", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateAddressResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    private void fetchUserAddress()
    {
        Call<UserProfileResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .profile(sharedPrefManager.getUser().getId());

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                UserProfileResponse userProfileResponse = response.body();
                if(response.isSuccessful())
                {
                    if(userProfileResponse != null)
                    {
                      //  System.out.println(userProfileResponse);
                        street1.getEditText().setText(userProfileResponse.getStreet1());
                        street2.getEditText().setText(userProfileResponse.getStreet2());
                        city.getEditText().setText(userProfileResponse.getCity());
                        state.getEditText().setText(userProfileResponse.getState());
                        country.getEditText().setText(userProfileResponse.getCountry());
                        pincode.getEditText().setText(userProfileResponse.getPincode());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkoutOrder()
    {
        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Please Waiting...");
        mDialog.show();


        String otype = order_type.getText().toString();
        //  int id = orderResponse.getId();


        Call<OrderResponse2> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateOrderId(oid,otype,"Pending");

        call.enqueue(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                OrderResponse2 orderResponse2 = response.body();
                if(response.isSuccessful())
                {
                    if (orderResponse2 != null)
                    {
                        mDialog.dismiss();
                        Toast.makeText(getContext(),"Checkout",Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT,oid);
                        startActivity(intent);
                    }
                }
                else
                {
                    if (orderResponse2 != null) {
                        Toast.makeText(getContext(),"Not Respond",Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    public void showFoodDelivery()
    {
        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

//        if (getArguments() != null) {
//            final String data = getArguments().getString("order_no");// data which sent from activity


        Call<OrderResponse1> call = RetrofitClient
                .getInstance()
                .getApi()
                .getorderid();

        call.enqueue(new Callback<OrderResponse1>() {
            @Override
            public void onResponse(Call<OrderResponse1> call, Response<OrderResponse1> response) {
                OrderResponse1 orderResponse1 = response.body();
                if(response.isSuccessful())
                {
                    if (orderResponse1 != null)
                    {
                        // sharedPrefManager.saveOrder(orderResponse1);
                        mDialog.dismiss();
                        order_no.setText(orderResponse1.getOrder_no());
                        Toast.makeText(getContext(),"Delivery",Toast.LENGTH_SHORT).show();
                        oid = orderResponse1.getId();
                        Toast.makeText(getContext(),"New order id:" + oid,Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if (orderResponse1 != null) {
                        Toast.makeText(getContext(),"Not Respond",Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse1> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });

    }
}