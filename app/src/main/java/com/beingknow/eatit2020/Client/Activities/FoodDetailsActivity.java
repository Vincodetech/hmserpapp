package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Client.Adapters.ItemListAdapter;
import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.Database.Database;
import com.beingknow.eatit2020.Models.Food;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class FoodDetailsActivity extends AppCompatActivity {

    TextView food_name, food_price, food_description, food_quantity1;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    private ItemDetailAdapter itemDetailAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Item> itemList;


    private int[] myImageList = new int[]{R.drawable.baigan_bharta};
    private String[] myImageNameList = new String[]{"Baingan ka Bharta"};
    private String [] myDesc = new String[]{"This is a Baingan ka Bharta"};
    private Double [] myprice = new Double[]{150.00};

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        numberButton = findViewById(R.id.number_button);
        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberButton.getNumber();
                food_quantity1.setText(number);
                int qty = Integer.parseInt(food_quantity1.getText().toString());
                double price = 150.00;
                double after_price = (qty * price);
                food_price.setText(Double.toString(after_price));
            }
        });
        btnCart = findViewById(R.id.btn_cart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodDetailsActivity.this, "Added to Cart...!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FoodDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        food_name = findViewById(R.id.food_name);
        food_description = findViewById(R.id.food_description);
        food_quantity1 = findViewById(R.id.food_quantity1);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.food_image);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpanededAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance((R.style.CollapsedAppBar));

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.food_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();
        itemDetailAdapter = new ItemDetailAdapter(this, itemList);



        itemDetailAdapter = new ItemDetailAdapter(getApplicationContext(), itemList,recyclerView);
      //  recyclerView.setAdapter(itemDetailAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(FoodDetailsActivity.this, LinearLayoutManager.VERTICAL, false));


    }



}