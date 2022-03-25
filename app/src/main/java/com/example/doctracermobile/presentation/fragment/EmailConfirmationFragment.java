package com.example.doctracermobile.presentation.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doctracermobile.R;
import com.example.doctracermobile.presentation.StartActivity;
import com.example.doctracermobile.presentation.VerificationActivity;
import com.example.doctracermobile.repository.UserClient;
import com.google.android.material.snackbar.Snackbar;


public class EmailConfirmationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button buttConfirm;

    public EmailConfirmationFragment() {
        // Required empty public constructor
    }

    public static EmailConfirmationFragment newInstance(String param1, String param2) {
        EmailConfirmationFragment fragment = new EmailConfirmationFragment();
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
        return inflater.inflate(R.layout.fragment_email_confirmation, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttConfirm = (Button) getView().findViewById(R.id.confirm_butt_finish);
        buttConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getArguments().getString("email");

                String code = ((EditText) getView().findViewById(R.id.confirm_edit_code))
                        .getText()
                        .toString();

                new ConfTask(code, email).execute();
            }
        });

    }

    private class ConfTask extends AsyncTask<Void, Void, Boolean> {
        private String code;
        private String email;

        private ConfTask(String code, String email) {
            this.code = code;
            this.email = email;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return UserClient.verifyEmail(code, email);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                EntryFragment fragment = new EntryFragment();
                //TODO написать переход к другому фрагменту
            } else {
                Snackbar.make(buttConfirm, "Неверный код!", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}