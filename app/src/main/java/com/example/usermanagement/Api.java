package com.example.usermanagement;

import com.example.usermanagement.ModelResponse.FetchUserResponse;
import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api
{
    @FormUrlEncoded
    @POST("addusers")
    Call<RegisterResponse> register(
            @Field("user_name") String user_name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("loginuser")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("users")
    Call<FetchUserResponse> fetchUsers();
}
