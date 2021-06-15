package com.beingknow.eatit2020;

import android.content.Context;
import android.content.SharedPreferences;

import com.beingknow.eatit2020.ModelResponse.CartResponse;
import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.ModelResponse.OrderResponse1;
import com.beingknow.eatit2020.ModelResponse.UserResponse;
import com.beingknow.eatit2020.Models.Item;

import java.util.ArrayList;

public class SharedPrefManager {
    private static final String Shared_Pref = "Hotel1";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(LoginResponse loginResponse) {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id", loginResponse.getId());
        editor.putString("user_name", loginResponse.getUser_name());
        editor.putString("email", loginResponse.getEmail());
        editor.putBoolean("logged", true);
        editor.apply();
    }

    public void saveOrder(OrderResponse1 orderResponse1)
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("order_id", orderResponse1.getId());
        editor.putString("order_no", orderResponse1.getOrder_no());
        editor.apply();
    }

    public boolean isLoggedIn() {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged", false);
    }

    public UserResponse getUser() {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        return new UserResponse(
                sharedPreferences.getInt("id", 0),
                sharedPreferences.getString("user_name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getInt("active", 0));
    }

    public OrderResponse1 getOrderId()
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        return new OrderResponse1(
                sharedPreferences.getInt("order_id", 0),
                sharedPreferences.getString("order_no", null));
    }

    public void addCartItem(CartResponse cartResponse) {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("item_id", cartResponse.getItem_id());
        editor.putInt("order_id", cartResponse.getOrder_id());
        editor.putString("quantity", cartResponse.getQuantity());
        editor.putFloat("amount", cartResponse.getAmount());
        editor.apply();
    }

    public void addCart(Item item)
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("name", item.getName());
        editor.putString("quantity", item.getQuantity());
        editor.putFloat("price", (float) item.getPrice());
        editor.apply();
    }
    public void addCart1(ArrayList<Item> item)
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        for(int i = 0; i < item.size(); i++) {
            editor.putString("name", item.get(i).getName());
            editor.putString("quantity", item.get(i).getQuantity());
            editor.putFloat("price", (float) item.get(i).getPrice());
        }
            editor.apply();
    }

    public void logout()
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
