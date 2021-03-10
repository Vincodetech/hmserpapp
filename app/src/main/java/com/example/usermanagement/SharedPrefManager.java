package com.example.usermanagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.usermanagement.ModelResponse.User;

public class SharedPrefManager
{
    private static String Shared_Pref = "UserManagement";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(User user)
    {
        sharedPreferences = context.getSharedPreferences(Shared_Pref,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id",user.getId());
        editor.putString("username",user.getUsername());
        editor.putString("email",user.getEmail());
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
        return new User(sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("username",null),
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
