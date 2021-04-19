package com.beingknow.eatit2020;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.util.Objects;

//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    public ActivityMainBinding activityMainBinding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
//        activityMainBinding.txtSlogan.setTypeface(typeface);
//
//        // set on click listener
//        activityMainBinding.btnSignIn.setOnClickListener(this);
//        activityMainBinding.btnSignUp.setOnClickListener(this);
//        activityMainBinding.btnSignInRestaurant.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view == activityMainBinding.btnSignIn) {
//            Intent intent = new Intent(this, SignInActivity.class);
//            startActivity(intent);
//
//        }
//        if (view == activityMainBinding.btnSignUp) {
//            Intent intent = new Intent(this, SignUpActivity.class);
//            startActivity(intent);
//        }
//        if (view == activityMainBinding.btnSignInRestaurant) {
//            Intent intent = new Intent(this, SignInActivityRes.class);
//            startActivity(intent);
//        }
//
//    }
//}
public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver MyReceiver = null;
    private ImageView logo;
    private static int splash=5000;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=(ImageView)findViewById(R.id.imageView);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //slide=AnimationUtils.loadAnimation(R.anim.translate);
        MyReceiver=new MyReceiver();
        Thread t=new Thread()
        {
            public void run()
            {
                try {
                    broadcastIntent();
                    progressBar.setProgress(6000);
                    sleep(6000);
                    // broadcastIntent();

                    Intent i = new Intent(MainActivity.this, com.beingknow.eatit2020.Client.Activities.SignInActivity.class);
                    startActivity(i);
                    finish();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.animation);
        logo.startAnimation(anim);
        if(!isNetworkAvailable(this))
        {
            //Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show(
            finish();
            System.exit(0);
        }

    }
    public void broadcastIntent(){
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        //this.finish();
        super.onPause();
        unregisterReceiver(MyReceiver);
        //Process.killProcess(Process.myPid());
        //super.onDestroy();
        //  this.finish();
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

}