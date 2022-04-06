package com.example.blooddonation.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blooddonation.Auth.LoginActivity;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentProfileBinding;

public class FragmentProfile extends Fragment {

    FragmentProfileBinding binding;
    Context mContext;
    String token, name, number;


    public FragmentProfile() {
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        name = sharedPreferences.getString("name", "");
        number = sharedPreferences.getString("number", "");


        binding.tvUserName.setText(name);
        binding.tvUserPhone.setText(number);

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences accessToken = getContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
                SharedPreferences.Editor editAccessToken = accessToken.edit();
                editAccessToken.clear();
                editAccessToken.apply();

                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return binding.getRoot();
    }
}