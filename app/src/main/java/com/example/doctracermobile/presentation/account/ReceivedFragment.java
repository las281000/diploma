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
import com.example.doctracermobile.entity.Task;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.TaskClient;
import com.example.doctracermobile.utile.AssignedTasksListAdapter;

import java.util.List;

public class ReceivedFragment extends Fragment {

    RecyclerView tasksList;

    public ReceivedFragment() {
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
        return inflater.inflate(R.layout.fragment_received, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tasksList = getView().findViewById(R.id.assigned_rv);
        String login = Preferences.getLogin(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
        String password = Preferences.getPassword(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));

        new GetAssignedTask(login, password).execute();
    }

    private class GetAssignedTask extends AsyncTask<Void, Void, List<Task>> {
        private final String email;
        private final String password;

        private GetAssignedTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected List<Task> doInBackground(Void... voids) { //тут возвращаем уже список объектов Employee
            return TaskClient.getAssignedTasks(email, password);
        }

        @Override
        protected void onPostExecute(List<Task> assignedTasks) {
            super.onPostExecute(assignedTasks);
            if (assignedTasks != null){
                tasksList.setLayoutManager(new LinearLayoutManager(getContext()));
                AssignedTasksListAdapter adapter = new AssignedTasksListAdapter(assignedTasks, email, password);
                adapter.notifyDataSetChanged();
                tasksList.setAdapter(adapter);
            } else {
                tasksList.setVisibility(View.GONE);
            }
        }
    }
}