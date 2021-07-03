package com.beingknow.eatit2020.Client.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beingknow.eatit2020.Common.Common;
import com.beingknow.eatit2020.Interface.ItemClickListener;
import com.beingknow.eatit2020.Models.Category;
import com.beingknow.eatit2020.NavFragment.DashboardFragment;
import com.beingknow.eatit2020.NavFragment.ProfileFragment;
import com.beingknow.eatit2020.NavFragment.ScanFragment;
import com.beingknow.eatit2020.R;
import com.beingknow.eatit2020.SharedPrefManager;
import com.beingknow.eatit2020.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    BottomNavigationView bottomNavigationView;
    SharedPrefManager sharedPrefManager;
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;
    private static final String Shared_Pref = "Hotel";


     DrawerLayout dl;
    private ActionBarDrawerToggle t;
     NavigationView nv;
    TextView full_name, logged_email;
    String fullName = "Android Studio";
    String Email = "android.studio@android.com";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        nv = (NavigationView)findViewById(R.id.nv);

        full_name = (TextView)nv.getHeaderView(0).findViewById(R.id.txtFullName);
        logged_email = (TextView)nv.getHeaderView(0).findViewById(R.id.txtLEmail);

        if (sharedPrefManager != null){
            fullName = sharedPrefManager.getUser().getUser_Name();
            Email = sharedPrefManager.getUser().getEmail();
        }
        else
        {
            fullName = "Android Studio";
            Email = "android.studio@android.com";
        }
        full_name.setText(fullName);
        logged_email.setText(Email);

        Menu logout_menu = nv.getMenu();
        MenuItem logout_menu_item = logout_menu.findItem(R.id.nav_sign_out);


        if (sharedPrefManager.isLoggedIn()){
            logout_menu_item.setEnabled(true);
                } else {
            logout_menu_item.setEnabled(false);
        }

        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_menu:
                        Toast.makeText(HomeActivity.this, "Menu",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_cart:
                        Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                        startActivity(intent);
                        Toast.makeText(HomeActivity.this, "My Cart",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_orders:
                        Intent intent1 = new Intent(HomeActivity.this, OrderSummaryActivity.class);
                        startActivity(intent1);
                        Toast.makeText(HomeActivity.this, "My Orders",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_sign_out:
                        logout();
                        new SweetAlertDialog(
                                HomeActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Logout")
                                .setContentText("You have Logout Successfully...!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                        Toast.makeText(HomeActivity.this, "Sign Out",Toast.LENGTH_SHORT).show();break;
                        default:
                        return true;
                }
                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch ((item.getItemId()))
        {
            case R.id.dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.scan:
                fragment = new ScanFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        if(fragment!=null)
        {
            loadFragment(fragment);
        }
        return true;
    }
    public void loadFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.relative,fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void logout()
    {
        sharedPrefManager.logout();
    }
}