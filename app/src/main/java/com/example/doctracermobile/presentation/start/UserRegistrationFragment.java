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

    //Получает данные пользователя из формы регистрации
    private User getUserFromForm() {
        String name = ((EditText) getView().findViewById(R.id.reg_user_edit_name))
                .getText()
                .toString();
        String surname = ((EditText) getView().findViewById(R.id.reg_user_edit_surname))
                .getText()
                .toString();
        String patronum = ((EditText) getView().findViewById(R.id.reg_user_edit_patronum))
                .getText()
                .toString();
        String position = ((EditText) getView().findViewById(R.id.reg_user_edit_position))
                .getText()
                .toString();
        String phone = ((EditText) getView().findViewById(R.id.reg_user_edit_phone))
                .getText()
                .toString();
        String email = ((EditText) getView().findViewById(R.id.reg_user_edit_email))
                .getText()
                .toString();
        String password = ((EditText) getView().findViewById(R.id.reg_user_edit_pass))
                .getText()
                .toString(); // строка из первого поля

        return new User(name, surname, patronum, position, phone, email, password);
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
                User user = getUserFromForm();
                String password_d = ((EditText) getView().findViewById(R.id.reg_user_edit_pass_d))
                        .getText()
                        .toString();

                //Проверка пустых полей
                if (user.emptyFieldCheck() && password_d.equals("")) {
                    Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                // Проврека заглавных букв
                if (!UserDataValidator.capitalLetterCheck(user.getName()) ||
                        !UserDataValidator.capitalLetterCheck(user.getSurname()) ||
                        !UserDataValidator.capitalLetterCheck(user.getPatronum()) ||
                        !UserDataValidator.capitalLetterCheck(user.getPosition())) {
                    Snackbar.make(v, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                String validatorReply; //сюда будем класть ответы валидатора
                validatorReply = UserDataValidator.phoneCheck(user.getPhone()); //Проверка телефона
                if (validatorReply != null) {
                    Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
                    return;
                }
                validatorReply = UserDataValidator.passwordCheck(user.getPass()); //Валиддация пароля
                if (validatorReply != null) {
                    Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (!user.getPass().equals(password_d)) {//если введены разные строки
                    Snackbar.make(v, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
                } else {
                    user.setPosition(user
                            .getPhone()
                            .replaceAll(" ", "")
                            .replaceAll("-", ""));
                    Company company = (Company) getArguments().getSerializable("company");
                    new RegTask(user, company).execute();
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
                EmailConfirmationFragment fragment = new EmailConfirmationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email", user.getEmail());
                //TODO написать переход к другому фрагменту
            } else {
                Snackbar.make(confirmButton, "Регистрация не удалась!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}