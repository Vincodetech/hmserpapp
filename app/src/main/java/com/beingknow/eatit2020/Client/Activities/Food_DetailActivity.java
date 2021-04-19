package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beingknow.eatit2020.Client.Adapters.ItemListAdapter;
import com.beingknow.eatit2020.Client.Adapters.MenuAdapter;
import com.beingknow.eatit2020.Models.Category;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.NavFragment.DashboardFragment;
import com.beingknow.eatit2020.R;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Food_DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Item> itemList;
    private ItemListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;

    private int[] myImageList = new int[]{R.drawable.baigan_bharta, R.drawable.kashmiri_kofta, R.drawable.lasaniya_bataka, R.drawable.lasaniya_paneer, R.drawable.veg_toofani, R.drawable.veg_kofta, R.drawable.sev_tameta, R.drawable.mysore_masala_dosa};
    private String[] myImageNameList = new String[]{"Baingan ka Bharta", "Kashmiri Kofta", "Lasaniya Bataka", "Lasaniya Paneer", "Veg Toofani", "Veg Kofta", "Sev Tameta", "Mysore Masala Dosa"};
    private String [] myDesc = new String[]{"This is a Baingan ka Bharta", "This is a Kashmiri Kofta","This is a Lasaniya Bataka","This is a Lasaniya Paneer","This is a Veg Toofani","This is a Veg Kofta","This is a Sev Tameta","This is a Mysore Masala Dosa"};
    private Double [] myprice = new Double[]{150.00,140.00,150.00,130.00,120.00,110.00,120.00,70.00};

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food__detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.menu));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        itemList = new ArrayList<>();
        mAdapter = new ItemListAdapter(this, itemList);

        itemList = eatFruits();
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(Food_DetailActivity.this, DividerItemDecoration.VERTICAL));
//        recyclerView.setAdapter(mAdapter);

        mAdapter = new ItemListAdapter(getApplicationContext(), itemList,recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Food_DetailActivity.this, LinearLayoutManager.VERTICAL, false));


    }

    private ArrayList<Item> eatFruits() {

        ArrayList<Item> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Item fruitModel = new Item();
            fruitModel.setName(myImageNameList[i]);
            fruitModel.setThumbnail(myImageList[i]);
            fruitModel.setDescription(myDesc[i]);
            fruitModel.setPrice(myprice[i]);
            list.add(fruitModel);


        }

        return list;
    }

    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), DashboardFragment.class);
//        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
}