package com.example.doctracermobile.presentation;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Company;
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.request.JointUserCompany;
import com.example.doctracermobile.usecase.UserDataValidator;
import com.google.android.material.snackbar.Snackbar;

import static com.example.doctracermobile.util.Constants.APP_PREFERENCES;

public class StartActivity extends AppCompatActivity {

    EditText loginEdit;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String login = Preferences.getLogin(preferences); //получаем логин из файла настроек
        String password = Preferences.getPassword(preferences); //получаем пароль из файла настроек


        if ((login != null) && (password != null)) {
            new SignInTask(login, password).execute();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //Восстановление пароля (потом)
    public void onClick_recover(View view) {
        Intent recover = new Intent(this, RecoveryActivity.class);
        startActivity(recover);
    }

    //Авторизация
    public void onClick_signIn(View view) {
        loginEdit = findViewById(R.id.auth_edit_login);
        EditText passwordEdit = findViewById(R.id.auth_edit_password);

        String login = loginEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if ((login.equals("") || password.equals(""))) {
            Snackbar.make(passwordEdit, "Введите логин и пароль", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (UserDataValidator.passwordCheck(password) != null) {
            Snackbar.make(passwordEdit, UserDataValidator.passwordCheck(password), Snackbar.LENGTH_LONG);
            return;
        }

        //Отправака запроса
        new SignInTask(login, password).execute();

    }


    public void onClick_reg(View view) {
        Intent reg = new Intent(this, OrgRegistrationActivity.class);
        startActivity(reg);
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

                Intent signIn = new Intent(StartActivity.this, ProfileActivity.class);
                signIn.putExtra("user", user); //передаем сериализованный объект
                signIn.putExtra("company",company);
                startActivity(signIn);
                finish();
            } else {
                EditText passwordEdit = findViewById(R.id.auth_edit_password);
                Snackbar.make(passwordEdit, "Неверный логин или пароль", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}