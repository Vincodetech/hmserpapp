package com.beingknow.eatit2020.NavFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beingknow.eatit2020.R;


public class TakeawayFragment extends Fragment {





    public TakeawayFragment() {

    }


    public static TakeawayFragment newInstance(String param1, String param2) {
        TakeawayFragment fragment = new TakeawayFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_takeaway, container, false);

        return view;
    }
}