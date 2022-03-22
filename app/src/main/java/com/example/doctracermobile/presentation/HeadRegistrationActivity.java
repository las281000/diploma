package com.example.doctracermobile.presentation;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Company;
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.repository.CompanyClient;
import com.example.doctracermobile.usecase.UserDataValidator;
import com.google.android.material.snackbar.Snackbar;

public class HeadRegistrationActivity extends AppCompatActivity {

    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_registration);

    }

    @Override
    protected void onStart() {
        super.onStart();
        editName = findViewById(R.id.reg_head_edit_name);

        //Чтоб телефон красиво вписался
        EditText phoneEdit = findViewById(R.id.reg_head_edit_phone);
        phoneEdit.addTextChangedListener(new PhoneNumberFormattingTextWatcher("RU"));
    }

    public void onClick_reg_confirm(View view) {
        String name = ((EditText) findViewById(R.id.reg_head_edit_name)).getText().toString();
        String surname = ((EditText) findViewById(R.id.reg_head_edit_surname)).getText().toString();
        String patronum = ((EditText) findViewById(R.id.reg_head_edit_patronum)).getText().toString();
        String position = ((EditText) findViewById(R.id.reg_head_edit_position)).getText().toString();

        String phone = ((EditText) findViewById(R.id.reg_head_edit_phone)).getText().toString();
        String email = ((EditText) findViewById(R.id.reg_head_edit_email)).getText().toString();

        String password = ((EditText) findViewById(R.id.reg_head_edit_pass)).getText().toString(); // строка из первого поля
        String password_d = ((EditText) findViewById(R.id.reg_head_edit_pass_d)).getText().toString(); // строка из второго поля

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
            Snackbar.make(editName, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!UserDataValidator.capitalLetterCheck(name) ||
                !UserDataValidator.capitalLetterCheck(surname) ||
                !UserDataValidator.capitalLetterCheck(patronum) ||
                !UserDataValidator.capitalLetterCheck(position)) {
            Snackbar.make(editName, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //сюда будем класть ответы валидатора
        String validatorReply;
        validatorReply = UserDataValidator.phoneCheck(phone);
        if (validatorReply != null) { //если к строке есть замечания
            Snackbar.make(editName, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }

        validatorReply = UserDataValidator.passwordCheck(password);
        if (validatorReply != null) { //если к строке есть замечания
            Snackbar.make(editName, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }

        //если введены разные строки
        if (!password.equals(password_d)) {
            Snackbar.make(editName, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
        } else { //если введены одинаковые строки
            Company company = (Company) this.getIntent().getSerializableExtra("company");
            phone = phone.replaceAll(" ", "").replaceAll("-","" );
            User user = new User(name, surname, patronum, position, phone, email, password);

            new RegTask(user, company).execute();
        }
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
                Intent verification = new Intent(HeadRegistrationActivity.this, VerificationActivity.class);
                verification.putExtra("email", user.getEmail());
                startActivity(verification);
            } else {
                Snackbar.make(editName, "Регистрация не удалась!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
