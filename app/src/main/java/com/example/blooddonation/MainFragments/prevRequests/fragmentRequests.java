package com.example.blooddonation.MainFragments.prevRequests;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blooddonation.MainFragments.prevRequests.fragments.FragmentStatusAdapter;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentRequestsBinding;
import com.google.android.material.tabs.TabLayout;


public class fragmentRequests extends Fragment {

    FragmentRequestsBinding binding;
    Context mContext;
    FragmentStatusAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_requests, container, false);
        binding = FragmentRequestsBinding.inflate(inflater, container, false);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        adapter = new FragmentStatusAdapter(fragmentManager, getLifecycle());
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Running"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Managed"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Completed"));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

        return binding.getRoot();
    }

}