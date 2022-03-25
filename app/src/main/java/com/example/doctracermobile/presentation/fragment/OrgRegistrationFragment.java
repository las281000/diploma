package com.example.doctracermobile.presentation.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Company;
import com.example.doctracermobile.usecase.CompanyDataValidator;
import com.google.android.material.snackbar.Snackbar;

public class OrgRegistrationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Spinner spinType;
    private EditText editType;
    private String selected = "";
    private Button buttNext;

    public OrgRegistrationFragment() {
        // Required empty public constructor
    }

    public static OrgRegistrationFragment newInstance(String param1, String param2) {
        OrgRegistrationFragment fragment = new OrgRegistrationFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_org_registration, container, false);
    }

    //Регистрация новой организации
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Дополнительное поле изначально спрятано
        editType = getView().findViewById(R.id.reg_org_edit_type);
        editType.setVisibility(View.INVISIBLE);

        spinType = getView().findViewById(R.id.reg_org_spin_type);
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] types = getResources().getStringArray(R.array.com_types);
                selected = types[position];
                
                //Если выбран вариант "Другое"
                if (selected.contains("Другое")) { 
                    editType.setVisibility(View.VISIBLE);
                } else {
                    //если передумали и выбрали вариант, то надо спрятать доп.поле
                    editType.setVisibility(View.INVISIBLE);
                }
            }

            //Еслиничего не выбрали, то по дефолту первый вариант из списка
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = getResources().getStringArray(R.array.com_types)[0].toString();
            }
        });

        //Логика кнопки (тут проверки полей и переход к следующему этапу регистрации)
        buttNext = (Button) getView().findViewById(R.id.reg_org_but_next);
        buttNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //забираем тип организации
                String type = spinType.getSelectedItem().toString();
                if (type.equals("Другое")) {
                    type = editType.getText().toString();
                }

                //собираем данные организации из полей
                String name = ((EditText) getView()
                        .findViewById(R.id.reg_org_edit_name))
                        .getText()
                        .toString();

                String country = ((EditText) getView()
                        .findViewById(R.id.reg_org_edit_country))
                        .getText()
                        .toString();

                String address = ((EditText) getView()
                        .findViewById(R.id.reg_org_edit_address))
                        .getText()
                        .toString();

                String inn = ((EditText) getView()
                        .findViewById(R.id.reg_org_edit_inn))
                        .getText()
                        .toString();

                String ogrn = ((EditText) getView()
                        .findViewById(R.id.reg_org_edit_ogrn))
                        .getText()
                        .toString();

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

                //Если данные организации корректны, переходим к фр. UserRegistrationFragment
                Company company = new Company(name, type, country, address, inn, ogrn);
                UserRegistrationFragment fragment = new UserRegistrationFragment();
                final Bundle args = new Bundle();
                args.putSerializable("company", company);//передаем компанию следующему фрагменту
                fragment.setArguments(args);

                //TODO переход на UserRegistrationFragment
            }
        });

    }
}