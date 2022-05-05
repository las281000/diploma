package com.example.doctracermobile.presentation.start;

import android.app.DatePickerDialog;
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
import com.example.doctracermobile.usecase.DataValidator;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProjRegistrationFragment extends Fragment {

    private Button buttNext;
    private EditText editStartDate;
    private EditText editEndDate;
    private Calendar calendar;

    //Получает данные проекта из формы
    private Project getProjectFromForm() {

        String name = ((EditText) getView()
                .findViewById(R.id.reg_project_edit_name))
                .getText()
                .toString();
        String description = ((EditText) getView()
                .findViewById(R.id.reg_project_edit_description))
                .getText()
                .toString();
        String startDate = ((EditText) getView()
                .findViewById(R.id.reg_project_date_start))
                .getText()
                .toString();

        String endDate = ((EditText) getView()
                .findViewById(R.id.reg_project_date_end))
                .getText()
                .toString();

        return new Project(name,description, startDate, endDate);
    }

    //слушатель окна календаря
    private final DatePickerDialog.OnDateSetListener dateListener = (v, year, month, day) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    };

    //слушатель нажатия на поле ввода даты
    private final View.OnClickListener startDateButtListener = (v) -> {
        new DatePickerDialog(this.getContext(), dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        ((EditText) v).setText(formatter.format(calendar.getTime()));
    };

    private final View.OnClickListener nextButtListener = (v) -> {
        Project project = getProjectFromForm();

        //Проверка пустых полей
        if (project.emptyFieldsCheck()) {
            Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Чтоб с заглавной буквы
        if (!DataValidator.capitalLetterCheck(project.getName())){
            Snackbar.make(v, "Укажите название проекта с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Если данные проекта корректны, переходим к фр. UserRegistrationFragment
        UserRegistrationFragment fragment = new UserRegistrationFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);//передаем компанию следующему фрагменту

        ((StartActivity) getActivity())
                .getNavController()
                .navigate(R.id.action_orgRegistrationFragment_to_userRegistrationFragment,bundle);

    };

    public ProjRegistrationFragment() {
        // Required empty public constructor
    }

    public static ProjRegistrationFragment newInstance(String param1, String param2) {
        ProjRegistrationFragment fragment = new ProjRegistrationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_registration, container, false);
    }

    //Регистрация новой организации
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getActivity().getResources().getString(R.string.proj_reg_fragment_title);
        ((StartActivity)getActivity()).setTitle(title);

        calendar = Calendar.getInstance();

        //Логика кнопки (тут проверки полей и переход к следующему этапу регистрации)
        buttNext = (Button) getView().findViewById(R.id.reg_project_but_next);
        buttNext.setOnClickListener(nextButtListener);

        editStartDate = (EditText) getView().findViewById(R.id.reg_project_date_start);
        editEndDate = (EditText) getView().findViewById(R.id.reg_project_date_end);

        editStartDate.setOnClickListener(startDateButtListener);
        editEndDate.setOnClickListener(startDateButtListener);

    }
}