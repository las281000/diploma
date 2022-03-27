package com.example.doctracermobile.presentation.account;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.doctracermobile.R;
import com.example.doctracermobile.databinding.ActivityAccountBinding;
import com.example.doctracermobile.entity.User;
import com.google.android.material.navigation.NavigationView;

public class AccountActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration; //конфиг меню в шторке
    private ActivityAccountBinding binding; //для связи с разметкой activity_lk_profile.xml

    private NavController navController;
    private User user;


    public NavController getNavController() {
        return navController;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAccountBinding.inflate(getLayoutInflater()); //получение разметки
        setContentView(binding.getRoot()); //получаем корневой элемент разметки

        //делаем из бара ЭкшнБар (для отображения заголовков)
        setSupportActionBar(binding.appBarAccount.toolbar);

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

        //класс управления навигацией (управление фрагементами), ассоциирован с хост-фрагментом
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_lk_profile);

        /*установка меню с обработчиком (сам выполняет навигацию к фрагменту с тем же id, что и пункт меню)*/
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        //ассоциируем View для меню и контроллер
        NavigationUI.setupWithNavController(navigationView, navController);


        user = (User) getIntent().getSerializableExtra("user");
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
}

