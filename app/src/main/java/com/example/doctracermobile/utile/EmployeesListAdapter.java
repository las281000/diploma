package com.example.doctracermobile.utile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Employee;

import java.util.List;


public class EmployeesListAdapter extends RecyclerView.Adapter<EmployeesListAdapter.EmployeeViewHolder> {

    private List<Employee> employeesList;

    public EmployeesListAdapter(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    //Создает View для кадого пункта списка
    @Override
    public EmployeesListAdapter.EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_card_layout, parent, false);
        return new EmployeeViewHolder(view);
    }

    //Заполнение View данными объекта из списка
    @Override
    public void onBindViewHolder(EmployeesListAdapter.EmployeeViewHolder holder, final int position) {
        final Employee employee = employeesList.get(position);
        holder.fullName.setText(employee.getFullName());
        holder.position.setText(employee.getPosition());
        holder.phone.setText(employee.getPhone());
        holder.email.setText(employee.getEmail());
    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView fullName;
        private TextView position;
        private TextView phone;
        private TextView email;

        private CardView cardView;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.card_text_name);
            position = itemView.findViewById(R.id.card_text_position);
            phone = itemView.findViewById(R.id.card_text_phone);
            email = itemView.findViewById(R.id.card_text_email);
            cardView = itemView.findViewById(R.id.staff_cardView);
        }
    }
}
