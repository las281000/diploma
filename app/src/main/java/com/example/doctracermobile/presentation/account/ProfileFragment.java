package com.example.doctracermobile.presentation.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Company;
import com.example.doctracermobile.entity.User;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private User user;
    private Button accessButt;

    private View.OnClickListener accessButtListener = (v) -> {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);

        ((AccountActivity) getActivity())
                .getNavController()
                .navigate(R.id.action_nav_profile_to_nav_access_edit);
    };

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accessButt = getView().findViewById(R.id.profile_but_change);
        accessButt.setOnClickListener(accessButtListener);

        if (getArguments() != null){
            user = (User) getArguments().getSerializable("newUserData");
            Log.e("PROFILE", "Новые данные получены фрагментом");
        } else{
            user = (User) getActivity().getIntent().getSerializableExtra("user");
        }

        ((TextView) getView()
                .findViewById(R.id.profile_text_surname))
                .setText(user.getSurname());

        ((TextView) getView()
                .findViewById(R.id.profile_text_name))
                .setText(user.getName());

        ((TextView) getView()
                .findViewById(R.id.profile_text_patronum))
                .setText(user.getPatronum());

        ((TextView) getView()
                .findViewById(R.id.profile_text_position))
                .setText(user.getPosition());

        ((TextView) getView()
                .findViewById(R.id.profile_text_phone))
                .setText(user.getPhone());

        ((TextView) getView()
                .findViewById(R.id.profile_text_email))
                .setText(user.getEmail());

        Company company = (Company) getActivity().getIntent().getSerializableExtra("company");

        ((TextView) getView()
                .findViewById(R.id.profile_org_name))
                .setText(String.format("%s \"%s\"",
                        company.getType(),
                        company.getName()));

        ((TextView) getView()
                .findViewById(R.id.profile_org_country))
                .setText(company.getCountry());

        ((TextView) getView()
                .findViewById(R.id.profile_org_address))
                .setText(company.getAddress());

        ((TextView) getView()
                .findViewById(R.id.profile_org_inn))
                .setText(company.getInn());

        ((TextView) getView()
                .findViewById(R.id.profile_org_ogrn))
                .setText(company.getOgrn());
    }
}