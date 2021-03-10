package com.example.usermanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.RegisterResponse;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;
import com.example.usermanagement.SharedPrefManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView loginlink;
    EditText email,password;
    Button login;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginlink = (TextView)findViewById(R.id.txtGotoRegister);
        email = (EditText)findViewById(R.id.edtEmail);
        password = (EditText)findViewById(R.id.edtPassword);
        login = (Button)findViewById(R.id.btnLogin);
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
            public void onClick(View v)
            {
                userLogin();
            }
        });

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
                        sharedPrefManager.saveUser(loginResponse.getUser());
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