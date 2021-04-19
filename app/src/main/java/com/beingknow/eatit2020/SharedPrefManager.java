package com.beingknow.eatit2020;

import android.content.Context;
import android.content.SharedPreferences;

import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.ModelResponse.UserResponse;

public class SharedPrefManager
{
    private static final String Shared_Pref = "Hotel1";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(LoginResponse loginResponse)
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id",loginResponse.getId());
        editor.putString("user_name",loginResponse.getUser_name());
        editor.putString("email",loginResponse.getEmail());
        editor.putBoolean("logged",true);
        editor.apply();
    }

    public boolean isLoggedIn()
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged",false);
    }

    public UserResponse getUser()
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        return new UserResponse(
                sharedPreferences.getInt("id",0),
                sharedPreferences.getString("user_name",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getInt("active", 0));
    }

    public void logout()
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
