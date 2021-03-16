package com.example.usermanagement.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.usermanagement.Constant;
import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.RegisterResponse;
import com.example.usermanagement.NavFragment.DashboardFragment;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;
import com.example.usermanagement.SharedPrefManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView loginlink;
    EditText email, password;
    Button login;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginlink = (TextView) findViewById(R.id.txtGotoRegister);
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
        login = (Button) findViewById(R.id.btnLogin);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
               // login();
            }
        });

    }

    private void login() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (userEmail.isEmpty()) {
            email.requestFocus();
            email.setError("Please Enter your Email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.requestFocus();
            email.setError("Please Enter Correct Email");
            return;
        }
        if (userPassword.isEmpty()) {
            password.requestFocus();
            password.setError("Please Enter your Password");
            return;
        }
        if (userPassword.length() < 6) {
            password.requestFocus();
            password.setError("Please Enter your Password to  Maximum 6 Characters");
            return;
        }

//        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN, response -> {
//            JsonObject object = new JsonObject();
//            if(object.getAsBoolean())
//            {
//                JsonObject user = object.getAsJsonObject("users");
//                SharedPreferences userPref = getApplicationContext().getSharedPreferences("users", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = userPref.edit();
//                editor.putString("email",user.getAsString());
//                editor.putString("password",user.getAsString());
//                editor.apply();
//                Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Login.this, Home.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//
//            }
//        }, error -> {
//            error.printStackTrace();
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                map.put("email",email.getText().toString().trim());
//                map.put("password",password.getText().toString());
//                return map;
//            }
//        };
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", email.getText().toString().trim());
                map.put("password", password.getText().toString());
                return map;

            }
        };
        int socketTimeOut = 10000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);

        queue.add(request);
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constant.LOGIN,
//                null, new com.android.volley.Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.e("Response", response.toString());
//            }
//        },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Response", error.toString());
//                    }
//                }) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                map.put("email",email.getText().toString().trim());
//                map.put("password",password.getText().toString());
//                return map;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                map.put("Content-Type","application/x-www-form-urlencoded");
//                return map;
//            }
//        };
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
    }



    private void userLogin()
    {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(userEmail.isEmpty())
        {
            email.requestFocus();
            email.setError("Please Enter your Email");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            email.requestFocus();
            email.setError("Please Enter Correct Email");
            return;
        }
        if(userPassword.isEmpty())
        {
            password.requestFocus();
            password.setError("Please Enter your Password");
            return;
        }
        if(userPassword.length() < 6)
        {
            password.requestFocus();
            password.setError("Please Enter your Password to  Maximum 6 Characters");
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(userEmail,userPassword);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                LoginResponse loginResponse = response.body();
                if(response.isSuccessful())
                {
                    if(loginResponse.getError().equals("200"))
                    {
                       // sharedPrefManager.saveUser(loginResponse.getUser());
                        Intent intent = new Intent(Login.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(Login.this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(Login.this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t)
            {
                Toast.makeText(Login.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if(sharedPrefManager.isLoggedIn())
        {
            Intent intent = new Intent(Login.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}