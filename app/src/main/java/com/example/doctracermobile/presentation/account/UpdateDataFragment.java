package com.example.doctracermobile.presentation.account;

import static android.content.Context.MODE_PRIVATE;
import static com.example.doctracermobile.util.Constants.APP_PREFERENCES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.presentation.start.StartActivity;
import com.example.doctracermobile.repository.Preferences;
import com.example.doctracermobile.repository.UserClient;
import com.example.doctracermobile.request.JointUserCompany;
import com.example.doctracermobile.request.UserForRequest;
import com.example.doctracermobile.usecase.UserDataValidator;
import com.google.android.material.snackbar.Snackbar;


public class UpdateDataFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText phoneEdit;
    private User oldData; // для текущих данных
    private User newData; // для новых данных
    private Button updateButton;

    public UpdateDataFragment() {
        // Required empty public constructor
    }

    private User getUserFromForm() {
        View rootView = getView();
        String name = ((EditText) rootView.findViewById(R.id.update_edit_name))
                .getText()
                .toString();
        String surname = ((EditText) rootView.findViewById(R.id.update_edit_surname))
                .getText()
                .toString();
        String patronum = ((EditText) rootView.findViewById(R.id.update_edit_patronum))
                .getText()
                .toString();
        String position = ((EditText) rootView.findViewById(R.id.update_edit_position))
                .getText()
                .toString();
        String phone = ((EditText) rootView.findViewById(R.id.update_edit_phone))
                .getText()
                .toString();
        String email = ((EditText) rootView.findViewById(R.id.update_edit_email))
                .getText()
                .toString();
        String password = ((EditText) rootView.findViewById(R.id.update_edit_pass))
                .getText()
                .toString();

        return new User(name, surname, patronum, position, phone, email, password);
    }

    private boolean capitalLetterCheck(User user) {
        if (!UserDataValidator.capitalLetterCheck(user.getName()) ||
                !UserDataValidator.capitalLetterCheck(user.getSurname()) ||
                !UserDataValidator.capitalLetterCheck(user.getPatronum()) ||
                !UserDataValidator.capitalLetterCheck(user.getPosition())) {
            return false;
        }
        return true;
    }

    private final View.OnClickListener editButtListener = (v) -> {
        newData = getUserFromForm(); //получение новых данных из формы
        String password_d = ((EditText) getView().findViewById(R.id.update_edit_pass_d)) //поле поддтверждения пароля
                .getText()
                .toString();

        if (newData.emptyFieldCheck() || password_d.equals("")) { //Проверка пустых полей
            Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }
        //Проверка заглавных букв
        if (!capitalLetterCheck(newData)) {
            Snackbar.make(v, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }
        //Проверка формы номера телефона
        String validatorReply;
        validatorReply = UserDataValidator.phoneCheck(newData.getPhone());
        if (validatorReply != null) {
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }
        //Валидация пароля
        validatorReply = UserDataValidator.passwordCheck(newData.getPass());
        if (validatorReply != null) { //если к строке есть замечания
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }
        //Проверка совпадения паролей в полях
        if (!newData.getPass().equals(password_d)) {
            Snackbar.make(v, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
        } else {
            newData.setPhone(newData.getPhone()
                    .replaceAll(" ", "")
                    .replaceAll("-", ""));
            UserForRequest updatedUser = new UserForRequest(newData.getName(),
                    newData.getSurname(),
                    newData.getPatronum(),
                    newData.getPosition(),
                    newData.getPhone(),
                    newData.getEmail(),
                    newData.getPass());
            new UpdateTask(oldData.getEmail(), oldData.getPass(), updatedUser).execute();
        }
    };

    public static UpdateDataFragment newInstance(String param1, String param2) {
        UpdateDataFragment fragment = new UpdateDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            oldData = (User) getArguments().getSerializable("user");
            Log.e("GETTING_ARGUMENT", oldData.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View rootView = getView();
        phoneEdit = (EditText) rootView.findViewById(R.id.update_edit_phone);
        phoneEdit.addTextChangedListener(new PhoneNumberFormattingTextWatcher("RU"));

        //Автоматическое заполнение полей, чтобы не заполнять всю форму заново
        ((EditText) rootView.findViewById(R.id.update_edit_name)).setText(oldData.getName());
        ((EditText) rootView.findViewById(R.id.update_edit_surname)).setText(oldData.getSurname());
        ((EditText) rootView.findViewById(R.id.update_edit_patronum)).setText(oldData.getPatronum());
        ((EditText) rootView.findViewById(R.id.update_edit_position)).setText(oldData.getPosition());
        phoneEdit.setText(oldData.getPhone());
        ((EditText) rootView.findViewById(R.id.update_edit_email)).setText(oldData.getEmail());
        ((EditText) rootView.findViewById(R.id.update_edit_pass)).setText(oldData.getPass());
        ((EditText) rootView.findViewById(R.id.update_edit_pass_d)).setText(oldData.getPass());

        updateButton = rootView.findViewById(R.id.update_butt_save);
        updateButton.setOnClickListener(editButtListener);
    }

    private class UpdateTask extends AsyncTask<Void, Void, JointUserCompany> {
        private final String oldEmail; //текущий логин
        private final String oldPassword; //текущий пароль
        private UserForRequest newUserData; //новын данные

        private UpdateTask(String email, String password, UserForRequest newUserData) {
            this.oldEmail = email;
            this.oldPassword = password;
            this.newUserData = newUserData;
        }

        @Override
        protected JointUserCompany doInBackground(Void... voids) {
            JointUserCompany updatedUserCompany = UserClient.update(oldEmail, oldPassword, newUserData);
            return updatedUserCompany;
        }

        @Override
        protected void onPostExecute(JointUserCompany updatedUserCompany) {
            super.onPostExecute(updatedUserCompany);

            if (updatedUserCompany == null) {
                EditText passwordEdit = getView().findViewById(R.id.update_edit_pass_d);
                Snackbar.make(passwordEdit, "Изменение данных не удалось!", Snackbar.LENGTH_LONG).show();
                return;
            }

            User user = new User(updatedUserCompany.getName(),
                    updatedUserCompany.getSurname(),
                    updatedUserCompany.getPatronum(),
                    updatedUserCompany.getPosition(),
                    updatedUserCompany.getPhoneNumber(),
                    updatedUserCompany.getEmail(),
                    newUserData.getPassword());

            SharedPreferences preferences = getActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
            //Если пароль изменился, то выход из акка
            if (!user.getPass().equals(Preferences.getPassword(preferences))) {
                Preferences.removePassword(preferences);

                startActivity(new Intent(getActivity(), StartActivity.class));
                getActivity().finish();
            } else { //переход к профилю
                Bundle bundle = new Bundle();
                bundle.putSerializable("newUserData", user);
                ((AccountActivity) getActivity())
                        .getNavController()
                        .navigate(R.id.action_nav_updating_to_nav_profile,bundle);
            }
        }
    }
}