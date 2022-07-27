package com.example.doctracermobile.presentation.account;

import static com.example.doctracermobile.utile.Constants.APP_PREFERENCES;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Employee;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.utile.EmployeesListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StaffFragment extends Fragment {
    RecyclerView employeesList;

    //слушатель кнопы добавления раба
    private final View.OnClickListener fabListener = (v) -> {
        Bundle bundle = new Bundle();
        bundle.putBoolean("newEmployee", true);
        ((AccountActivity) getActivity())
                .getNavController().navigate(R.id.action_nav_staff_to_userRegistrationFragment2,bundle);
    };

    public StaffFragment() {
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
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = getView().findViewById(R.id.staff_reg_fab);
        fab.setOnClickListener(fabListener);

        employeesList = getView().findViewById(R.id.staff_recycler_employees);

        String login = Preferences.getLogin(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
        String password = Preferences.getPassword(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
        new GetEmployeesTask(login,password).execute();
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
            return UserClient.getEmployees(email, password);
        }

        @Override
        protected void onPostExecute(ArrayList<Employee> employees) {
            super.onPostExecute(employees);
            if (employees != null){
                employeesList.setLayoutManager(new LinearLayoutManager(getContext()));
                employeesList.setAdapter(new EmployeesListAdapter(employees));
            } else {
                employeesList.setVisibility(View.GONE);
            }
        }
    }

}