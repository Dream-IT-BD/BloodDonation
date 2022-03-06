package com.example.blooddonation.MainFragments.prevRequests.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentStatusAdapter extends FragmentStateAdapter {
    public FragmentStatusAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new FragmentManaged();
            case 2:
                return new FargmentCompleted();
        }
        return new FragmentRunning();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
