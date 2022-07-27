package com.example.doctracermobile.utile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doctracermobile.presentation.account.CreatedTasksFragment;

public class TaskPageAdapter extends FragmentStateAdapter {

    public TaskPageAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return CreatedTasksFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
