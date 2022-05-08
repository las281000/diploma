package com.example.doctracermobile.presentation.start;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

public class ProjRegistrationFragment extends Fragment {

    private Button buttNext;

    private EditText editStartDate;
    private EditText editEndDate;
    private Calendar calendar;
    private Instant start;
    private Instant end;

    //создает диалог с нужным слушателем
    private void createDatePickerDialog(View v) {
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this.getContext(),
                startDateDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        if (v.getId() != R.id.reg_project_date_start) {
            dialog.setOnDateSetListener(endDateDialogListener);
        }
        dialog.show();
    }

    //вернет формат, котрым форматирует дату
    private SimpleDateFormat setDatePickerDialog(int year, int month, int day){
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //форматирование даты
        return format;
    };

    //слушатель для startDatePicker
    private DatePickerDialog.OnDateSetListener startDateDialogListener = (v, year, month, day) -> {
        SimpleDateFormat format = setDatePickerDialog(year, month, day);
        editStartDate.setText(format.format(calendar.getTime()));
        start = calendar.getTime().toInstant();
        Log.e("START_DATE", start.toString());
    };

    //слушатель для endDatePicker
    private DatePickerDialog.OnDateSetListener endDateDialogListener = (v, year, month, day) -> {
        SimpleDateFormat format = setDatePickerDialog(year, month, day);
        editEndDate.setText(format.format(calendar.getTime()));
        end = calendar.getTime().toInstant();
        Log.e("END_DATE", end.toString());
    };

    //слушатель поля ввода startDate
    private View.OnClickListener dateEditListener = (v) -> {
        createDatePickerDialog(v);
    };

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

        return new Project(name, description, start, end);
    }

    private final View.OnClickListener nextButtListener = (v) -> {
        Project project = getProjectFromForm();

        //Проверка пустых полей
        if (project.emptyFieldsCheck()) {
            Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Чтоб с заглавной буквы
        if (!DataValidator.capitalLetterCheck(project.getName())) {
            Snackbar.make(v, "Укажите название проекта с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Если данные проекта корректны, переходим к фр. UserRegistrationFragment
        UserRegistrationFragment fragment = new UserRegistrationFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);//передаем компанию следующему фрагменту

        ((StartActivity) getActivity())
                .getNavController()
                .navigate(R.id.action_orgRegistrationFragment_to_userRegistrationFragment, bundle);

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
        ((StartActivity) getActivity()).setTitle(title);

        //Логика кнопки (тут проверки полей и переход к следующему этапу регистрации)
        buttNext = (Button) getView().findViewById(R.id.reg_project_but_next);
        buttNext.setOnClickListener(nextButtListener);

        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC +3")); //получение текущей даты
        calendar.clear();


        editStartDate = (EditText) getView().findViewById(R.id.reg_project_date_start);
        editStartDate.setInputType(InputType.TYPE_NULL);
        editStartDate.setOnClickListener(dateEditListener);

        editEndDate = (EditText) getView().findViewById(R.id.reg_project_date_end);
        editEndDate.setInputType(InputType.TYPE_NULL);
        editEndDate.setOnClickListener(dateEditListener);
    }
}