package com.example.blooddonation.MainFragments.UserProfile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

//                bottomSheetLayout();

                FragmentBottomSheet fragmentBottomSheet = new FragmentBottomSheet();
                fragmentBottomSheet.show(getActivity().getSupportFragmentManager(),fragmentBottomSheet.getTag());

//                SharedPreferences accessToken = getContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editAccessToken = accessToken.edit();
//                editAccessToken.clear();
//                editAccessToken.apply();
//
//                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return binding.getRoot();
    }

//    private void bottomSheetLayout() {
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.id.bottom_sheet_layout);
//
//        LinearLayout supportLayout = dialog.findViewById(R.id.layoutSupport);
//        LinearLayout shareLayout = dialog.findViewById(R.id.layoutShare);
//        LinearLayout contactLayout = dialog.findViewById(R.id.layoutContact);
//        LinearLayout logoutLayout = dialog.findViewById(R.id.layoutLogout);
//
//        supportLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Support Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        shareLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Share Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        contactLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Contact Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        logoutLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Logout Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//    }
}