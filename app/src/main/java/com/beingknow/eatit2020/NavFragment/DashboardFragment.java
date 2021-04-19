package com.beingknow.eatit2020.NavFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.beingknow.eatit2020.Client.Activities.Food_DetailActivity;
import com.beingknow.eatit2020.Client.Adapters.CafeMenuAdapter;
import com.beingknow.eatit2020.Client.Adapters.MenuAdapter;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.CafeCategory;
import com.beingknow.eatit2020.Models.Category;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.SharedPrefManager;
import com.beingknow.eatit2020.SliderAdapter;
import com.beingknow.eatit2020.SliderData;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private MenuAdapter menuAdapter;
    private CardView cardview;
    private CafeMenuAdapter cafeMenuAdapter;
    ItemClickListener itemClickListener;
   // CategoryAdapter categoryAdapter;
    SliderView sliderView;
    private ArrayList<Category> imageModelArrayList;
    private ArrayList<CafeCategory> modelArrayList;
    private RelativeLayout main;
  //  private ArrayList<Category1> arrayList;
    private SliderAdapter adapter;

    Button addNewItem;
    TextView name, email;
    SharedPrefManager sharedPrefManager;
    View view;
    int val;
    TextView c;
    ViewPager viewPager;
    TabLayout tab;

    String Url1 = "https://blog.dineout-cdn.co.in/blog/wp-content/uploads/2019/10/Blog-10-1030x538.jpg";
    String Url2 = "https://i.dawn.com/large/2019/12/5def64d72b692.jpg";
    String Url3 = "https://www.mediahacker.org/wp-content/uploads/2018/05/Pizza-Restaurant.jpg";
    String Url4 = "https://restaurantindia.s3.ap-south-1.amazonaws.com/s3fs-public/content9442.jpg";
    String Url5 = "https://i.pinimg.com/originals/29/71/1d/29711d6333667d3235d98f369b33181e.jpg";
    String Url6 = "http://topranker4u.com/wp-content/uploads/2017/04/top-sizzler-restaurants-300-200-300x200-1200x675.jpg";

    String Url7 = "http://gopaisa.com/blog/wp-content/uploads/2016/10/Screenshot_5.jpg";
    String Url8 = "https://cdn.grabon.in/gograbon/images/web-images/uploads/1591771548178/food-coupons.jpg";
    String Url9 = "https://www.dineout.co.in/blog/wp-content/uploads/2018/10/WhatsApp-Image-2018-10-18-at-8.06.23-PM.jpeg";
    String Url10 = "https://dash.railrestro.com/images/RR_banner_1612440144.jpg";


    private int[] myImageList = new int[]{R.drawable.baigan_bharta, R.drawable.kashmiri_kofta, R.drawable.lasaniya_bataka, R.drawable.lasaniya_paneer, R.drawable.veg_toofani, R.drawable.veg_kofta, R.drawable.sev_tameta, R.drawable.mysore_masala_dosa};
    private String[] myImageNameList = new String[]{"Baingan ka Bharta", "Kashmiri Kofta", "Lasaniya Bataka", "Lasaniya Paneer", "Veg Toofani", "Veg Kofta", "Sev Tameta", "Mysore Masala Dosa"};

    private int[] myList = new int[]{R.drawable.tea, R.drawable.coffe, R.drawable.cold_coffee, R.drawable.cold_coffee_with_icecreame, R.drawable.ice_creame, R.drawable.faluda, R.drawable.milk_shake, R.drawable.cold_drinks};
    private String[] myNameList = new String[]{"Tea", "Coffee", "Cold Coffee", "Cold Coffee With Ice-Cream", "Ice-Cream", "Faluda", "Milk Shake", "Cold Drinks"};




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
//        name = view.findViewById(R.id.name);
//        email = view.findViewById(R.id.email);
        sliderView = view.findViewById(R.id.imageSlider1);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView1 = view.findViewById(R.id.recycler1);
        cardview = view.findViewById(R.id.cardview);

        if (getActivity() != null) {
//            arrayList = addCategory();
//            //CategoryAdapter adapter = new CategoryAdapter(getActivity(), arrayList);
//            categoryAdapter = new CategoryAdapter(getActivity(), arrayList);
//            recyclerView1.setAdapter(categoryAdapter);
//            recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), 2));
//            recyclerView1.setItemAnimator(new DefaultItemAnimator());

            imageModelArrayList = eatFruits();
            menuAdapter = new MenuAdapter(getContext(), imageModelArrayList,recyclerView);
            recyclerView.setAdapter(menuAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            menuAdapter.setItemClickListener(itemClickListener);

            modelArrayList = eatFruits1();
            cafeMenuAdapter = new CafeMenuAdapter(getContext(), modelArrayList);
            recyclerView1.setAdapter(cafeMenuAdapter);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }

        // sharedPrefManager = new SharedPrefManager(getActivity());
        //String username = "Hello " + sharedPrefManager.getUser().getUsername();
//        name.setText(username);
//        email.setText(sharedPrefManager.getUser().getEmail());
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.imageSlider1);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(Url1));
        sliderDataArrayList.add(new SliderData(Url2));
        sliderDataArrayList.add(new SliderData(Url7));
        sliderDataArrayList.add(new SliderData(Url3));
        sliderDataArrayList.add(new SliderData(Url4));
        sliderDataArrayList.add(new SliderData(Url8));
        sliderDataArrayList.add(new SliderData(Url5));
        sliderDataArrayList.add(new SliderData(Url9));
        sliderDataArrayList.add(new SliderData(Url6));
        sliderDataArrayList.add(new SliderData(Url10));




        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        return view;

    }

    private ArrayList<Category> eatFruits() {

        ArrayList<Category> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Category fruitModel = new Category();
            fruitModel.setName(myImageNameList[i]);
            fruitModel.setImage(myImageList[i]);
            list.add(fruitModel);
        }

        return list;
    }

    private ArrayList<CafeCategory> eatFruits1() {

        ArrayList<CafeCategory> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            CafeCategory fruitModel1 = new CafeCategory();
            fruitModel1.setName(myNameList[i]);
            fruitModel1.setImage(myList[i]);
            list.add(fruitModel1);
        }

        return list;
    }

    public void onClick(View view, int position, boolean isLongClick)
    {
        if(position == 0) {
            Intent intent = new Intent(getContext(), Food_DetailActivity.class);
            intent.putExtra("ItemPosition", position);
            startActivity(intent);
        }


    }




}


