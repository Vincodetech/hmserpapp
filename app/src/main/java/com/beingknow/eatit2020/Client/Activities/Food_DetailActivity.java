package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.NonNull;
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
import com.beingknow.eatit2020.RetrofitClient;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Food_DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Item> itemList;
    private ItemListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;



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



        getFoodItemList();

       // mAdapter = new ItemListAdapter(getApplicationContext(), itemList,recyclerView);
      //  recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Food_DetailActivity.this, LinearLayoutManager.VERTICAL, false));


    }

    public void getFoodItemList() {
        for (int i = 1; i <= itemList.size(); i++) {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("category_id", String.valueOf(i));


            Call<ArrayList<Item>> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .fooditemlist(paramsMap);

            call.enqueue(new Callback<ArrayList<Item>>() {
                @Override
                public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {

                    if(response.isSuccessful() && response.body() != null)
                    {
                        itemList = response.body();

                        mAdapter = new ItemListAdapter(getApplicationContext(), itemList,recyclerView);
                        recyclerView.setAdapter(mAdapter);


                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}