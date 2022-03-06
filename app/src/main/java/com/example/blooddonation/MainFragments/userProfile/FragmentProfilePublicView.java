package com.example.blooddonation.MainFragments.userProfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentProfilePublicViewBinding;

public class FragmentProfilePublicView extends Fragment {

    Context mContext;
    FragmentProfilePublicViewBinding binding;

    public FragmentProfilePublicView() {
        // Required empty public constructor
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
        binding = FragmentProfilePublicViewBinding.inflate(inflater, container, false);

        String userContactNumber = "01712508520";

        binding.btnContactNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userContactNumber));
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}