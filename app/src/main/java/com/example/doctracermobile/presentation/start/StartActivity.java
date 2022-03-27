package com.example.doctracermobile.presentation.start;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.doctracermobile.R;

public class StartActivity extends AppCompatActivity {
    private NavController navController;

    public NavController getNavController(){
        return navController;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_start);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}