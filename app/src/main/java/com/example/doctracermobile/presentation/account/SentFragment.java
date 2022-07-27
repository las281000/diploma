package com.example.doctracermobile.presentation.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doctracermobile.R;
import com.example.doctracermobile.utile.TaskPageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SentFragment extends Fragment {

    FloatingActionButton newTaskBtn;
    TaskPageAdapter pageAdapter;
    ViewPager2 viewPager;

    private final View.OnClickListener newTaskBtnListener = (v) ->{
        ((AccountActivity) getActivity())
                .getNavController().navigate(R.id.action_nav_sent_to_taskCreationFragment);
    };

    public SentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newTaskBtn = getView().findViewById(R.id.sent_newTask_fab);
        newTaskBtn.setOnClickListener(newTaskBtnListener);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        pageAdapter = new TaskPageAdapter(fm);

        viewPager = getView().findViewById(R.id.sent_view_pager);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);
    }


}