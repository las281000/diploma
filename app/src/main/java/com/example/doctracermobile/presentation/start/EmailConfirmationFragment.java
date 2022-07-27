package com.example.doctracermobile.presentation.start;

import android.os.AsyncTask;
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
import com.example.doctracermobile.presentation.account.AccountActivity;
import com.example.doctracermobile.repository.UserClient;
import com.google.android.material.snackbar.Snackbar;


public class EmailConfirmationFragment extends Fragment {


    private Button buttConfirm;
    private String email;
    private boolean newEmployeeFlag;

    //Слушатель кнопки подстведжения почты
    private final View.OnClickListener confirmListener = (v) -> {
        String code = ((EditText) getView().findViewById(R.id.confirm_edit_code))
                .getText()
                .toString();
        new ConfTask(code, email).execute();
    };

    public EmailConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("email");
            newEmployeeFlag = getArguments().getBoolean("newEmployee");
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
        buttConfirm.setOnClickListener(confirmListener);
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
                if (newEmployeeFlag) {
                    ((AccountActivity) getActivity())
                            .getNavController()
                            .navigate(R.id.action_emailConfirmationFragment2_to_nav_staff);
                } else {
                    ((StartActivity) getActivity())
                            .getNavController()
                            .navigate(R.id.action_emailConfirmationFragment_to_entryFragment);
                }
            } else {
                Snackbar.make(buttConfirm, "Неверный код!", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}