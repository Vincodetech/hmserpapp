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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Client.Adapters.ItemListAdapter;
import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.Database.Database;
import com.beingknow.eatit2020.Models.Food;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.Models.Order;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailsActivity extends AppCompatActivity {

    private TextView food_name, food_price, food_description, food_quantity1;
    private ImageView food_image;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;
    private ItemDetailAdapter itemDetailAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Item> itemList;
    private ImageView plus, minus;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);







        btnCart = (FloatingActionButton) findViewById(R.id.btn_cart);
        if(btnCart != null) {
            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FoodDetailsActivity.this, "Added to Cart...!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FoodDetailsActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            });
        }


        food_name = (TextView) findViewById(R.id.food_name);
        food_description = (TextView) findViewById(R.id.food_description);
        food_quantity1 = (TextView) findViewById(R.id.food_quantity1);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.food_image);
        plus = (ImageView) findViewById(R.id.plus);
        minus = (ImageView) findViewById(R.id.minus);

        collapsingToolbarLayout = findViewById(R.id.collapsing1);
        if(collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpanededAppBar);
            collapsingToolbarLayout.setCollapsedTitleTextAppearance((R.style.CollapsedAppBar));
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.food_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        recyclerView = findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();

        singleFoodItem();


     recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    public void singleFoodItem()
    {
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
                        itemList = response.body();

                        itemDetailAdapter = new ItemDetailAdapter(getApplicationContext(), itemList, recyclerView);
                        recyclerView.setAdapter(itemDetailAdapter);




                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


}