package com.example.blooddonation.MainFragments.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.R;
import com.example.blooddonation.databinding.RequestRecyclerItemBinding;

import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {
    private final List<BloodRequestItem> bloodRequestItems;
    private final Context context;
    private final FragmentHome parent;


    public BloodRequestAdapter(List<BloodRequestItem> bloodRequestItems, Context context, FragmentHome parent) {
        this.bloodRequestItems = bloodRequestItems;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RequestRecyclerItemBinding binding = RequestRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequestItem data = bloodRequestItems.get(position);
        holder.binding.patientName.setText(data.getPatient_name());
        holder.binding.hospitalName.setText(data.getHospital_name());
        holder.binding.bloodGroup.setText(data.getBlood_group());
        holder.binding.tvDate.setText(data.getDate());

///holder.binding.getRoot()
        holder.binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new BloodRequestDetailsFragment();
                Bundle arguments = new Bundle();
                arguments.putString("id",data.getId());
                fragment.setArguments(arguments);

                FragmentTransaction fragmentTransaction = parent.requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.dashboard_container, fragment);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }
    

    @Override
    public int getItemCount() {
        return bloodRequestItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RequestRecyclerItemBinding binding;
        public ViewHolder(@NonNull RequestRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
