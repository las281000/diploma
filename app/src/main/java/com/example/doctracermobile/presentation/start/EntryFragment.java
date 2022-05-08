package com.example.doctracermobile.presentation.start;

import static com.example.doctracermobile.utile.Constants.APP_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.doctracermobile.presentation.account.AccountActivity;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.request.JointUserProject;
import com.example.doctracermobile.usecase.DataValidator;
import com.google.android.material.snackbar.Snackbar;

import java.time.Instant;

public class EntryFragment extends Fragment {

    SharedPreferences preferences;
    Button buttSingIn; //кнопка входа
    Button buttSignUp; //кнопка регистрации
    Button buttRecovery; //кнопка восстановления пароля

    //Слушатель кнопки авторизации
    private final View.OnClickListener signInListener = (v) -> {
        //Получение логина и пароля из полей ввода
        String login = ((EditText) getView().findViewById(R.id.entry_edit_login))
                .getText()
                .toString();
        String password = ((EditText) getView().findViewById(R.id.entry_edit_password))
                .getText()
                .toString();
        if ((login.equals("") || password.equals(""))) {
            Snackbar.make(v, "Введите логин и пароль", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (DataValidator.passwordCheck(password) != null) {
            Snackbar.make(v, DataValidator.passwordCheck(password), Snackbar.LENGTH_LONG);
            return;
        }
        //Отправака запроса
        new SignInTask(login, password).execute();
    };

    //Слушатель кнопкии регистрации
    private final View.OnClickListener signUpListener = (v) -> {
        ((StartActivity) getActivity())
                .getNavController()
                .navigate(R.id.action_entryFragment_to_orgRegistrationFragment);
    };

    //Слушатель кнокпи восстановления
    private final View.OnClickListener recoverListener = (v) -> {
        ((StartActivity) getActivity())
                .getNavController()
                .navigate(R.id.action_entryFragment_to_recoveryFragment);
    };

    public EntryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        String title = getActivity().getResources().getString(R.string.app_name_title);
        ((StartActivity)getActivity()).setTitle(title);

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
        buttSingIn.setOnClickListener(signInListener);

        buttSignUp = (Button) getView().findViewById(R.id.entry_butt_signUp);
        buttSignUp.setOnClickListener(signUpListener);

        buttRecovery = (Button) getView().findViewById(R.id.entry_butt_recovery);
        buttRecovery.setOnClickListener(recoverListener);
    }

    private class SignInTask extends AsyncTask<Void, Void, JointUserProject> {

        private final String email;
        private final String password;

        private SignInTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected JointUserProject doInBackground(Void... voids) {
            JointUserProject jointUserProject = UserClient.signIn(email, password);
            return jointUserProject;
        }

        @Override
        protected void onPostExecute(JointUserProject jointUserProject) {
            super.onPostExecute(jointUserProject);
            if (jointUserProject != null) {
                jointUserProject.setPassword(password);

                User user = new User(jointUserProject.getName(),
                        jointUserProject.getSurname(),
                        jointUserProject.getPatronum(),
                        jointUserProject.getPosition(),
                        jointUserProject.getPhoneNumber(),
                        jointUserProject.getEmail(),
                        password);

                Project project = new Project(
                        jointUserProject.getProjectName(),
                        jointUserProject.getDescription(),
                        Instant.parse(jointUserProject.getStartDate()),
                        Instant.parse(jointUserProject.getEndDate()));

                Preferences.savePreferences(preferences, user.getEmail(), user.getPass());

                //Если авторизация прошла успешно заврешаем эту активность и переходим в АКТИВНОСТЬ профиля
                Intent signIn = new Intent(getContext(), AccountActivity.class);
                signIn.putExtra("user", user); //передаем сериализованный объект
                signIn.putExtra("project", project);
                startActivity(signIn);
                getActivity().finish();
            } else {
                Snackbar.make(buttSingIn, "Неверный логин или пароль", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}