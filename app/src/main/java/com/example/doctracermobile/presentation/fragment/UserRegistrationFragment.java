package com.example.doctracermobile.presentation.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Company;
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.repository.CompanyClient;
import com.example.doctracermobile.usecase.UserDataValidator;
import com.google.android.material.snackbar.Snackbar;


public class UserRegistrationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button confirmButton;

    public UserRegistrationFragment() {
        // Required empty public constructor
    }

    public static UserRegistrationFragment newInstance(String param1, String param2) {
        UserRegistrationFragment fragment = new UserRegistrationFragment();
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
        return inflater.inflate(R.layout.fragment_user_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        confirmButton = (Button) getView().findViewById(R.id.reg_user_but_confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) getView().findViewById(R.id.reg_user_edit_name)).getText().toString();
                String surname = ((EditText) getView().findViewById(R.id.reg_user_edit_surname)).getText().toString();
                String patronum = ((EditText) getView().findViewById(R.id.reg_user_edit_patronum)).getText().toString();
                String position = ((EditText) getView().findViewById(R.id.reg_user_edit_position)).getText().toString();

                String phone = ((EditText) getView().findViewById(R.id.reg_user_edit_phone)).getText().toString();
                String email = ((EditText) getView().findViewById(R.id.reg_user_edit_email)).getText().toString();

                String password = ((EditText) getView().findViewById(R.id.reg_user_edit_pass)).getText().toString(); // строка из первого поля
                String password_d = ((EditText) getView().findViewById(R.id.reg_user_edit_pass_d)).getText().toString(); // строка из второго поля

                //Проверка пустых полей
                if (name.equals("") ||
                        surname.equals("") ||
                        patronum.equals("") ||
                        position.equals("") ||
                        phone.equals("+7") ||
                        phone.equals("") ||
                        email.equals("") ||
                        password.equals("") ||
                        password_d.equals("")) {
                    Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (!UserDataValidator.capitalLetterCheck(name) ||
                        !UserDataValidator.capitalLetterCheck(surname) ||
                        !UserDataValidator.capitalLetterCheck(patronum) ||
                        !UserDataValidator.capitalLetterCheck(position)) {
                    Snackbar.make(v, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                //сюда будем класть ответы валидатора
                String validatorReply;
                validatorReply = UserDataValidator.phoneCheck(phone);
                if (validatorReply != null) { //если к строке есть замечания
                    Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
                    return;
                }

                validatorReply = UserDataValidator.passwordCheck(password);
                if (validatorReply != null) { //если к строке есть замечания
                    Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
                    return;
                }

                //если введены разные строки
                if (!password.equals(password_d)) {
                    Snackbar.make(v, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
                } else { //если введены одинаковые строки
                    /*Company company = (Company) this.getIntent().getSerializableExtra("company");
                    phone = phone.replaceAll(" ", "").replaceAll("-", "");
                    User user = new User(name, surname, patronum, position, phone, email, password);

                    new RegTask(user, company).execute();*/
                }
            }
        });

    }

    private class RegTask extends AsyncTask<Void, Void, Boolean> {
        private User user;
        private Company company;

        private RegTask(User user, Company company) {
            this.user = user;
            this.company = company;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return CompanyClient.register(company, user);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
            /*Intent verification = new Intent(HeadRegistrationActivity.this, VerificationActivity.class);
            verification.putExtra("email", user.getEmail());
            startActivity(verification);*/
            } else {
                /*Snackbar.make(editName, "Регистрация не удалась!", Snackbar.LENGTH_LONG).show();*/
            }
        }
    }

}