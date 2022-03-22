package com.example.doctracermobile.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.doctracermobile.R;

public class RecoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recovery);
    }

    public void onClick_recover_send(View view){
        //Тут будет что то для восстановления пароля
    }
}