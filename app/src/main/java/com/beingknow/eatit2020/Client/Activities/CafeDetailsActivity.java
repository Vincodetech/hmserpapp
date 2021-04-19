package com.beingknow.eatit2020.Client.Activities;
import com.beingknow.eatit2020.Client.Adapters.CafeItemAdapter;
import com.beingknow.eatit2020.Client.Adapters.ItemDetailAdapter;
import com.beingknow.eatit2020.Models.CafeItem;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class CafeDetailsActivity extends AppCompatActivity {

    TextView cafe_name, cafe_price, cafe_description, cafe_quantity1;
    ImageView cafe_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart1;
    ElegantNumberButton numberButton1;
    private CafeItemAdapter cafeItemAdapter;
    private RecyclerView recyclerView1;
    private ArrayList<CafeItem> itemList;

    private int[] myImageList = new int[]{R.drawable.tea};
    private String[] myImageNameList = new String[]{"Tea"};
    private String [] myDesc = new String[]{"This is a Tea"};
    private Double [] myprice = new Double[]{20.00};

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_details);

        numberButton1 = findViewById(R.id.number_button1);
        numberButton1.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberButton1.getNumber();
                cafe_quantity1.setText(number);
                int qty = Integer.parseInt(cafe_quantity1.getText().toString());
                double price = 20.00;
                double after_price = (qty * price);
                cafe_price.setText(Double.toString(after_price));
            }
        });

        btnCart1 = findViewById(R.id.btn_cart1);

        btnCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CafeDetailsActivity.this, "Added to Cart...!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CafeDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        cafe_name = findViewById(R.id.cafe_name);
        cafe_description = findViewById(R.id.cafe_description);
        cafe_quantity1 = findViewById(R.id.cafe_quantity1);
        cafe_price = findViewById(R.id.cafe_price);
        cafe_image = findViewById(R.id.cafe_image);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpanededAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance((R.style.CollapsedAppBar));

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.cafe_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView1 = findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();
        cafeItemAdapter = new CafeItemAdapter(this, itemList);

        itemList = eatFruits();


        cafeItemAdapter = new CafeItemAdapter(getApplicationContext(), itemList,recyclerView1);
    }

    private ArrayList<CafeItem> eatFruits() {

        ArrayList<CafeItem> list = new ArrayList<>();

        CafeItem fruitModel = new CafeItem();
        fruitModel.setName(myImageNameList[0]);
        fruitModel.setThumbnail(myImageList[0]);
        fruitModel.setDescription(myDesc[0]);
        fruitModel.setPrice(myprice[0]);
        list.add(fruitModel);

        return list;
    }

    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), DashboardFragment.class);
//        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
}