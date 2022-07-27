package com.example.doctracermobile.utile;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Task;
import com.example.doctracermobile.repository.TaskClient;
import com.google.android.material.snackbar.Snackbar;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AssignedTasksListAdapter extends RecyclerView.Adapter<AssignedTasksListAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private String login;
    private String password;
    private RecyclerView rv;


    public AssignedTasksListAdapter(List<Task> taskList, String login, String password) {
        this.taskList = taskList;
        this.login = login;
        this.password = password;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        rv = recyclerView;
    }

    //Создает View для кадого пункта списка
    @Override
    public AssignedTasksListAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opened_task_card_layout, parent, false);
        return new TaskViewHolder(view);
    }

    //Заполнение View данными объекта из списка
    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        final Task task = taskList.get(position);

        holder.name.setText(task.getName());
        holder.idea.setText(task.getIdea());

        String creationDate = DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .withZone(ZoneId.systemDefault()).format(task.getCreationDate());
        holder.creation.setText(creationDate);

        String deadline = DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .withZone(ZoneId.systemDefault()).format(task.getDeadline());
        holder.deadline.setText(deadline);

        holder.responsible.setText(task.getResponsible().getInitials());
        holder.creator.setText(task.getCreator().getInitials());
        holder.priority.setText(task.getPriority().toString());
        holder.id = task.getId();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView idea;
        private TextView creation;
        private TextView deadline;
        private TextView responsible;
        private TextView creator;
        private TextView priority;
        private Button closeTaskBtn;
        private Long id;

        private CardView cardView;

        //обработчик кнопки закрытия задачи
        private final View.OnClickListener closeBtnListener = (v) -> {
            new CloseTask(id).execute();
        };

        public TaskViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.opened_card_task_name);
            idea = itemView.findViewById(R.id.opened_card_task_idea);
            creation = itemView.findViewById(R.id.opened_card_task_creation);
            deadline = itemView.findViewById(R.id.opened_card_task_deadline);
            responsible = itemView.findViewById(R.id.opened_card_task_responsible);
            creator = itemView.findViewById(R.id.opened_card_task_creator);
            priority = itemView.findViewById(R.id.opened_card_task_priority);
            closeTaskBtn = itemView.findViewById(R.id.task_card_close);
            closeTaskBtn.setOnClickListener(closeBtnListener);

            cardView = itemView.findViewById(R.id.opened_task_cardView);
        }

    }

    private class CloseTask extends AsyncTask<Void, Void, Boolean> {
        private final Long id;

        private CloseTask(Long id) {
            this.id = id;
        }

        @Override
        protected Boolean doInBackground(Void... voids) { //тут возвращаем уже список объектов Employee
            return TaskClient.close(id, login, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                //если задача закрыта, удаляем из списка полученных задач. Адаптер сам обновится
                taskList.removeIf((Task t) -> t.getId() == id);
                Log.e("Changed_list", taskList.toString());
                notifyDataSetChanged();
            } else {
                Snackbar.make(rv, "Не удалось закрыть задачу", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}

