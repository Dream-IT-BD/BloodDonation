package com.example.blooddonation.MainFragments.prevRequests;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.blooddonation.MainFragments.prevRequests.details_interested.FragmentInterestedDonor;
import com.example.blooddonation.MainFragments.prevRequests.details_managed.FragmentManagedDonor;

public class InterestedAndManagedAdapter extends FragmentStateAdapter {
    public InterestedAndManagedAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return  new FragmentManagedDonor();
        }

        return new FragmentInterestedDonor();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
