package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.beingknow.eatit2020.Client.Adapters.CafeItemAdapter;
import com.beingknow.eatit2020.Models.CafeItem;
import com.beingknow.eatit2020.R;

import java.util.ArrayList;
import java.util.Objects;

public class CafeDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<CafeItem> itemList;
    private CafeItemAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;

    private int[] myImageList = new int[]{R.drawable.tea, R.drawable.coffe, R.drawable.cold_coffee, R.drawable.cold_coffee_with_icecreame, R.drawable.ice_creame, R.drawable.faluda, R.drawable.milk_shake, R.drawable.cold_drinks};
    private String[] myImageNameList = new String[]{"Tea", "Coffee", "Cold Coffee", "Cold Coffee With Ice-Cream", "Ice-Cream", "Faluda", "Milk Shake", "Cold Drinks"};
    private String [] myDesc = new String[]{"This is a Tea", "This is a Coffee","This is a Cold Coffee","This is a Cold Coffee With Ice-Cream","This is an Ice-Cream","This is a Faluda","This is a Milk Shake","These are Cold Drinks"};
    private Double [] myprice = new Double[]{20.00,30.00,50.00,100.00,70.00,120.00,120.00,70.00};


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.cafe_menu));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        itemList = new ArrayList<>();
        mAdapter = new CafeItemAdapter(this, itemList);

        itemList = eatFruits();

        mAdapter = new CafeItemAdapter(getApplicationContext(), itemList,recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CafeDetailActivity.this, LinearLayoutManager.VERTICAL, false));

    }

    private ArrayList<CafeItem> eatFruits() {

        ArrayList<CafeItem> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            CafeItem fruitModel = new CafeItem();
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