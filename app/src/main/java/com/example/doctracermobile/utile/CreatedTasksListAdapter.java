package com.example.doctracermobile.utile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Task;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class CreatedTasksListAdapter extends RecyclerView.Adapter<CreatedTasksListAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public CreatedTasksListAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    //Создает View для кадого пункта списка
    @Override
    public CreatedTasksListAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.created_task_card_layout, parent, false);
        return new TaskViewHolder(view);
    }

    //Заполнение View данными объекта из списка
    @Override
    public void onBindViewHolder(CreatedTasksListAdapter.TaskViewHolder holder, final int position) {
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

        holder.status.setText(task.getStatus().toString());
        if (task.getStatus().equals(TaskStatus.CREATED)){
            holder.status.setTextColor(holder.cardView.getContext().getResources().getColor(R.color.green_additional));
        } else {
            holder.status.setTextColor(holder.cardView.getContext().getResources().getColor(R.color.red));
        }
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
        private TextView status;

        private CardView cardView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.created_card_task_name);
            idea = itemView.findViewById(R.id.created_card_task_idea);
            creation = itemView.findViewById(R.id.created_card_task_creation);
            deadline = itemView.findViewById(R.id.created_card_task_deadline);
            responsible = itemView.findViewById(R.id.created_card_task_responsible);
            creator = itemView.findViewById(R.id.created_card_task_creator);
            priority = itemView.findViewById(R.id.created_card_task_priority);
            status = itemView.findViewById(R.id.created_card_task_status);

            cardView = itemView.findViewById(R.id.created_task_cardView);
        }
    }
}
