package com.example.doctracermobile.presentation.account;

import static com.example.doctracermobile.utile.Constants.APP_PREFERENCES;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Employee;
import com.example.doctracermobile.entity.Task;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.TaskClient;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.utile.TaskPriority;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskCreationFragment extends Fragment {

    private EditText creationEdit;
    private EditText deadlineEdit;
    private Button createBtn;
    private Spinner responsibleSpinner;
    private Spinner prioritySpinner;
    private EditText nameEdit;
    private EditText ideaEdit;

    private Instant creationDate;
    private Instant deadline;
    private Calendar calendar;

    private List<String> employeeList;//для спинера
    private ArrayList<Employee> employees; //тут будут сами сотрудники со всеми их данными

    private final View.OnClickListener createBtnListener = (v) -> {
        Log.e("DATES", creationDate.toString());
        Log.e("DATES", deadline.toString());
        Task task = getTaskFromForm();
        if (task.emptyFieldCheck()){
            new CreateNewTask(task).execute();
        } else{
            Snackbar.make(createBtn,"Необходимо заполнить все поля!", Snackbar.LENGTH_LONG).show();
        }

    };

    private Task getTaskFromForm() {
        String name = nameEdit.getText().toString();
        String idea = ideaEdit.getText().toString();

        responsibleSpinner.getSelectedItemPosition();
        return new Task(name,
                idea,
                creationDate,
                deadline,
                employees.get(responsibleSpinner.getSelectedItemPosition()),
                (TaskPriority) prioritySpinner.getSelectedItem());
    }

    //создает диалог для выбора дедлайна
    private void createDatePickerDialog(View v) {
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this.getContext(),
                deadlineDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    //вернет формат, котрым форматирует дату
    private SimpleDateFormat setDatePickerDialog(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //форматирование даты
        return format;
    }

    //слушатель для startDatePicker
    private DatePickerDialog.OnDateSetListener deadlineDialogListener = (v, year, month, day) -> {
        SimpleDateFormat format = setDatePickerDialog(year, month, day);
        deadlineEdit.setText(format.format(calendar.getTime()));
        deadline = calendar.getTime().toInstant();
        Log.e("START_DATE", deadline.toString());
    };

    //слушатель поля ввода deadline
    private View.OnClickListener deadlineEditListener = (v) -> {
        createDatePickerDialog(v);
    };

    public TaskCreationFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_task_creation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEdit = getView().findViewById(R.id.create_task_edit_name);
        ideaEdit = getView().findViewById(R.id.create_task_edit_idea);

        creationEdit = getView().findViewById(R.id.create_task_edit_creation);
        creationDate = Calendar.getInstance().toInstant();

        creationEdit.setText(DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .withZone(ZoneId.systemDefault()).format(creationDate)); //дата создания задачи - сегодня

        deadlineEdit = getView().findViewById(R.id.create_task_edit_deadline);
        deadlineEdit.setOnClickListener(deadlineEditListener);

        createBtn = getView().findViewById(R.id.create_task_btn_create);
        createBtn.setOnClickListener(createBtnListener);

        responsibleSpinner = getView().findViewById(R.id.create_task_spin_resp);
        String login = Preferences.getLogin(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
        String password = Preferences.getPassword(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
        new GetEmployeesTask(login, password).execute();

        prioritySpinner = getView().findViewById(R.id.create_task_spin_priority);
        ArrayAdapter <TaskPriority> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                TaskPriority.values());
        prioritySpinner.setAdapter(adapter);
    }

    private class GetEmployeesTask extends AsyncTask<Void, Void, ArrayList<Employee>> {
        private final String email;
        private final String password;

        private GetEmployeesTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected ArrayList<Employee> doInBackground(Void... voids) { //тут возвращаем уже список объектов Employee
            employees = UserClient.getEmployees(email, password);
            return employees;
        }

        @Override
        protected void onPostExecute(ArrayList<Employee> employees) {
            super.onPostExecute(employees);
            if (employees.size() != 0) {
                //составляем список из ФИО сотрудников
                employeeList = new ArrayList<String>();
                for (Employee empl : employees) {
                    employeeList.add(empl.getFullName());
                }

                //адаптер спинера
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        employeeList);
                responsibleSpinner.setAdapter(adapter);
            } else {
                nameEdit.setEnabled(false);
                ideaEdit.setEnabled(false);
                creationEdit.setEnabled(false);
                deadlineEdit.setEnabled(false);
                responsibleSpinner.setEnabled(false);
                createBtn.setEnabled(false);

                getView().findViewById(R.id.create_task_text_warning).setVisibility(View.VISIBLE);
            }
        }
    }

    private class CreateNewTask extends AsyncTask<Void, Void, Boolean> {
        private final Task task;

        private CreateNewTask(Task task) {

            this.task = task;
        }

        @Override
        protected Boolean doInBackground(Void... voids) { //тут возвращаем уже список объектов Employee
            String login = Preferences.getLogin(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
            String password = Preferences.getPassword(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));

            return TaskClient.create(task, login, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                ((AccountActivity)getActivity()).getNavController().navigate(R.id.action_taskCreationFragment_to_nav_sent);
            } else {
                Snackbar.make(createBtn,"Не удалось создать задачу", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}