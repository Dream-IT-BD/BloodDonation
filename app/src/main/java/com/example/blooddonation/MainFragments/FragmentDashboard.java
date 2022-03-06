package com.example.blooddonation.MainFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blooddonation.MainFragments.home.FragmentHome;
import com.example.blooddonation.MainFragments.prevRequests.fragRequests;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentDashboardBinding;
import com.example.blooddonation.databinding.HomeActivityBinding;


public class FragmentDashboard extends Fragment {

    FragmentDashboardBinding binding;
    Context mContext;
    HomeActivityBinding homeActivityBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        binding.viewBloodRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new FragmentHome();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
                fragmentTransaction.addToBackStack(null);

            }
        });

        binding.addBloodRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentFindBloodDonor();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
        binding.viewPreviousRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new fragRequests();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        return binding.getRoot();
    }
}