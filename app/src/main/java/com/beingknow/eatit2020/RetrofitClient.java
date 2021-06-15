package com.beingknow.eatit2020;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    private static String BASE_URL = "http://192.168.42.235:8000/api/";
    private static com.beingknow.eatit2020.RetrofitClient retrofitClient;
    private static Retrofit retrofit;
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    private RetrofitClient()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(15, TimeUnit.SECONDS);
        client.readTimeout(15, TimeUnit.SECONDS);
        client.writeTimeout(15, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public static synchronized com.beingknow.eatit2020.RetrofitClient getInstance()
    {
        if(retrofitClient == null)
        {
            retrofitClient = new com.beingknow.eatit2020.RetrofitClient();
        }
        return retrofitClient;
    }

    public com.beingknow.eatit2020.Api getApi()
    {
        return retrofit.create(com.beingknow.eatit2020.Api.class);
    }
}
