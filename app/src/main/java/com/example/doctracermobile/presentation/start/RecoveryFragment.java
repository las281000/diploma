package com.example.doctracermobile.presentation.start;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctracermobile.R;


public class RecoveryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button buttRecover;

    private final View.OnClickListener recoverListener = (v) -> {
        //Добываем адрес почты из поля ввода
        String email = ((EditText) getView().findViewById(R.id.recover_edit_email))
                .getText()
                .toString();

        //TODO дописать приватный класс для отправки на сервер и код в UserClint?
    };

    public RecoveryFragment() {
        // Required empty public constructor
    }

    public static RecoveryFragment newInstance(String param1, String param2) {
        RecoveryFragment fragment = new RecoveryFragment();
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
        return inflater.inflate(R.layout.fragment_recovery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttRecover = (Button) getView().findViewById(R.id.confirm_butt_finish);
        buttRecover.setOnClickListener(recoverListener);
    }
}