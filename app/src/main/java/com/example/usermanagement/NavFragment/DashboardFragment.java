package com.example.usermanagement.NavFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.R;
import com.example.usermanagement.SharedPrefManager;
import com.example.usermanagement.SliderAdapter;
import com.example.usermanagement.SliderData;
import com.example.usermanagement.UserAdapter;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class DashboardFragment extends Fragment
{
    SliderView sliderView;
    private SliderAdapter adapter;
    Button addNewItem;
    TextView name,email;
    SharedPrefManager sharedPrefManager;
    View view;
    int val;
    TextView c;
    ViewPager viewPager;
    TabLayout tab;

    String Url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
    String Url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp";
    String Url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";

    public static DashboardFragment newInstance(String contIndex) {
        DashboardFragment cf = new DashboardFragment();

        Bundle args = new Bundle();
        args.putString("contIndex", contIndex);
        cf.setArguments(args);
        return cf;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        name = view.findViewById(R.id.name);
//        email = view.findViewById(R.id.email);
     //   sliderView = view.findViewById(R.id .imageSlider);
      //  sharedPrefManager = new SharedPrefManager(getActivity());
       // String username = "Hello " + sharedPrefManager.getUser().getUsername();
//        name.setText(username);
//        email.setText(sharedPrefManager.getUser().getEmail());
        // we are creating array list for storing our image urls.
      //  ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
      //  SliderView sliderView = view.findViewById(R.id.imageSlider);

        // adding the urls inside array list
//        sliderDataArrayList.add(new SliderData(Url1));
//        sliderDataArrayList.add(new SliderData(Url2));
//        sliderDataArrayList.add(new SliderData(Url3));


        // passing this array list inside our adapter class.
     //   SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
       // sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
       // sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
       // sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
       // sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
       // sliderView.startAutoCycle();

//        String[] data = {"Java", "Python", "C++", "C#", "Angular", "Go"};
//
//        ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_selected, data);
//        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//        Spinner spinner = view.findViewById(R.id.spinner);
//        spinner.setAdapter(adapter1);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//              //  Toast.makeText(getActivity(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });






        return view;



    }


}



