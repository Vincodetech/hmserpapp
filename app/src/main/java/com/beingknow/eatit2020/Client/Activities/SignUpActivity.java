package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.NonNull;
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

import com.beingknow.eatit2020.ModelResponse.RegisterResponse;
import com.beingknow.eatit2020.Models.ClientUsers;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView register_link;
    EditText name,email,phone,password;
    Button register;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        register_link = (TextView)findViewById(R.id.txt_Goto_Login);
        name = (EditText)findViewById(R.id.edtName);
        email = (EditText)findViewById(R.id.edtEmail);
        phone = (EditText)findViewById(R.id.edtPhone);
        password = (EditText)findViewById(R.id.edtPassword);
        register = (Button)findViewById(R.id.btnRegister);
        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerUser();
            }
        });
    }

    private void registerUser()
    {
        String username = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPhone = phone.getText().toString();
        String userPassword = password.getText().toString();

        final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();


        if(username.isEmpty())
        {
            name.requestFocus();
            name.setError("Please Enter your Name");
            return;
        }
        if(userEmail.isEmpty())
        {
            email.requestFocus();
            email.setError("Please Enter your Email");
            return;
        }
        if(userPhone.isEmpty())
        {
            phone.requestFocus();
            phone.setError("Please Enter your Mobile No.");
            return;
        }
        if(userPhone.length() < 10)
        {
            phone.requestFocus();
            phone.setError("Please Enter your Correct Mobile No.");
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

        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(username,userEmail,userPhone,userPassword,1,2);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response)
            {
                RegisterResponse registerResponse = response.body();
                if(response.isSuccessful())
                {
                    if (registerResponse != null && registerResponse.getError().equals("000"))
                    {
                        Toast.makeText(SignUpActivity.this,registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                else
                {
                    if (registerResponse != null) {
                        Toast.makeText(SignUpActivity.this,registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t)
            {
                Toast.makeText(SignUpActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }
}