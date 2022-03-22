package com.example.doctracermobile.presentation.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.doctracermobile.R;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import static com.example.doctracermobile.util.Constants.APP_PREFERENCES;

public class AccessEditFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AccessEditFragment() {
        // Required empty public constructor
    }

    public static AccessEditFragment newInstance(String param1, String param2) {
        AccessEditFragment fragment = new AccessEditFragment();
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
        return inflater.inflate(R.layout.fragment_access_edit, container, false);
    }


}