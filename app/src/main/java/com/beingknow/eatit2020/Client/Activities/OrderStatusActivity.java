package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beingknow.eatit2020.Client.Adapters.CartAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderStatusAdapter;
import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.Models.Request;
import com.beingknow.eatit2020.R;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;


public class OrderStatusActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    public TextView txtOrderID, txtOrderStatus, txtOrderPhone, txtOrderAddress;

    ArrayList<Request> request = new ArrayList<>();
    OrderStatusAdapter adapter;

    private int[] orderid = new int[]{1001,1002,1003};
    private String[] order_status = new String[]{"Your Order has Shipped...!", "Your Order is on the Way...!", "Your Order has Placed...!"};
    private String[] phone = new String[]{"9638743411","9638743411","9638743411"};
    private String [] address = new String[]{"85/A,Suvidhinath Society,Kalka Road,Patan", "85/A,Suvidhinath Society,Kalka Road,Patan", "85/A,Suvidhinath Society,Kalka Road,Patan"};

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        txtOrderID = (TextView) findViewById(R.id.order_id);
        txtOrderStatus = (TextView) findViewById(R.id.order_status);
        txtOrderPhone = (TextView) findViewById(R.id.order_phone);
        txtOrderAddress = (TextView) findViewById(R.id.order_address);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.my_orders));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Load menu
        recyclerView = findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderStatusAdapter(this, request);

        request = orderList();
        adapter = new OrderStatusAdapter(getApplicationContext(), request,recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderStatusActivity.this, LinearLayoutManager.VERTICAL, false));


    }

    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), DashboardFragment.class);
//        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    private ArrayList<Request> orderList()
    {
        ArrayList<Request> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Request fruitModel = new Request();
            fruitModel.setOrderid(orderid[i]);
            fruitModel.setStatus(order_status[i]);
            fruitModel.setPhone(phone[i]);
            fruitModel.setAddress(address[i]);
            list.add(fruitModel);


        }

        return list;
    }



}