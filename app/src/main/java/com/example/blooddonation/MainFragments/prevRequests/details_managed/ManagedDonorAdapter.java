package com.example.blooddonation.MainFragments.prevRequests.details_managed;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManagedDonorAdapter extends RecyclerView.Adapter<ManagedDonorAdapter.ViewHolder> {

    private final List<ManagedDonorItem> managedDonorItems;
    private final Context context;
    private final FragmentManagedDonor parent;

    public ManagedDonorAdapter(List<ManagedDonorItem> managedDonorItems, Context context, FragmentManagedDonor parent) {
        this.managedDonorItems = managedDonorItems;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ManagedDonorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ManagedDonorAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
