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
import com.example.doctracermobile.entity.Project;
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.repository.ProjectClient;
import com.example.doctracermobile.usecase.DataValidator;
import com.google.android.material.snackbar.Snackbar;


public class UserRegistrationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Project project;
    private User user;

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

    private final View.OnClickListener confirmButtListener = (v) -> {
        user = getUserFromForm();
        String password_d = ((EditText) getView().findViewById(R.id.reg_user_edit_pass_d))
                .getText()
                .toString();

        //Проверка пустых полей
        if (user.emptyFieldCheck() && password_d.equals("")) {
            Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }

        // Проврека заглавных букв
        if (!DataValidator.capitalLetterCheck(user.getName()) ||
                !DataValidator.capitalLetterCheck(user.getSurname()) ||
                !DataValidator.capitalLetterCheck(user.getPatronum()) ||
                !DataValidator.capitalLetterCheck(user.getPosition())) {
            Snackbar.make(v, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }

        String validatorReply; //сюда будем класть ответы валидатора
        validatorReply = DataValidator.phoneCheck(user.getPhone()); //Проверка телефона
        if (validatorReply != null) {
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }
        validatorReply = DataValidator.passwordCheck(user.getPass()); //Валиддация пароля
        if (validatorReply != null) {
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!user.getPass().equals(password_d)) {//если введены разные строки
            Snackbar.make(v, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
        } else {
            user.setPhone(user
                    .getPhone()
                    .replaceAll(" ", "")
                    .replaceAll("-", ""));

            new RegTask(user, project).execute();
        }
    };

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
            //получение объекта проекта из прошлого фрагмента
            project = (Project) getArguments().getSerializable("project");
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
        String title = getActivity().getResources().getString(R.string.manager_reg_fragment_title);
        ((StartActivity)getActivity()).setTitle(title);

        confirmButton = (Button) getView().findViewById(R.id.reg_user_but_confirm);
        confirmButton.setOnClickListener(confirmButtListener);
    }

    private class RegTask extends AsyncTask<Void, Void, Boolean> {
        private User user;
        private Project project;

        private RegTask(User user, Project project) {
            this.user = user;
            this.project = project;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return ProjectClient.register(project, user);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Bundle bundle = new Bundle();
                bundle.putString("email", user.getEmail());
                ((StartActivity) getActivity())
                        .getNavController()
                        .navigate(R.id.action_userRegistrationFragment_to_emailConfirmationFragment,bundle);
            } else {
                Snackbar.make(confirmButton, "Регистрация не удалась!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}