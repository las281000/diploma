package com.example.doctracermobile.presentation.account;

import static com.example.doctracermobile.util.Constants.APP_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.doctracermobile.R;
import com.example.doctracermobile.presentation.start.StartActivity;
import com.example.doctracermobile.repository.Preferences;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment {

    private AppBarConfiguration SettingsMenuConfiguration; //конфиг меню в шторке

/*  private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;*/

    private Button exitButt;
    // Обработчик кнопки выхода из аккаунта
    private final View.OnClickListener exitButtListener = (v) -> {
        SharedPreferences preferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (!Preferences.removePassword(preferences)){
            Snackbar.make(v,"Не удалось удалить данные с устройства.", Snackbar.LENGTH_LONG);
        } else{
            startActivity(new Intent(getActivity(), StartActivity.class));
            getActivity().finish();
        }
    };

    public SettingsFragment() {
        // Required empty public constructor
    }

/*    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exitButt = getView().findViewById(R.id.set_butt_exit);
        exitButt.setOnClickListener(exitButtListener);
    }

    }