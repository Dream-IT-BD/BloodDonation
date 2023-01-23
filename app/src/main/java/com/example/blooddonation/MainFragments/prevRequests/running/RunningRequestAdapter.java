package com.example.blooddonation.MainFragments.prevRequests.running;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.MainFragments.prevRequests.details_interested.StatusDetailsFragment;
import com.example.blooddonation.MainFragments.prevRequests.fragments.FragmentRunning;
import com.example.blooddonation.MainFragments.prevRequests.importantData;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.RunningRequestRecyclerItemBinding;

import java.util.List;

public class RunningRequestAdapter extends RecyclerView.Adapter<RunningRequestAdapter.ViewHolder> {
    private final List<RunningRequestItem> runningRequestItems;
    private final FragmentRunning parent;


    public RunningRequestAdapter(List<RunningRequestItem> runningRequestItems, FragmentRunning parent) {
        this.runningRequestItems = runningRequestItems;
        this.parent = parent;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RunningRequestRecyclerItemBinding binding = RunningRequestRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
                importantData.BLOOD_REQUEST_ID = data.getId();
                Navigation.findNavController(parent.requireView()).navigate(R.id.action_fragment_requests_to_fragment_requests_details);

            }
        });
    }


    @Override
    public int getItemCount() {
        return runningRequestItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RunningRequestRecyclerItemBinding binding;

        public ViewHolder(@NonNull RunningRequestRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

}
