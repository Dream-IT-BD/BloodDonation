package com.example.blooddonation.MainFragments.prevRequests.details_managed;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentManagedBinding;

public class FragmentManagedDonor extends Fragment {

    Context mContext;
    FragmentManagedBinding binding;

    public FragmentManagedDonor() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManagedBinding.inflate(inflater, container, false);
        //return inflater.inflate(R.layout.fragment_managed_donor, container, false);




        return binding.getRoot();
    }
}