package com.beingknow.eatit2020.NavFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.beingknow.eatit2020.Api;
import com.beingknow.eatit2020.Client.Activities.Food_DetailActivity;
import com.beingknow.eatit2020.Client.Adapters.CafeMenuAdapter;
import com.beingknow.eatit2020.Client.Adapters.MenuAdapter;
import com.beingknow.eatit2020.Constant;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.FoodCategoryResponse;
import com.beingknow.eatit2020.ModelResponse.SliderData;
import com.beingknow.eatit2020.ModelResponse.CafeCategory;
import com.beingknow.eatit2020.Models.Item;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;
import com.beingknow.eatit2020.Client.Adapters.SliderAdapter;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RelativeLayout main;
    private ItemClickListener mOnItemClickInterface;
    private MenuAdapter menuAdapter;
    private CardView cardview;
    private CafeMenuAdapter cafeMenuAdapter;
    ItemClickListener itemClickListener;
    private Api apiInterface;
   Uri imageUrl, imageUrl1;
   private Bitmap bitmap;
    SliderView sliderView;
    private String url = "";

    private ArrayList<FoodCategoryResponse> imageModelArrayList;
    private ArrayList<CafeCategory> modelArrayList;
    private ArrayList<SliderData> sliderData;
    private ArrayList<Item> itemArrayList;
   // private RelativeLayout main;
    private SliderAdapter adapter;
    public Constant constant;
    public RetrofitClient retrofitClient;

    Button addNewItem;
    TextView name, email;
    SharedPrefManager sharedPrefManager;
    View view;
    int val;
    TextView c,menu_name;
    String menu_cat_name;
    ViewPager viewPager;
    TabLayout tab;
    ImageView imageView;


    private String myCategoryImageList[] = null;
    private String myCategoryImageNameList[] = null;

    private String myCafeImageList;
    private String myCafeNameList;

    public DashboardFragment() {
    }


    public static DashboardFragment newInstance(String contIndex) {
        DashboardFragment cf = new DashboardFragment();

        Bundle args = new Bundle();
        args.putString("contIndex", contIndex);
        cf.setArguments(args);
        return cf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        sliderView = (SliderView) view.findViewById(R.id.imageSlider1);
        recyclerView =(RecyclerView) view.findViewById(R.id.recycler);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler1);
        cardview = (CardView) view.findViewById(R.id.cardview);
        imageView = (ImageView) view.findViewById(R.id.menu_image);
        menu_name = (TextView) view.findViewById(R.id.menu_name);
        main = (RelativeLayout) view.findViewById(R.id.relative);

        if (getActivity() != null) {

            fetchCategory();
            fetchSliderImage();
            fetchCafeCategory();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);

            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            recyclerView1.setLayoutManager(linearLayoutManager1);
        }
        return view;

    }

    private void fetchCafeCategory()
    {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("category_type", "Cafe");

        Call<ArrayList<CafeCategory>> call = RetrofitClient
                .getInstance()
                .getApi()
                .cafecategorylist(paramsMap);

        call.enqueue(new Callback<ArrayList<CafeCategory>>() {
            @Override
            public void onResponse(Call<ArrayList<CafeCategory>> call, Response<ArrayList<CafeCategory>> response) {
                if(response.isSuccessful() && response.body() != null && getActivity() != null)
                {
                    CafeMenuAdapter menuAdapter = new CafeMenuAdapter(getContext(),response.body(),recyclerView);
                    recyclerView1.setAdapter(menuAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CafeCategory>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSliderImage()
    {

        Call<ArrayList<SliderData>> call = RetrofitClient
                .getInstance()
                .getApi()
                .sliderlist();

        call.enqueue(new Callback<ArrayList<SliderData>>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call<ArrayList<SliderData>> call, Response<ArrayList<SliderData>> response) {
                if(response.isSuccessful() && response.body() != null && getActivity() != null)
                {
                        ArrayList<SliderData> sliderData = new ArrayList<>();

                        sliderData = response.body();

                        SliderAdapter adapter = new SliderAdapter(getContext(), sliderData, sliderView);

                        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

                        sliderView.setSliderAdapter(adapter);

                        sliderView.setScrollTimeInSec(3);

                        sliderView.setAutoCycle(true);

                        sliderView.startAutoCycle();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<SliderData>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void fetchCategory()
    {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("category_type", "Restaurant");

        Call<ArrayList<FoodCategoryResponse>> call = RetrofitClient
                .getInstance()
                .getApi()
                .foodcategorylist(paramsMap);

        call.enqueue(new Callback<ArrayList<FoodCategoryResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<FoodCategoryResponse>> call, Response<ArrayList<FoodCategoryResponse>> response) {

                if(response.isSuccessful() && response.body() != null && getActivity() != null)
                {
                    MenuAdapter menuAdapter = new MenuAdapter(getContext(),response.body(),recyclerView);
                    recyclerView.setAdapter(menuAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FoodCategoryResponse>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}



