package com.beingknow.eatit2020;

import com.beingknow.eatit2020.ModelResponse.FoodCategoryResponse;
import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.ModelResponse.RegisterResponse;
import com.beingknow.eatit2020.ModelResponse.UpdateProfileResponse;
import com.beingknow.eatit2020.ModelResponse.UserResponse;
import com.beingknow.eatit2020.ModelResponse.UserProfileResponse;
import com.beingknow.eatit2020.Models.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api
{
    @FormUrlEncoded
    @POST("addusers")
    Call<RegisterResponse> register(
            @Field("user_name") String user_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("loginuser")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("user/{id}")
    Call<UserProfileResponse> profile(@Path("id") int id);

    @FormUrlEncoded
    @POST("updateuser/{id}")
    Call<UpdateProfileResponse> updateProfile(
            @Path("id") int id,
            @Field("user_name") String user_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("street1") String street1,
            @Field("street2") String street2,
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country,
            @Field("pincode") String pincode
    );

    @GET("foodcategorylist")
    Call<ArrayList<FoodCategoryResponse>> foodcategorylist();
}
