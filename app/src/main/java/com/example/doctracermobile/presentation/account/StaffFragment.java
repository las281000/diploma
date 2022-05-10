package com.example.doctracermobile.presentation.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctracermobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StaffFragment extends Fragment {

    //слушатель кнопы добавления раба
    private final View.OnClickListener fabListener = (v) -> {
        Bundle bundle = new Bundle();
        bundle.putBoolean("newEmployee", true);
        ((AccountActivity) getActivity())
                .getNavController().navigate(R.id.action_nav_staff_to_userRegistrationFragment2,bundle);
    };

    public StaffFragment() {
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
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = getView().findViewById(R.id.staff_reg_fab);
        fab.setOnClickListener(fabListener);


    }



}