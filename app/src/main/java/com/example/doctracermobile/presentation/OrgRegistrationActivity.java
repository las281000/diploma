package com.example.doctracermobile.presentation;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Company;
import com.example.doctracermobile.usecase.CompanyDataValidator;
import com.google.android.material.snackbar.Snackbar;

public class OrgRegistrationActivity extends AppCompatActivity {

    private Spinner spinType;
    private EditText editType;
    private String selected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_registration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        editType = findViewById(R.id.reg_com_edit_type);
        editType.setVisibility(View.INVISIBLE);

        spinType = findViewById(R.id.reg_com_spin_type);

        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] types = getResources().getStringArray(R.array.com_types);

                selected = types[position];
                System.out.println(selected);

                if (selected.contains("Другое")) {
                    System.out.println("OK");
                    editType.setVisibility(View.VISIBLE);
                } else {
                    //TODO исчение поля
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClick_next_reg(View view) {

        String type = spinType.getSelectedItem().toString();
        if (type.equals("Другое")) {
            type = editType.getText().toString();
        }
        String name = ((EditText) findViewById(R.id.reg_com_edit_name)).getText().toString();
        String country = ((EditText) findViewById(R.id.reg_com_edit_country)).getText().toString();
        String address = ((EditText) findViewById(R.id.reg_com_edit_address)).getText().toString();
        String inn = ((EditText) findViewById(R.id.reg_com_edit_inn)).getText().toString();
        String ogrn = ((EditText) findViewById(R.id.reg_com_edit_ogrn)).getText().toString();

        //Проверка пустых полей
        if (name.equals("") ||
                country.equals("") ||
                address.equals("") ||
                inn.equals("") ||
                ogrn.equals("") ||
                (spinType.getSelectedItem().toString().equals("Другое") && (type.equals("")))) {
            Snackbar.make(editType, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Чтоб с заглавной буквы
        if (!CompanyDataValidator.capitalLetterCheck(name) ||
                !CompanyDataValidator.capitalLetterCheck(country) ||
                !CompanyDataValidator.capitalLetterCheck(address)) {
            Snackbar.make(editType, "Название, страна и адрес указываются с заглавной буквы.", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Для проврки ИНН надо знать, ИП или нет
        int lStatus;
        if (type.equals("ИП")) {
            lStatus = CompanyDataValidator.PE;
        } else {
            lStatus = CompanyDataValidator.LE;
        }

        //Проверка ИНН
        String check = CompanyDataValidator.innCheck(inn, type);
        if (check != null) {
            Snackbar.make(editType, check, Snackbar.LENGTH_LONG).show();
            return;
        }

        check = CompanyDataValidator.ogrnCheck(ogrn);
        if (check != null) {
            Snackbar.make(editType, check, Snackbar.LENGTH_LONG).show();
            return;
        }


        //Если нас все еще не слили, можно и дальше пойти регаться
        Company company = new Company(name, type, country, address, inn, ogrn);
        Intent head_reg = new Intent(this, HeadRegistrationActivity.class);
        head_reg.putExtra("company", company);
        startActivity(head_reg);
    }
}