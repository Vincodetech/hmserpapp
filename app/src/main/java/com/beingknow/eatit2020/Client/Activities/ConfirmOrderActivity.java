package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Adapters.OrderSummaryAdapter1;
import com.beingknow.eatit2020.Interface.OrderResponse4;
import com.beingknow.eatit2020.ModelResponse.OrderDetailResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse3;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderActivity extends AppCompatActivity {


    private int oid = 0;
    private TextView order_no_text, order_no, order_type_text, order_type;
    private CardView cardView;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btn_confirm;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.confirm_order));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            oid = intent.getIntExtra(Intent.EXTRA_TEXT, 1);

            Toast.makeText(ConfirmOrderActivity.this, "Oid:" + oid, Toast.LENGTH_SHORT).show();
        }

        order_no_text = (TextView) findViewById(R.id.order_no_confirm_text);
        order_no = (TextView) findViewById(R.id.order_no_confirm);
        order_type_text = (TextView) findViewById(R.id.order_type_confirm_text);
        order_type = (TextView) findViewById(R.id.order_type_confirm);
        cardView = (CardView) findViewById(R.id.card);
        radioGroup = findViewById(R.id.radioPayment);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);

        displayOrder();

        final int selectedID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedID);
        Toast.makeText(ConfirmOrderActivity.this, radioButton.getText(),Toast.LENGTH_SHORT).show();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();

            }
        });

    }

    private void updateStatus()
    {
        final ProgressDialog mDialog = new ProgressDialog(ConfirmOrderActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

        Call<OrderResponse4> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateStatus(oid,"Complete",radioButton.getText().toString());

        call.enqueue(new Callback<OrderResponse4>() {
            @Override
            public void onResponse(Call<OrderResponse4> call, Response<OrderResponse4> response) {
                OrderResponse4 orderResponse4 = response.body();
                if(response.isSuccessful())
                {
                    if(orderResponse4 != null)
                    {
                        mDialog.dismiss();
                        Toast.makeText(ConfirmOrderActivity.this, "Update Status Successfully..!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ConfirmOrderActivity.this, "Not Respond", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse4> call, Throwable t) {
                Toast.makeText(ConfirmOrderActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    private void displayOrder()
    {
        final ProgressDialog mDialog = new ProgressDialog(ConfirmOrderActivity.this);
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
                        Toast.makeText(ConfirmOrderActivity.this,"Continue Order",Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    if (orderResponse3 != null) {
                        Toast.makeText(ConfirmOrderActivity.this,"Not Respond",Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse3> call, Throwable t) {
                Toast.makeText(ConfirmOrderActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}