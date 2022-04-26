package com.example.blooddonation.MainFragments.UserProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentBottomSheet extends BottomSheetDialogFragment {

    Context mContext;
    FragmentBottomSheetBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);


        binding.layoutSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(mContext, SupportActivity.class));

                //Toast.makeText(mContext, "Support Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        binding.layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Share Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        binding.layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Contact Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        binding.layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Logout Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}