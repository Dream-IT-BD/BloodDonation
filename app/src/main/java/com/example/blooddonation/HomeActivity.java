package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.blooddonation.MainFragments.FragmentDashboard;
import com.example.blooddonation.MainFragments.home.FragmentHome;
import com.example.blooddonation.MainFragments.UserProfile.FragmentProfile;
import com.example.blooddonation.databinding.HomeActivityBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Cannot invoke method length() on null object";
    HomeActivityBinding binding;
    public DrawerLayout drawer;
    public NavigationView navigation;
    public Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    public ExtendedFloatingActionButton btnFloatingActionButton;
    String token, name, number;
    // Change from github


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        name = sharedPreferences.getString("name", "");
        number = sharedPreferences.getString("number", "");


        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentDashboard()).commit();
        //navigation.setCheckedItem(R.id.nav_home);

        bottomBar();
    }

    private void bottomBar() {
        binding.bottomNavigation.setSelectedItemId(R.id.home);


        binding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentDashboard()).commit();

                } else if (item.getItemId() == R.id.nav_findBloodDonor) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentHome()).commit();
                } else if (item.getItemId() == R.id.nav_findHospital) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentProfile()).commit();
                }
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentHome()).commit();

//        if (drawer.isDrawerOpen(GravityCompat.START)){
//            drawer.closeDrawer(GravityCompat.START);
//        }else{
//            super.onBackPressed();
//        }
    }
}
