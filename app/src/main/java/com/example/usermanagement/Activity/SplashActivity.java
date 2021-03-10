package com.example.usermanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.usermanagement.MyReceiver;
import com.example.usermanagement.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private BroadcastReceiver MyReceiver = null;
    private ImageView logo;
    private static int splash=5000;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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

                    Intent i = new Intent(SplashActivity.this, com.example.usermanagement.Activity.Login.class);
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