package com.example.doctracermobile.presentation.start;

import static com.example.doctracermobile.utile.Constants.APP_PREFERENCES;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.example.doctracermobile.presentation.account.AccountActivity;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.ProjectClient;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.usecase.DataValidator;
import com.google.android.material.snackbar.Snackbar;


public class UserRegistrationFragment extends Fragment {

    private Project newProject;
    private User newUser;
    private boolean newEmployeeFlag;
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
        newUser = getUserFromForm();
        String password_d = ((EditText) getView().findViewById(R.id.reg_user_edit_pass_d))
                .getText()
                .toString();

        //Проверка пустых полей
        if (newUser.emptyFieldCheck() && password_d.equals("")) {
            Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }

        // Проврека заглавных букв
        if (!DataValidator.capitalLetterCheck(newUser.getName()) ||
                !DataValidator.capitalLetterCheck(newUser.getSurname()) ||
                !DataValidator.capitalLetterCheck(newUser.getPatronum()) ||
                !DataValidator.capitalLetterCheck(newUser.getPosition())) {
            Snackbar.make(v, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }

        String validatorReply; //сюда будем класть ответы валидатора
        validatorReply = DataValidator.phoneCheck(newUser.getPhoneNumber()); //Проверка телефона
        if (validatorReply != null) {
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }
        validatorReply = DataValidator.passwordCheck(newUser.getPassword()); //Валиддация пароля
        if (validatorReply != null) {
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!newUser.getPassword().equals(password_d)) {//если введены разные строки
            Snackbar.make(v, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
        } else {
            newUser.setPhoneNumber(newUser
                    .getPhoneNumber()
                    .replaceAll(" ", "")
                    .replaceAll("-", ""));

            if (newEmployeeFlag) {
                new EmplRegTask(newUser).execute();
            } else{
                new ProjRegTask(newUser, newProject).execute();
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //получение объекта проекта из прошлого фрагмента
            newProject = (Project) getArguments().getSerializable("project");
            newEmployeeFlag = getArguments().getBoolean("newEmployee"); //получим true, если вызвано для регистрации подчиненного
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
        confirmButton.setOnClickListener(confirmButtListener);

        EditText phone = (EditText) getView().findViewById(R.id.reg_user_edit_phone);
        phone.setText("+7");
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private class ProjRegTask extends AsyncTask<Void, Void, Boolean> {
        private User newUser;
        private Project newProject;

        private ProjRegTask(User newUser, Project newProject) {
            this.newUser = newUser;
            this.newProject = newProject;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return ProjectClient.register(newProject, newUser);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Bundle bundle = new Bundle();
                bundle.putString("email", newUser.getEmail());
                ((StartActivity) getActivity())
                        .getNavController()
                        .navigate(R.id.action_userRegistrationFragment_to_emailConfirmationFragment,bundle);
            } else {
                Snackbar.make(confirmButton, "Регистрация не удалась!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    //регистрируем раба
    private class EmplRegTask extends AsyncTask<Void, Void, Boolean> {
        private final User employee;

        private EmplRegTask(User employee) {
            this.employee = employee;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String login = Preferences.getLogin(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
            String password = Preferences.getPassword(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));

            return UserClient.register(employee, login, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Bundle bundle = new Bundle();
                bundle.putString("email", employee.getEmail());
                if (newEmployeeFlag){
                    bundle.putBoolean("newEmployee", true);
                }
                ((AccountActivity) getActivity())
                        .getNavController()
                        .navigate(R.id.action_userRegistrationFragment2_to_emailConfirmationFragment2,bundle);
            } else {
                Snackbar.make(confirmButton, "Регистрация не удалась!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}