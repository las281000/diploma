package com.example.doctracermobile.presentation.account;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.User;


public class EditDataFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private User user;
    private EditText phoneEdit;

    public EditDataFragment() {
        // Required empty public constructor
    }

    public static EditDataFragment newInstance(String param1, String param2) {
        EditDataFragment fragment = new EditDataFragment();
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
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/

            user = (User) getArguments().getSerializable("user");
            Log.e("GETTING_ARGUMENT", user.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_data, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneEdit = (EditText) getView().findViewById(R.id.change_edit_phone);
        phoneEdit.addTextChangedListener(new PhoneNumberFormattingTextWatcher("RU"));

        ((EditText) getView().findViewById(R.id.change_edit_name)).setText(user.getName());
        ((EditText) getView().findViewById(R.id.change_edit_surname)).setText(user.getSurname());
        ((EditText) getView().findViewById(R.id.change_edit_patronum)).setText(user.getPatronum());
        ((EditText) getView().findViewById(R.id.change_edit_position)).setText(user.getPosition());
        phoneEdit.setText(user.getPhone());
        ((EditText) getView().findViewById(R.id.change_edit_email)).setText(user.getEmail());
        ((EditText) getView().findViewById(R.id.change_edit_pass)).setText(user.getPass());
        ((EditText) getView().findViewById(R.id.change_edit_pass_d)).setText(user.getPass());

    }


}