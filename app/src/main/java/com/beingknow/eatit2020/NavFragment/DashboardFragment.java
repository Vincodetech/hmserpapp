package com.beingknow.eatit2020.NavFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.beingknow.eatit2020.Api;
import com.beingknow.eatit2020.Client.Activities.Food_DetailActivity;
import com.beingknow.eatit2020.Client.Activities.HomeActivity;
import com.beingknow.eatit2020.Client.Activities.SignInActivity;
import com.beingknow.eatit2020.Client.Adapters.CafeMenuAdapter;
import com.beingknow.eatit2020.Client.Adapters.MenuAdapter;
import com.beingknow.eatit2020.Constant;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.ModelResponse.FoodCategoryResponse;
import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.Models.CafeCategory;
import com.beingknow.eatit2020.Models.Category;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;
import com.beingknow.eatit2020.SliderAdapter;
import com.beingknow.eatit2020.SliderData;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private MenuAdapter menuAdapter;
    private CardView cardview;
    private CafeMenuAdapter cafeMenuAdapter;
    ItemClickListener itemClickListener;
    private Api apiInterface;
   // CategoryAdapter categoryAdapter;
   Uri imageUrl, imageUrl1;
   private Bitmap bitmap;
    SliderView sliderView;
    private ArrayList<FoodCategoryResponse> imageModelArrayList;
    private ArrayList<CafeCategory> modelArrayList;
    private RelativeLayout main;
  //  private ArrayList<Category1> arrayList;
    private SliderAdapter adapter;
    public Constant constant;

    Button addNewItem;
    TextView name, email;
    SharedPrefManager sharedPrefManager;
    View view;
    int val;
    TextView c;
    ViewPager viewPager;
    TabLayout tab;


    private String myCategoryImageList;
    private String myCategoryImageNameList;

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
        sliderView = view.findViewById(R.id.imageSlider1);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView1 = view.findViewById(R.id.recycler1);
        cardview = view.findViewById(R.id.cardview);

        if (getActivity() != null) {

          //  imageModelArrayList = eatFruits();
//            menuAdapter = new MenuAdapter(getContext(), imageModelArrayList,recyclerView);
//            recyclerView.setAdapter(menuAdapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
           // menuAdapter.setItemClickListener(itemClickListener);

          //  modelArrayList = eatFruits1();
//            cafeMenuAdapter = new CafeMenuAdapter(getContext(), modelArrayList);
//            recyclerView1.setAdapter(cafeMenuAdapter);
//            recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


            Call<ArrayList<FoodCategoryResponse>> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .foodcategorylist();

            call.enqueue(new Callback<ArrayList<FoodCategoryResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<FoodCategoryResponse>> call, Response<ArrayList<FoodCategoryResponse>> response) {
                    if(response.isSuccessful())
                    {
                        imageModelArrayList=response.body();
                        menuAdapter = new MenuAdapter(getContext(), imageModelArrayList,recyclerView);
                        recyclerView.setAdapter(menuAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<FoodCategoryResponse>> call, Throwable t) {
                    Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }

        // sharedPrefManager = new SharedPrefManager(getActivity());
        //String username = "Hello " + sharedPrefManager.getUser().getUsername();
//        name.setText(username);
//        email.setText(sharedPrefManager.getUser().getEmail());
        // we are creating array list for storing our image urls.
//        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
//
//        // initializing the slider view.
//        SliderView sliderView = view.findViewById(R.id.imageSlider1);
//
//        // adding the urls inside array list
//        sliderDataArrayList.add(new SliderData(imageUrl1));





//        // passing this array list inside our adapter class.
//        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);
//
//        // below method is used to set auto cycle direction in left to
//        // right direction you can change according to requirement.
//        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//        // below method is used to
//        // setadapter to sliderview.
//        sliderView.setSliderAdapter(adapter);
//
//        // below method is use to set
//        // scroll time in seconds.
//        sliderView.setScrollTimeInSec(3);
//
//        // to set it scrollable automatically
//        // we use below method.
//        sliderView.setAutoCycle(true);
//
//        // to start autocycle below method is used.
//        sliderView.startAutoCycle();

        return view;

    }

//    private ArrayList<Category> eatFruits() {
//
//        ArrayList<Category> list = new ArrayList<>();
//
//        for (int i = 0; i < 8; i++) {
//            Category fruitModel = new Category();
//            fruitModel.setName(myImageNameList[i]);
//            fruitModel.setImage(myImageList[i]);
//            list.add(fruitModel);
//        }
//
//        return list;
//    }

//    private ArrayList<CafeCategory> eatFruits1() {
//
//        ArrayList<CafeCategory> list = new ArrayList<>();
//
//        for (int i = 0; i < 8; i++) {
//            CafeCategory fruitModel1 = new CafeCategory();
//            fruitModel1.setName(myNameList[i]);
//            fruitModel1.setImage(myList[i]);
//            list.add(fruitModel1);
//        }
//
//        return list;
//    }

    public void onClick(View view, int position, boolean isLongClick)
    {
        if(position == 0) {
            Intent intent = new Intent(getContext(), Food_DetailActivity.class);
            intent.putExtra("ItemPosition", position);
            startActivity(intent);
        }


    }




}


