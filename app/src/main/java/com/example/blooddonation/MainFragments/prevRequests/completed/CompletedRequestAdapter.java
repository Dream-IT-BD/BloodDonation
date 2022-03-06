package com.example.blooddonation.MainFragments.prevRequests.completed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.MainFragments.prevRequests.fragments.FargmentCompleted;
import com.example.blooddonation.databinding.CompletedRequestRecyclerItemBinding;

import java.util.List;

public class CompletedRequestAdapter extends RecyclerView.Adapter<CompletedRequestAdapter.ViewHolder> {
    private final List<CompletedRequestItem> completedRequestItems;
    private final Context context;
    private final FargmentCompleted parent;

    public CompletedRequestAdapter(List<CompletedRequestItem> completedRequestItems, Context context, FargmentCompleted parent) {
        this.completedRequestItems = completedRequestItems;
        this.context = context;
        this.parent = parent;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CompletedRequestRecyclerItemBinding binding = CompletedRequestRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompletedRequestItem data = completedRequestItems.get(position);
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
        return completedRequestItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CompletedRequestRecyclerItemBinding binding;
        public ViewHolder(@NonNull CompletedRequestRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

}
