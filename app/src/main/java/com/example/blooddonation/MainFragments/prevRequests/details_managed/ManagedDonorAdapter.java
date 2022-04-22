package com.example.blooddonation.MainFragments.prevRequests.details_managed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.databinding.InterestedPeopleVecyclerItemBinding;
import com.example.blooddonation.databinding.ManagedPeopleVecyclerItemBinding;

import java.util.List;

public class ManagedDonorAdapter extends RecyclerView.Adapter<ManagedDonorAdapter.ViewHolder> {

    private final List<ManagedDonorItem> managedDonorItems;
    private final Context context;
    private final FragmentManagedDonor parent;

    String contactNumber;

    public ManagedDonorAdapter(List<ManagedDonorItem> managedDonorItems, Context context, FragmentManagedDonor parent) {
        this.managedDonorItems = managedDonorItems;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ManagedDonorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ManagedPeopleVecyclerItemBinding binding = ManagedPeopleVecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagedDonorAdapter.ViewHolder holder, int position) {

        ManagedDonorItem data = managedDonorItems.get(position);
        holder.binding.name.setText(data.getName());

        contactNumber = data.getContactNumber();

        holder.binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactNumber));


                Toast.makeText(context, "Number : " + contactNumber + "\n Call Feature is not working. :( ", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return managedDonorItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ManagedPeopleVecyclerItemBinding binding;
        public ViewHolder(@NonNull ManagedPeopleVecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
