package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.ModelResponse.LoginResponse;
import com.beingknow.eatit2020.Models.ClientUsers;
import com.beingknow.eatit2020.NavFragment.DashboardFragment;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;
import com.beingknow.eatit2020.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    TextView login_link,skip;
    EditText email, password;
    Button login;
    SharedPrefManager sharedPrefManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        login_link = (TextView) findViewById(R.id.txt_Goto_Register);
        skip = (TextView)findViewById(R.id.skip);
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
        login = (Button) findViewById(R.id.btnLogin);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin()
    {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();


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
                .login(userEmail,userPassword,1);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                LoginResponse loginResponse = response.body();
                if(response.isSuccessful())
                {
                    if (loginResponse != null && loginResponse.getError().equals("200")) {
                        sharedPrefManager.saveUser(loginResponse);
                        Toast.makeText(SignInActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        new SweetAlertDialog(
                                SignInActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Login")
                                .setContentText("You have Login Successfully...!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
                else
                {
                    if (loginResponse != null) {
                        Toast.makeText(SignInActivity.this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        new SweetAlertDialog(SignInActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something Went Wrong!")
                                .show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t)
            {
                Toast.makeText(SignInActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                new SweetAlertDialog(SignInActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something Went Wrong!")
                        .show();
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if(sharedPrefManager.isLoggedIn())
        {
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}