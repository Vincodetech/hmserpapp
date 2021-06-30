package com.beingknow.eatit2020.NavFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.beingknow.eatit2020.Client.Activities.HomeActivity;
import com.beingknow.eatit2020.Client.Activities.SignInActivity;
import com.beingknow.eatit2020.ModelResponse.UpdateProfileResponse;
import com.beingknow.eatit2020.ModelResponse.UserResponse;
import com.beingknow.eatit2020.ModelResponse.UserProfileResponse;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    TextInputLayout name, email, phone, street1, street2, city, state, country, pincode;
    private SharedPrefManager manager;
    Button update;

    public ProfileFragment() {

    }



    public static ProfileFragment newInstance(String contIndex) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("contIndex", contIndex);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        manager = new SharedPrefManager(getContext());

        update = (Button)view.findViewById(R.id.btnUpdateProfile);

        name = (TextInputLayout)view.findViewById(R.id.txtName);
        email = (TextInputLayout)view.findViewById(R.id.txtEmail);
        phone = (TextInputLayout)view.findViewById(R.id.txtPhone);
        street1 = (TextInputLayout)view.findViewById(R.id.txtStreet1);
        street2 = (TextInputLayout)view.findViewById(R.id.txtStreet2);
        city = (TextInputLayout)view.findViewById(R.id.txtCity);
        state = (TextInputLayout)view.findViewById(R.id.txtState);
        country = (TextInputLayout)view.findViewById(R.id.txtCountry);
        pincode = (TextInputLayout)view.findViewById(R.id.pincode);

        fetchUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                updateUser();
            }
        });
        return view;

    }

    private void fetchUser()
    {
        Call<UserProfileResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .profile(manager.getUser().getId());

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                UserProfileResponse userProfileResponse = response.body();
                if(response.isSuccessful())
                {
                    if(userProfileResponse != null)
                    {
                        System.out.println(userProfileResponse);
                        name.getEditText().setText(userProfileResponse.getUser_name());
                        email.getEditText().setText(userProfileResponse.getEmail());
                        phone.getEditText().setText(userProfileResponse.getPhone());
                        street1.getEditText().setText(userProfileResponse.getStreet1());
                        street2.getEditText().setText(userProfileResponse.getStreet2());
                        city.getEditText().setText(userProfileResponse.getCity());
                        state.getEditText().setText(userProfileResponse.getState());
                        country.getEditText().setText(userProfileResponse.getCountry());
                        pincode.getEditText().setText(userProfileResponse.getPincode());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                    displayAlert(t.getMessage());
            }
        });
    }



            private void updateUser()
        {
        String username = name.getEditText().getText().toString().trim();
        String useremail = email.getEditText().getText().toString().trim();
        String userphone = phone.getEditText().getText().toString().trim();
        String add1 = street1.getEditText().getText().toString().trim();
        String add2 = street2.getEditText().getText().toString().trim();
        String usercity = city.getEditText().getText().toString().trim();
        String userstate = state.getEditText().getText().toString().trim();
        String usercountry = country.getEditText().getText().toString().trim();
        String userpincode = pincode.getEditText().getText().toString().trim();



        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

       Call<UpdateProfileResponse> call = RetrofitClient
               .getInstance()
               .getApi()
               .updateProfile(manager.getUser().getId(), username, useremail, userphone, add1, add2, usercity,
                       userstate, usercountry, userpincode);

       call.enqueue(new Callback<UpdateProfileResponse>() {
           @Override
           public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                UpdateProfileResponse profileResponse = response.body();
                if(response.isSuccessful())
                {
                    if(profileResponse != null)
                    {
                        Toast.makeText(getContext(), "Profile Update Successfully..!", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        new SweetAlertDialog(
                                requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Update Profile")
                                .setContentText("Profile Update Successfully..!")
                                .show();
                    }
                }
           }

           @RequiresApi(api = Build.VERSION_CODES.KITKAT)
           @Override
           public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                    displayAlert(t.getMessage());
               new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                       .setTitleText("Oops...")
                       .setContentText("Something Went Wrong!")
                       .show();
           }
       });



    }



    public void displayAlert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}

