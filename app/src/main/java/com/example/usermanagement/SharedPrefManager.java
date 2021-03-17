package com.example.usermanagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.User;

public class SharedPrefManager
{
    private static final String Shared_Pref = "UserManagement";
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

    public User getUser()
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id",0),
                sharedPreferences.getString("user_name",null),
                sharedPreferences.getString("email",null));

    }

    public void logout()
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
