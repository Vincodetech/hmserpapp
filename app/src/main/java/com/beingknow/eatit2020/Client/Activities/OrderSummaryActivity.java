package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Adapters.OrderSummaryAdapter;
import com.beingknow.eatit2020.Client.Adapters.OrderSummaryAdapter1;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.ModelResponse.OrderDetailResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse3;
import com.beingknow.eatit2020.Models.Item1;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;

import java.util.ArrayList;
import java.util.Objects;

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
    private ArrayList<Item1> cart = new ArrayList<>();
    private final Item1 item1 = null;
    private OrderSummaryAdapter orderSummaryAdapter;
    private OrderSummaryAdapter1 orderSummaryAdapter1;
    private DatabaseHelper databaseHelper;
    private ListView listView;


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

        databaseHelper = new DatabaseHelper(getApplicationContext());

        order_no_text = (TextView) findViewById(R.id.order_no_text_summary);
        order_no = (TextView) findViewById(R.id.order_no_summary);
        order_type_text = (TextView) findViewById(R.id.order_type_text_summary);
        order_type = (TextView) findViewById(R.id.order_type_summary);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        total = (TextView) findViewById(R.id.total);
        listView = (ListView) findViewById(R.id.listview);
        btn_continue = (Button) findViewById(R.id.btn_continue);

        checkOrder();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(OrderSummaryActivity.this, ConfirmOrderActivity.class);
//                startActivity(intent1);
                addOrderDetail();
                Toast.makeText(OrderSummaryActivity.this, "Add Order Detail Successfully..!", Toast.LENGTH_SHORT).show();
            }
        });
        //recyclerView.setLayoutManager(new LinearLayoutManager(OrderSummaryActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    private void addOrderDetail()
    {
        final ProgressDialog mDialog = new ProgressDialog(OrderSummaryActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

        Call<OrderDetailResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .addorderdetail(i_id,oid,quantity,price,1);

        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                OrderDetailResponse orderDetailResponse = response.body();
                if(response.isSuccessful())
                {
                    if(orderDetailResponse != null)
                    {
                        i_id = orderDetailResponse.getItem_id();
                        quantity = orderDetailResponse.getQuantity();
                        price = orderDetailResponse.getAmount();
//                        i_id = databaseHelper.getItemId();
//                        quantity = databaseHelper.getQuantity();
//                        price = databaseHelper.getAmount();
                       // cart = databaseHelper.getCartData1();
//                        i_id = item1.getId();
//                         quantity = item1.getQuantity();
//                         price = item1.getPrice();
                        mDialog.dismiss();
                        Toast.makeText(OrderSummaryActivity.this, "item_id = " + i_id, Toast.LENGTH_SHORT).show();
                     //   mDialog.dismiss();
                        Toast.makeText(OrderSummaryActivity.this, "Add Order Detail Successfully..!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(OrderSummaryActivity.this, "Not Respond", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
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
                        orderSummaryAdapter1 = new OrderSummaryAdapter1(getApplicationContext(), cart);
                        listView.setAdapter(orderSummaryAdapter1);
                        orderSummaryAdapter1.notifyDataSetChanged();
                        long sum = databaseHelper.sum_Of_Amount();
                        total.setText(String.valueOf(sum));
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