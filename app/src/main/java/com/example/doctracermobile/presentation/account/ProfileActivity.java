package com.example.doctracermobile.presentation.account;

import static com.example.doctracermobile.util.Constants.APP_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.doctracermobile.R;
import com.example.doctracermobile.databinding.ActivityProfileBinding;
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.presentation.start.StartActivity;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.request.JointUserCompany;
import com.example.doctracermobile.request.UserForRequest;
import com.example.doctracermobile.usecase.UserDataValidator;
import com.example.doctracermobile.util.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration; //конфиг меню в шторке
    private ActivityProfileBinding binding; //для связи с разметкой activity_lk_profile.xml

    private NavController navController;
    private User user;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater()); //получение разметки
        setContentView(binding.getRoot()); //получаем корневой элемент разметки

        //делаем из бара ЭкшнБар (для отображения заголовков)
        setSupportActionBar(binding.appBarLkProfile.toolbar);

        DrawerLayout drawer = binding.drawerLayout;// достаем разметку шторки
        NavigationView navigationView = binding.navView; // достаем меню для навигации

        mAppBarConfiguration = new AppBarConfiguration.Builder( //настраиваем варианты в меню из разметки
                R.id.nav_profile,
                R.id.nav_staff,
                R.id.nav_sent,
                R.id.nav_received,
                R.id.nav_settings,
                R.id.nav_info)
                .setDrawerLayout(drawer)  //указываем на разметку меню
                .build();  //собираем всю меню из пунктов и разметки

        //класс управления навигацией (управление фрагементами)
        //контроллер ассоциирован с хост-фрагментом
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_lk_profile);

        /*установка компонента меню с обработчиком (сам выполняет навигацию к фрагменту с тем же id,
        что и пункт меню)*/
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        //ассоциируем View для меню и контроллер
        NavigationUI.setupWithNavController(navigationView, navController);


        //////////////////////////////////////////////////////////////////////////////////////
        user = (User) this.getIntent().getSerializableExtra("user");

        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.bar_profile_name))
                .setText(String.format("%s %s %s",
                        user.getSurname(),
                        user.getName(),
                        user.getPatronum()));

        ((TextView) navigationView
                .getHeaderView(0)
                .findViewById(R.id.bar_profile_position))
                .setText(user.getPosition());

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_lk_profile);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    ////////////////////////////// КНОПЫ ФРАГМЕНТОВ \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    //Переход на фрагмент доступа к изменению данных (Кнопка в ProfileFragment)
    public void onClick_changeData(View view) {
        navController.navigate(R.id.action_nav_profile_to_nav_access_edit);
    }

    //Проверка действующего пароля и переход на фрагмент изменения данных
    public void onClick_getAccess(View view) {
        EditText passwordEdit = (EditText) findViewById(R.id.change_edit_check);
        String password = passwordEdit.getText().toString();
        String controlPass = Preferences.getPassword(getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE));

        Log.e("GET_ACCESS", controlPass);
        if (!password.equals(controlPass)) {
            Log.e("ACCESS_TO_EDITING", "Пароль не верный.");
            Snackbar.make(passwordEdit, "Неверный пароль!", Snackbar.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            navController.navigate(R.id.action_nav_access_edit_to_nav_edit_userData, bundle);
        }

    }

    public void onClick_exit(View view){
       preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
       if (!Preferences.removePassword(preferences)){
           Snackbar.make((Button) findViewById(R.id.set_butt_exit),
                   "Не удалось удалить данные с устройства.",
                   Snackbar.LENGTH_LONG);
       } else{
           startActivity(new Intent(ProfileActivity.this, StartActivity.class));
           finish();
       }

    }

    //Изменение данных пользователя
    public void onClick_update(View view) {

        EditText editName = (EditText) findViewById(R.id.change_edit_name);

        String name = ((EditText) findViewById(R.id.change_edit_name)).getText().toString();
        String surname = ((EditText) findViewById(R.id.change_edit_surname)).getText().toString();
        String patronum = ((EditText) findViewById(R.id.change_edit_patronum)).getText().toString();
        String position = ((EditText) findViewById(R.id.change_edit_position)).getText().toString();
        String phone = ((EditText) findViewById(R.id.change_edit_phone)).getText().toString();
        String email = ((EditText) findViewById(R.id.change_edit_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.change_edit_pass)).getText().toString(); // строка из первого поля
        String password_d = ((EditText) findViewById(R.id.change_edit_pass_d)).getText().toString(); // строка из второго поля

        Log.e("NEW PASSWORD", password);

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

        //Проверка заглавных букв
        if (!UserDataValidator.capitalLetterCheck(name) ||
                !UserDataValidator.capitalLetterCheck(surname) ||
                !UserDataValidator.capitalLetterCheck(patronum) ||
                !UserDataValidator.capitalLetterCheck(position)) {
            Snackbar.make(editName, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Проверка формы номера телефона
        String validatorReply;
        validatorReply = UserDataValidator.phoneCheck(phone);
        if (validatorReply != null) { //если к строке есть замечания
            Snackbar.make(editName, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }

        //Валидация пароля
        validatorReply = UserDataValidator.passwordCheck(password);
        if (validatorReply != null) { //если к строке есть замечания
            Snackbar.make(editName, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }

        //если введены разные строки
        if (!password.equals(password_d)) {
            Snackbar.make(editName, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
        } else { //если введены одинаковые строки
            phone = phone.replaceAll(" ", "").replaceAll("-", "");
            UserForRequest updatedUser = new UserForRequest(name, surname, patronum, position, phone, email, password);

            new UpdateTask(user.getEmail(), user.getPass(), updatedUser).execute();
        }
    }

    private class UpdateTask extends AsyncTask<Void, Void, JointUserCompany> {

        private final String oldEmail; //текущий логин
        private final String oldPassword; //текущий пароль
        private UserForRequest newUserData; //новын данные

        private UpdateTask(String email, String password, UserForRequest newUserData) {
            this.oldEmail = email;
            this.oldPassword = password;
            this.newUserData = newUserData;
        }

        @Override
        protected JointUserCompany doInBackground(Void... voids) {
            JointUserCompany updatedUserCompany = UserClient.update(oldEmail, oldPassword, newUserData);
            System.out.println(updatedUserCompany);
            return updatedUserCompany;
        }

        @Override
        protected void onPostExecute(JointUserCompany updatedUserCompany) {
            super.onPostExecute(updatedUserCompany);

            if (updatedUserCompany != null) {

                Log.e("ASYNC_TASK", "Обновленные данные получены.");
                user = new User(updatedUserCompany.getName(),
                        updatedUserCompany.getSurname(),
                        updatedUserCompany.getPatronum(),
                        updatedUserCompany.getPosition(),
                        updatedUserCompany.getPhoneNumber(),
                        updatedUserCompany.getEmail(),
                        newUserData.getPassword());

                Log.e("NEW DATA", user.toString());

                //Если пароль изменился, то выход из акка
                if (!user.getPass().equals(Preferences.getPassword(getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)))) {
                    SharedPreferences preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
                    Preferences.removePassword(preferences);
                    startActivity(new Intent(ProfileActivity.this, StartActivity.class));
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("newUserData", user);
                    navController.navigate(R.id.action_nav_edit_userData_to_nav_profile, bundle);
                }

            } else {
                EditText passwordEdit = findViewById(R.id.auth_edit_password);
                Snackbar.make(passwordEdit, "Изменение данных не удалось!", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}