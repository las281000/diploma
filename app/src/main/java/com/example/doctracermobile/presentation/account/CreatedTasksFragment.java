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
import com.example.doctracermobile.utile.CreatedTasksListAdapter;
import com.example.doctracermobile.utile.TaskStatus;

import java.util.List;

public class CreatedTasksFragment extends Fragment {
    RecyclerView tasksList;
    String status;

    public CreatedTasksFragment() {
        // Required empty public constructor
    }

    public static CreatedTasksFragment newInstance(int position) {
        CreatedTasksFragment fragment = new CreatedTasksFragment();
        Bundle args = new Bundle();
        if (position == 0) {
            args.putString("STATUS", TaskStatus.CREATED.toString());
        } else {
            args.putString("STATUS", TaskStatus.CLOSED.toString());
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           status = getArguments().getString("STATUS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_created_tasks, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tasksList = getView().findViewById(R.id.task_created_rv);

        String login = Preferences.getLogin(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));
        String password = Preferences.getPassword(getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));

        new GetCreatedTask(login, password).execute();
    }

    private class GetCreatedTask extends AsyncTask<Void, Void, List<Task>> {
        private final String email;
        private final String password;

        private GetCreatedTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected List<Task> doInBackground(Void... voids) { //тут возвращаем уже список объектов Employee
            if (status.equals("ОТКРЫТО")){
                return TaskClient.getCreatedTasks(email, password);
            }
            return TaskClient.getClosedTasks(email, password);
        }

        @Override
        protected void onPostExecute(List<Task> createdTasks) {
            super.onPostExecute(createdTasks);
            if (createdTasks != null) {
                tasksList.setLayoutManager(new LinearLayoutManager(getContext()));
                tasksList.setAdapter(new CreatedTasksListAdapter(createdTasks));
            } else {
                tasksList.setVisibility(View.GONE);
            }
        }
    }
}