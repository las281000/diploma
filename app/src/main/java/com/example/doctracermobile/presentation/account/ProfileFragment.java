package com.example.doctracermobile.presentation.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.doctracermobile.R;
import com.example.doctracermobile.entity.Project;
import com.example.doctracermobile.entity.User;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ProfileFragment extends Fragment {

    private User user;
    private Project project;
    private Button accessButt;

    //Обработчик кнопки обновления перс.данных (перекинет на страницу доступа)
    private View.OnClickListener accessButtListener = (v) -> {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        ((AccountActivity) getActivity())
                .getNavController()
                .navigate(R.id.action_nav_profile_to_nav_access_update, bundle);
    };

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private int getDaysLeft() {
        int daysLeft = 0;

        return daysLeft;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //установка слушателя на кнопку
        accessButt = getView().findViewById(R.id.profile_but_change);
        accessButt.setOnClickListener(accessButtListener);

        if (getArguments() != null) { //сработает если были изменены данные
            user = (User) getArguments().getSerializable("newUserData");
            Log.e("PROFILE", "Новые данные получены фрагментом");
        } else { // сработает если только вошли в акк
            user = (User) getActivity().getIntent().getSerializableExtra("user");
        }

        //размазывание информации по экрану
        ((TextView) getView()
                .findViewById(R.id.profile_text_surname))
                .setText(user.getSurname());

        ((TextView) getView()
                .findViewById(R.id.profile_text_name))
                .setText(user.getName());

        ((TextView) getView()
                .findViewById(R.id.profile_text_patronum))
                .setText(user.getPatronum());

        ((TextView) getView()
                .findViewById(R.id.profile_text_position))
                .setText(user.getPosition());

        ((TextView) getView()
                .findViewById(R.id.profile_text_phone))
                .setText(user.getPhoneNumber());

        ((TextView) getView()
                .findViewById(R.id.profile_text_email))
                .setText(user.getEmail());

        project = (Project) getActivity().getIntent().getSerializableExtra("project");

        ((TextView) getView()
                .findViewById(R.id.profile_proj_name))
                .setText(project.getName());

        ((TextView) getView()
                .findViewById(R.id.profile_proj_description))
                .setText(project.getDescription());

        String startDate = DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .withZone(ZoneId.systemDefault()).format(project.getStartDate());

        String endDate = DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .withZone(ZoneId.systemDefault()).format(project.getEndDate());

        ((TextView) getView()
                .findViewById(R.id.profile_proj_text_start))
                .setText(startDate);

        ((TextView) getView()
                .findViewById(R.id.profile_proj_text_end))
                .setText(endDate);

        long daysLeft = Duration.between(Instant.now(), project.getEndDate()).toDays();

        TextView labelDaysLeft = getView().findViewById(R.id.profile_proj_days_left_label);
        TextView textDaysLeft =  getView().findViewById(R.id.profile_proj_days_left);

        if (daysLeft > 0) {
            labelDaysLeft.setTextColor(ContextCompat.getColor(this.getContext(), R.color.green_additional));
            textDaysLeft.setText(String.valueOf(daysLeft));
            textDaysLeft.setTextColor(ContextCompat.getColor(this.getContext(), R.color.green_additional));
        } else{
            labelDaysLeft.setTextColor(ContextCompat.getColor(this.getContext(), R.color.red));
            labelDaysLeft.setText("Просрочено дней");

            textDaysLeft.setText(String.valueOf(daysLeft*(-1)));
            textDaysLeft.setTextColor(ContextCompat.getColor(this.getContext(), R.color.red));
        }

    }
}