package com.example.blooddonation.MainFragments.prevRequests.running;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.MainFragments.prevRequests.details_interested.FragmentInterestedDonor;
import com.example.blooddonation.MainFragments.prevRequests.fragments.FragmentRunning;
import com.example.blooddonation.MainFragments.prevRequests.details_interested.StatusDetailsFragment;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.RunningRequestRecyclerItemBinding;

import java.util.List;

public class RunningRequestAdapter extends RecyclerView.Adapter<RunningRequestAdapter.ViewHolder> {
    private final List<RunningRequestItem> runningRequestItems;
    private final Context context;
    private final FragmentRunning parent;


    public RunningRequestAdapter(List<RunningRequestItem> runningRequestItems, Context context, FragmentRunning parent) {
        this.runningRequestItems = runningRequestItems;
        this.context = context;
        this.parent = parent;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RunningRequestRecyclerItemBinding binding = RunningRequestRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RunningRequestItem data = runningRequestItems.get(position);
        holder.binding.patientName.setText(data.getPatient_name());
        holder.binding.hospitalName.setText(data.getHospital_name());
        holder.binding.bloodGroup.setText(data.getBlood_group());
        holder.binding.tvDate.setText(data.getDate());

///holder.binding.getRoot()
        holder.binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new StatusDetailsFragment();
                Bundle arguments = new Bundle();
                arguments.putString("id",data.getId());
                fragment.setArguments(arguments);

                String postID = data.getId();

                FragmentTransaction fragmentTransaction = parent.requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.dashboard_container, fragment).addToBackStack(null).commit();
                //fragmentTransaction.addToBackStack(null);

//                Fragment fragmentInterestedDonor = new FragmentInterestedDonor();
//                Bundle arguments1 = new Bundle();
//                arguments1.putString("bloodRequestID", data.getId());
//                fragmentInterestedDonor.setArguments(arguments1);

            }
        });
    }
    

    @Override
    public int getItemCount() {
        return runningRequestItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RunningRequestRecyclerItemBinding binding;
        public ViewHolder(@NonNull RunningRequestRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

}
