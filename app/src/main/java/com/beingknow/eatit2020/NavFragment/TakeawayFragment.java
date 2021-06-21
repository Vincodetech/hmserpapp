package com.beingknow.eatit2020.NavFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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

import com.beingknow.eatit2020.Client.Activities.CartActivity;
import com.beingknow.eatit2020.Client.Activities.OrderSummaryActivity;
import com.beingknow.eatit2020.Client.Activities.OrderTypeActivity;
import com.beingknow.eatit2020.Client.Activities.SignInActivity;
import com.beingknow.eatit2020.Client.Activities.SignUpActivity;
import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Client.Adapters.MenuAdapter;
import com.beingknow.eatit2020.Client.Adapters.TakeawayOrderAdapter;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.FoodCategoryResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse1;
import com.beingknow.eatit2020.ModelResponse.OrderResponse2;
import com.beingknow.eatit2020.ModelResponse.RegisterResponse;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TakeawayFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemClickListener mOnItemClickInterface;
    private TakeawayOrderAdapter takeawayOrderAdapter;
    private ArrayList<OrderResponse> orderResponses;
    private OrderResponse orderResponse;
    private OrderResponse2 orderResponse2;
    private OrderResponse1 orderResponse1;
    private TextView order_no_text, order_no, order_type_text, order_type,hotel_address,hotel_name,hotel_add1,hotel_add2,hotel_add3,hotel_mo;
    private ImageView location;
    private Button btn_checkout;
    private CardView card_myevent;
    private SharedPrefManager sharedPrefManager;
    private int oid = 0;

    public TakeawayFragment() {

    }


    public static TakeawayFragment newInstance(String param1, String param2) {
        TakeawayFragment fragment = new TakeawayFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_takeaway, container, false);

        order_no_text = (TextView) view.findViewById(R.id.order_no_text);
        order_no = (TextView) view.findViewById(R.id.order_no_takeaway);
        order_type_text = (TextView) view.findViewById(R.id.order_type_takeaway_text);
        order_type = (TextView) view.findViewById(R.id.order_type_takeaway);
        hotel_address = (TextView) view.findViewById(R.id.hotel_address);
        hotel_name = (TextView) view.findViewById(R.id.hotel_name);
        hotel_add1 = (TextView) view.findViewById(R.id.hotel_add1);
        hotel_add2 = (TextView) view.findViewById(R.id.hotel_add2);
        hotel_add3 = (TextView) view.findViewById(R.id.hotel_add3);
        hotel_mo = (TextView) view.findViewById(R.id.hotel_mo);
        card_myevent = (CardView) view.findViewById(R.id.card_myevent);
        recyclerView = (RecyclerView) view.findViewById(R.id.listCart);
        btn_checkout = (Button) view.findViewById(R.id.btn_checkout);
        location = (ImageView) view.findViewById(R.id.location);
        sharedPrefManager = new SharedPrefManager(getContext());
        orderResponses = new ArrayList<>();

        if (getActivity() != null) {

            takeawayOrder();

        }
        if(btn_checkout != null)
        {
            btn_checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkoutOrder();
                }
            });

        }

        return view;
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
                .updateOrderId(oid,otype);

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

    private void takeawayOrder() {

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
                        Toast.makeText(getContext(),"Takeaway",Toast.LENGTH_SHORT).show();
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