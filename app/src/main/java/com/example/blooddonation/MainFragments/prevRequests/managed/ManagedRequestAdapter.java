package com.example.blooddonation.MainFragments.prevRequests.managed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.MainFragments.prevRequests.completed.CompletedRequestItem;
import com.example.blooddonation.MainFragments.prevRequests.fragments.FragmentManaged;
import com.example.blooddonation.databinding.CompletedRequestRecyclerItemBinding;
import com.example.blooddonation.databinding.ManagedRequestRecyclerItemBinding;

import java.util.List;

public class ManagedRequestAdapter extends RecyclerView.Adapter<ManagedRequestAdapter.ViewHolder> {
    private final List<ManagedRequestItem> managedRequestItems;
    private final Context context;
    private final FragmentManaged parent;

    public ManagedRequestAdapter(List<ManagedRequestItem> managedRequestItems, Context context, FragmentManaged parent) {
        this.managedRequestItems = managedRequestItems;
        this.context = context;
        this.parent = parent;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ManagedRequestRecyclerItemBinding binding = ManagedRequestRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ManagedRequestItem data = managedRequestItems.get(position);
        holder.binding.patientName.setText(data.getPatient_name());
        holder.binding.hospitalName.setText(data.getHospital_name());
        holder.binding.bloodGroup.setText(data.getBlood_group());
        holder.binding.tvDate.setText(data.getDate());

///holder.binding.getRoot()
//        holder.binding.button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Fragment fragment = new BloodRequestDetailsFragment();
//                Bundle arguments = new Bundle();
//                arguments.putString("id",data.getId());
//                fragment.setArguments(arguments);
//
//                FragmentTransaction fragmentTransaction = parent.requireActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment).commit();
//                fragmentTransaction.addToBackStack(null);
//
//            }
//        });
    }
    

    @Override
    public int getItemCount() {
        return managedRequestItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ManagedRequestRecyclerItemBinding binding;
        public ViewHolder(@NonNull ManagedRequestRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

}
