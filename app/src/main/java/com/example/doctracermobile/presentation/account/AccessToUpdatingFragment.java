package com.example.doctracermobile.presentation.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctracermobile.R;

public class AccessToUpdatingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button nextButt;

    private  final View.OnClickListener nextButtListener = (v) -> {
        ((AccountActivity)getActivity()).
                getNavController()
                .navigate(R.id.action_nav_profile_to_nav_access_edit);
    };

    public AccessToUpdatingFragment() {
        // Required empty public constructor
    }

    public static AccessToUpdatingFragment newInstance(String param1, String param2) {
        AccessToUpdatingFragment fragment = new AccessToUpdatingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_access_to_updating, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextButt = getView().findViewById(R.id.access_butt_next);
        nextButt.setOnClickListener(nextButtListener);
    }


}