package com.example.doctracermobile.presentation.account;

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
import com.example.doctracermobile.entity.User;
import com.google.android.material.snackbar.Snackbar;

public class AccessToUpdatingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private User user;

    private Button nextButt;

    private  final View.OnClickListener nextButtListener = (v) -> {
        String password = ((EditText) getView().findViewById(R.id.access_edit_password)).getText().toString();

        if (!password.equals(user.getPassword())) {
            Snackbar.make(v, "Неверный пароль!", Snackbar.LENGTH_LONG).show();
        } else {
            //если введен верный пароль, передаем в след. фрагмент данные пользователя
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user );
            ((AccountActivity)getActivity())
                    .getNavController()
                    .navigate(R.id.action_nav_accessing_to_nav_updating_userData, bundle);
        }
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
            user = (User) getArguments().getSerializable("user");
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

        // установка слушателя кнопки "Далее" для перехода к изменению данных
        nextButt = getView().findViewById(R.id.access_butt_next);
        nextButt.setOnClickListener(nextButtListener);
    }
}