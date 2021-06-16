package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.ModelResponse.OrderResponse1;
import com.beingknow.eatit2020.ModelResponse.OrderResponse3;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends AppCompatActivity {

    private TextView order_no_text, order_no, order_type_text, order_type;
    private Button btn_checkout;
    private SharedPrefManager sharedPrefManager;
    private int oid = 0;
    private RecyclerView recyclerView;


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

        order_no_text = (TextView) findViewById(R.id.order_no_text_summary);
        order_no = (TextView) findViewById(R.id.order_no_summary);
        order_type_text = (TextView) findViewById(R.id.order_type_text_summary);
        order_type = (TextView) findViewById(R.id.order_type_summary);
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        btn_checkout = (Button) findViewById(R.id.btn_checkout);

        checkOrder();

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