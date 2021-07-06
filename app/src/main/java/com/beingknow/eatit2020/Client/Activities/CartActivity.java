package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Adapters.CartAdapter;
import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Client.Adapters.ItemListAdapter;
import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.Database.Database;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.AmountResponse;
import com.beingknow.eatit2020.ModelResponse.CartDataResponse;
import com.beingknow.eatit2020.ModelResponse.CartResponse;
import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.Models.Request;
import com.beingknow.eatit2020.NavFragment.DeliveryFragment;
import com.beingknow.eatit2020.NavFragment.TakeawayFragment;
import com.beingknow.eatit2020.R;

import com.beingknow.eatit2020.RandomString;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public TextView txtTotalPrice,total,price,cart_amount;
    public Button btnPlace;
    CardView cardView;
    public ArrayList<Item1> cart = new ArrayList<>();
    public ArrayList<CartDataResponse> cartDataResponses = new ArrayList<>();
    public ArrayList<AmountResponse> amountResponses = new ArrayList<>();
    public CartAdapter cartAdapter;
    public SharedPrefManager sharedPrefManager;
    public DatabaseHelper databaseHelper;
    public ImageView plus, minus;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.my_cart));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        //init
        recyclerView =findViewById(R.id.listCart);
        price = findViewById(R.id.cart_price);
      //  cart_amount = findViewById(R.id.cart_amount);
        plus = (ImageView) findViewById(R.id.plus);
        minus = (ImageView) findViewById(R.id.minus);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btn_place_order);
        if(btnPlace != null)
        {
            btnPlace.setOnClickListener(new View.OnClickListener() {
             @Override
                public void onClick(View view) {
                    if (cart != null) {
                                addOrder();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Cart is Empty...!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        getCartData();
        getAmount();
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));

    }

    private void getAmount()
    {
       Call<ArrayList<AmountResponse>> call = RetrofitClient
               .getInstance()
               .getApi()
               .getsumofamountcartdata(sharedPrefManager.getUser().getId());

       call.enqueue(new Callback<ArrayList<AmountResponse>>() {
           @Override
           public void onResponse(Call<ArrayList<AmountResponse>> call, Response<ArrayList<AmountResponse>> response) {
               if (response.isSuccessful() && response.body() != null && getApplicationContext() != null) {
                    Double amount = response.body().get(0).getAmount();
                    txtTotalPrice.setText(String.valueOf(amount));
               }
           }

           @Override
           public void onFailure(Call<ArrayList<AmountResponse>> call, Throwable t) {
               Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void getCartData()
    {
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, 1);

            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("id", String.valueOf(id));

            Call<ArrayList<CartDataResponse>> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getcartdata(paramsMap);

            call.enqueue(new Callback<ArrayList<CartDataResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<CartDataResponse>> call, Response<ArrayList<CartDataResponse>> response) {
                    if (response.isSuccessful() && response.body() != null && getApplicationContext() != null) {
                        cartDataResponses = response.body();
                        cartAdapter = new CartAdapter(getApplicationContext(), cartDataResponses, recyclerView);
                        recyclerView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CartDataResponse>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addOrder()
    {
        final ProgressDialog mDialog = new ProgressDialog(CartActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

        int n = 10;
        final String order_no = "H-" + RandomString.getAlphaNumericString(n);



        Call<OrderResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .addorder(order_no,1,sharedPrefManager.getUser().getId());

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                OrderResponse orderResponse = response.body();
                if(response.isSuccessful())
                {
                    if (orderResponse != null && orderResponse.getError().equals("000"))
                    {
                      //  Toast.makeText(CartActivity.this,orderResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        sharedPrefManager.saveOrder(orderResponse);
                        Bundle bundle = new Bundle();
                        bundle.putString("order_no", order_no);

                        // Your fragment
                        TakeawayFragment obj = new TakeawayFragment();
                        obj.setArguments(bundle);

                        mDialog.dismiss();

                        new SweetAlertDialog(
                                CartActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Place Order")
                                .setContentText("Your Order has Placed Successfully...!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Toast.makeText(getApplicationContext(), "Place Order...!", Toast.LENGTH_SHORT).show();
                                        final Intent intent = new Intent(CartActivity.this, OrderTypeActivity.class);
                                        startActivity(intent);
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }
                else
                {
                    if (orderResponse != null) {
                        Toast.makeText(CartActivity.this,orderResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        new SweetAlertDialog(CartActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something Went Wrong!")
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                new SweetAlertDialog(CartActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something Went Wrong!")
                        .show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}