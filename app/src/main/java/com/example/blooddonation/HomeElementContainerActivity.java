package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.blooddonation.MainFragments.FragmentFindBloodDonor;
import com.example.blooddonation.MainFragments.home.FragmentHome;
import com.example.blooddonation.MainFragments.prevRequests.fragRequests;

public class HomeElementContainerActivity extends AppCompatActivity {

    String intentFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_element_container);

        Intent intent = getIntent();
        intentFlag = intent.getStringExtra("frag");

        if (intentFlag.equals("viewBloodRequest")) {
            fragmentTransaction(new FragmentHome());
        } else if (intentFlag.equals("addBloodRequest")) {
            fragmentTransaction(new FragmentFindBloodDonor());
        } else {
            fragmentTransaction(new fragRequests());
        }

    }

    public  void fragmentTransaction(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dashboard_container, fragment)
                .commit();
    }

}