package com.example.doctracermobile.presentation.fragment;

import static com.example.doctracermobile.util.Constants.APP_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.doctracermobile.presentation.ProfileActivity;
import com.example.doctracermobile.presentation.StartActivity;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.request.JointUserCompany;
import com.example.doctracermobile.usecase.UserDataValidator;
import com.google.android.material.snackbar.Snackbar;

public class EntryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    
    SharedPreferences preferences;
    Button buttSingIn; //кнопка входа
    Button buttSignUp; //кнопка регистрации
    Button buttRecovery; //кнопка восстановления пароля

    public EntryFragment() {
        // Required empty public constructor
    }

    public static EntryFragment newInstance(String param1, String param2) {
        EntryFragment fragment = new EntryFragment();
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
        return inflater.inflate(R.layout.fragment_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //добываем сохраненный пароль и логин
        preferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String login = Preferences.getLogin(preferences); //получаем логин из файла настроек
        String password = Preferences.getPassword(preferences); //получаем пароль из файла настроек

        //если нашли логин и пароль, то выполняем вход
        if ((login != null) && (password != null)) {
            new SignInTask(login, password).execute();
            return;
        }

        buttSingIn = (Button) getView().findViewById(R.id.entry_butt_signIn);
        buttSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Получение логина и пароля из полей ввода
                String login = ((EditText) getView().findViewById(R.id.entry_edit_login))
                        .getText()
                        .toString();
                String password = ((EditText) getView().findViewById(R.id.entry_edit_password))
                        .getText()
                        .toString();

                if ((login.equals("") || password.equals(""))) {
                    Snackbar.make(buttSingIn, "Введите логин и пароль", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (UserDataValidator.passwordCheck(password) != null) {
                    Snackbar.make(buttSingIn, UserDataValidator.passwordCheck(password), Snackbar.LENGTH_LONG);
                    return;
                }

                //Отправака запроса
                new SignInTask(login, password).execute();
            }
        });

        buttSignUp = (Button) getView().findViewById(R.id.entry_butt_signUp);
        buttSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgRegistrationFragment fragment = new OrgRegistrationFragment();
                //TODO написать переход к фрагементу регистрации
            }
        });
        
        buttRecovery = (Button) getView().findViewById(R.id.entry_butt_recovery);
        buttRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO написать восстановление пароля
            }
        });

    }

    private class SignInTask extends AsyncTask<Void, Void, JointUserCompany> {

        private final String email;
        private final String password;

        private SignInTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected JointUserCompany doInBackground(Void... voids) {
            JointUserCompany jointUserCompany = UserClient.signIn(email, password);
            return jointUserCompany;
        }

        @Override
        protected void onPostExecute(JointUserCompany jointUserCompany) {
            super.onPostExecute(jointUserCompany);
            if (jointUserCompany != null) {
                jointUserCompany.setPassword(password);

                User user = new User(jointUserCompany.getName(),
                        jointUserCompany.getSurname(),
                        jointUserCompany.getPatronum(),
                        jointUserCompany.getPosition(),
                        jointUserCompany.getPhoneNumber(),
                        jointUserCompany.getEmail(),
                        password);

                Company company = new Company(jointUserCompany.getCompanyName(),
                        jointUserCompany.getType(),
                        jointUserCompany.getCountry(),
                        jointUserCompany.getAddress(),
                        jointUserCompany.getInn(),
                        jointUserCompany.getOgrn());

                Preferences.savePreferences(preferences, user.getEmail(), user.getPass());

                //Если авторизация прошла успешно заврешаем эту активность и переходим в профиль
                Intent signIn = new Intent(getContext(), ProfileActivity.class);
                signIn.putExtra("user", user); //передаем сериализованный объект
                signIn.putExtra("company",company);
                startActivity(signIn);
                getActivity().finish();
            } else {
                Snackbar.make(buttSingIn, "Неверный логин или пароль", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}