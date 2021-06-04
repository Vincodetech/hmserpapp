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
import com.beingknow.eatit2020.Client.Adapters.ItemListAdapter;
import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.Database.Database;
import com.beingknow.eatit2020.ModelResponse.CartResponse;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.Models.Request;
import com.beingknow.eatit2020.R;

import com.beingknow.eatit2020.RetrofitClient;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ElegantNumberButton numberButton;
    TextView txtTotalPrice;
    MaterialButton btnPlace;
    CardView cardView;
    ArrayList<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    private String[] myNameList = new String[]{"Kashmiri Kofta", "Veg Toofani", "Veg Kofta", "Baingan ka Bharta"};
    private int[] myQuantity = new int[]{1,1,1,1};
    private Double [] myprice = new Double[]{150.00,140.00,130.00,140.00};


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.my_cart));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     //   numberButton = findViewById(R.id.quantity_number_button);
//        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String number = numberButton.getNumber();
////                food_quantity1.setText(number);
////                int qty = Integer.parseInt(food_quantity1.getText().toString());
////                double price = 150.00;
////                double after_price = (qty * price);
////                food_price.setText(Double.toString(after_price));
//            }
//        });


        //init
        recyclerView =findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new CartAdapter(this, cart);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btn_place_order);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialog();
            }
        });

        loadFoodList();

        cart = cartList();
        adapter = new CartAdapter(getApplicationContext(), cart,recyclerView);
        recyclerView.setAdapter(adapter);
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
//                Request request = new Request(
//
//                );
//
//                new Database(getBaseContext()).cleanCart();
//                Toast.makeText(CartActivity.this, "Thank You...! Your Order has Placed...!", Toast.LENGTH_SHORT).show();
//                finish();
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
       // cart = new Database(this).getCarts();
     //   adapter = new CartAdapter(cart,this);
      //  recyclerView.setAdapter(adapter);

        //calculate total price
  //      int total = 0;
  //      for (Order order:cart)
  //          total+=(Integer.parseInt(order.getPrice()))*Integer.parseInt(order.getQuantity());
//        Locale locale = new Locale("en","US");
//        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
//        txtTotalPrice.setText(total);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private ArrayList<Order> cartList()
    {
        ArrayList<Order> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Order fruitModel = new Order();
            fruitModel.setProductName(myNameList[i]);
            fruitModel.setQuantity(myQuantity[i]);
            fruitModel.setPrice(myprice[i]);
            list.add(fruitModel);


        }

        return list;
    }

    public void addCartItem()
    {
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT))
        {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, 1);
            String item_name = intent.getStringExtra(Intent.EXTRA_TEXT);
            String quantity = intent.getStringExtra(Intent.EXTRA_TEXT);
            double price = intent.getDoubleExtra(Intent.EXTRA_TEXT, 1.0);
            Double D = Double.valueOf(price);
            float f = D.floatValue();


        }
    }
}