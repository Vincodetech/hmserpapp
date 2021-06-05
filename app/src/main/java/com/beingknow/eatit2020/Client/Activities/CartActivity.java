package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Adapters.CartAdapter;
import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Client.Adapters.ItemListAdapter;
import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.Database.Database;
import com.beingknow.eatit2020.Database.DatabaseHelper;
import com.beingknow.eatit2020.ModelResponse.CartResponse;
import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.Models.Request;
import com.beingknow.eatit2020.R;

import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView txtTotalPrice;
    MaterialButton btnPlace;
    CardView cardView;
    ArrayList<Item> cart = new ArrayList<>();
    CartAdapter cartAdapter;
    SharedPrefManager sharedPrefManager;
    private DatabaseHelper databaseHelper;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.my_cart));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        //init
        recyclerView =findViewById(R.id.listCart);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);


      //  adapter = new CartAdapter(this, cart);
        cart = new ArrayList<>(databaseHelper.getCartData());
        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btn_place_order);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialog();
            }
        });

        loadFoodList();
        addCartItem();

      //  cart = cartList();
//        adapter = new CartAdapter(getApplicationContext(), cart,recyclerView);
//        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));

    }



    private void ShowAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("One More Step!");
        alertDialog.setMessage("Enter your Address");

        final EditText edtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress); //add editText to AlertDialog
        alertDialog.setIcon(R.drawable.ic_baseline_shopping_cart_24);

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(CartActivity.this, OrderStatusActivity.class);
                startActivity(intent);
                Toast.makeText(CartActivity.this, "Thank You...! Your Order has Placed...!",Toast.LENGTH_SHORT).show();

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }

    private void loadFoodList() {

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }



    public void addCartItem()
    {
//        Intent intent = getIntent();
//        if (intent.hasExtra(Intent.EXTRA_TEXT))
//        {
//            int id = intent.getIntExtra(Intent.EXTRA_TEXT, 1);
//            String item_name = intent.getStringExtra(Intent.EXTRA_TEXT);
//            String quantity = intent.getStringExtra(Intent.EXTRA_TEXT);
//            double price = intent.getDoubleExtra(Intent.EXTRA_TEXT, 1.0);
//            Double D = Double.valueOf(price);
//            float f = D.floatValue();
//
//           Call<ArrayList<CartResponse>> call = RetrofitClient
//                   .getInstance()
//                   .getApi()
//                   .addcartitem(id,17,quantity,f);
//
//           call.enqueue(new Callback<ArrayList<CartResponse>>() {
//               @Override
//               public void onResponse(Call<ArrayList<CartResponse>> call, Response<ArrayList<CartResponse>> response) {
//
//               }
//
//               @Override
//               public void onFailure(Call<ArrayList<CartResponse>> call, Throwable t) {
//
//               }
//           });
//        }
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, 1);

            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("id", String.valueOf(id));

           Call<ArrayList<Item>> call = RetrofitClient
                   .getInstance()
                   .getApi()
                   .singleFoodItem(paramsMap);
           call.enqueue(new Callback<ArrayList<Item>>() {
               @Override
               public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                   if (response.isSuccessful() && response.body() != null && getApplicationContext() != null) {
                       cart = response.body();
                      // sharedPrefManager.addCart1(cart);
                       cartAdapter = new CartAdapter(getApplicationContext(), cart, recyclerView);
                       recyclerView.setAdapter(cartAdapter);

                   }
               }

               @Override
               public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                   Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
               }
           });
        }

    }
}