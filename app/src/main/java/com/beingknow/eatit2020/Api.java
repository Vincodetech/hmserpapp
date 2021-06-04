package com.beingknow.eatit2020;

import com.beingknow.eatit2020.ModelResponse.CafeCategory;
import com.beingknow.eatit2020.ModelResponse.CartResponse;
import com.beingknow.eatit2020.ModelResponse.FoodCategoryResponse;
import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.ModelResponse.RegisterResponse;
import com.beingknow.eatit2020.ModelResponse.SliderData;
import com.beingknow.eatit2020.ModelResponse.UpdateProfileResponse;
import com.beingknow.eatit2020.ModelResponse.UserProfileResponse;
import com.beingknow.eatit2020.Models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

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
    Call<ArrayList<FoodCategoryResponse>> foodcategorylist(
            @QueryMap Map<String, String> paramsMap
            );

    @GET("sliderlist")
    Call<ArrayList<SliderData>> sliderlist();

    @GET("foodcategorylist")
    Call<ArrayList<CafeCategory>> cafecategorylist(
            @QueryMap Map<String, String> paramsMap
    );

    @GET("getfooditems")
    Call<ArrayList<Item>> fooditemlist(
            @QueryMap Map<String, String> paramsMap
    );

    @GET("singlefooditem")
    Call<ArrayList<Item>> singleFoodItem(
            @QueryMap Map<String, String> paramsMap
    );

    @FormUrlEncoded
    @POST("addcartitem")
    Call<ArrayList<CartResponse>> addcartitem(
            @Field("item_id") int item_id,
            @Field("order_id") int order_id,
            @Field("quantity") String quantity,
            @Field("amount") float amount
    );
}
