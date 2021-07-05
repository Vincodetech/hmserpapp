package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Adapters.CartAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderSummaryAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderSummaryAdapter1;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.ModelResponse.AmountResponse;
import com.beingknow.eatit2020.ModelResponse.CartDataResponse;
import com.beingknow.eatit2020.ModelResponse.OrderDetailResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse3;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends AppCompatActivity {

    private TextView order_no_text, order_no, order_type_text, order_type,total;
    private Button btn_continue;
    private SharedPrefManager sharedPrefManager;
    private int oid = 0;
    private int i_id = 0;
    private String quantity = null;
    private double price = 0;
    private RecyclerView recyclerView;
    public ArrayList<CartDataResponse> cartDataResponses = new ArrayList<>();
    private ArrayList<Item1> cart = new ArrayList<>();
    private final Item1 item1 = null;
    public CartAdapter cartAdapter;
    private OrderSummaryAdapter orderSummaryAdapter;
    private OrderSummaryAdapter1 orderSummaryAdapter1;
    private DatabaseHelper databaseHelper;
    private ListView listView;
    public String name,qty,p;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.order_summary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, 1);

            Toast.makeText(OrderSummaryActivity.this, "Oid:" + id, Toast.LENGTH_SHORT).show();
        }

        Intent i=this.getIntent();
        if(i !=null) {// to avoid the NullPointerException
            name = intent.getStringExtra("name");
            qty = intent.getStringExtra("quantity");
            p = intent.getStringExtra("price");
        }

        databaseHelper = new DatabaseHelper(getApplicationContext());
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        order_no_text = (TextView) findViewById(R.id.order_no_text_summary);
        order_no = (TextView) findViewById(R.id.order_no_summary);
        order_type_text = (TextView) findViewById(R.id.order_type_text_summary);
        order_type = (TextView) findViewById(R.id.order_type_summary);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        total = (TextView) findViewById(R.id.total);
        listView = (ListView) findViewById(R.id.listview);
        btn_continue = (Button) findViewById(R.id.btn_continue);


            checkOrder();
            getCartData();
            getAmount();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addOrderDetail();
            }
        });

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String name= intent.getStringExtra("name");
            Toast.makeText(OrderSummaryActivity.this, name, Toast.LENGTH_SHORT).show();
        }
    };

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
                    total.setText(String.valueOf(amount));
                    orderSummaryAdapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AmountResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addOrderDetail()
    {
        final ProgressDialog mDialog = new ProgressDialog(OrderSummaryActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();



        Call<OrderDetailResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .addorderdetail(Integer.parseInt(name),oid,qty,Double.parseDouble(p),1);

        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                OrderDetailResponse orderDetailResponse = response.body();
                if(response.isSuccessful())
                {
                    if(orderDetailResponse != null)
                    {
                        mDialog.dismiss();
                        new SweetAlertDialog(
                                OrderSummaryActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Order Detail")
                                .setContentText("Add Order Detail Successfully...!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        final Intent intent = new Intent(OrderSummaryActivity.this, ConfirmOrderActivity.class);
                                        intent.putExtra(Intent.EXTRA_TEXT,oid);
                                        Toast.makeText(OrderSummaryActivity.this, "Add Order Detail Successfully..!", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    else
                    {
                        Toast.makeText(OrderSummaryActivity.this, "Not Respond", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        new SweetAlertDialog(OrderSummaryActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something Went Wrong!")
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                new SweetAlertDialog(OrderSummaryActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something Went Wrong!")
                        .show();
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
                        orderSummaryAdapter1 = new OrderSummaryAdapter1(getApplicationContext(), cartDataResponses);
                        listView.setAdapter(orderSummaryAdapter1);
                        orderSummaryAdapter1.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CartDataResponse>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkOrder()
    {
        final ProgressDialog mDialog = new ProgressDialog(OrderSummaryActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

        Call<OrderResponse3> call = RetrofitClient
                .getInstance()
                .getApi()
                .getorder();

        call.enqueue(new Callback<OrderResponse3>() {
            @Override
            public void onResponse(Call<OrderResponse3> call, Response<OrderResponse3> response) {
                OrderResponse3 orderResponse3 = response.body();
                if(response.isSuccessful())
                {
                    if (orderResponse3 != null)
                    {
                        cart = databaseHelper.getCartData1();
                        // sharedPrefManager.saveOrder(orderResponse1);
                        mDialog.dismiss();
                        order_no.setText(orderResponse3.getOrder_no());
                        order_type.setText(orderResponse3.getOrder_type());


                        Toast.makeText(OrderSummaryActivity.this,"Continue Order",Toast.LENGTH_SHORT).show();
                        oid = orderResponse3.getId();
                        Toast.makeText(OrderSummaryActivity.this,"New order id:" + oid,Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if (orderResponse3 != null) {
                        Toast.makeText(OrderSummaryActivity.this,"Not Respond",Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse3> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}