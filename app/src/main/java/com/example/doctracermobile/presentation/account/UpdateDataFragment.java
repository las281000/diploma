package com.example.doctracermobile.presentation.account;

import static android.content.Context.MODE_PRIVATE;
import static com.example.doctracermobile.utile.Constants.APP_PREFERENCES;

import android.content.Intent;
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
import com.example.doctracermobile.request.UserProjectRequest;
import com.example.doctracermobile.request.UserRequest;
import com.example.doctracermobile.usecase.DataValidator;
import com.google.android.material.snackbar.Snackbar;


public class UpdateDataFragment extends Fragment {

/*    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    private EditText phoneEdit;
    private User oldUserData; // для текущих данных
    private User newUserData; // для новых данных

    private Button updateButton;
    private final View.OnClickListener editButtListener = (v) -> {
        newUserData = getUserFromForm(); //получение новых данных из формы
        String password_d = ((EditText) getView().findViewById(R.id.update_edit_pass_d)) //поле поддтверждения пароля
                .getText()
                .toString();

        if (newUserData.emptyFieldCheck() || password_d.equals("")) { //Проверка пустых полей
            Snackbar.make(v, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
            return;
        }
        //Проверка заглавных букв
        if (!capitalLetterCheck(newUserData)) {
            Snackbar.make(v, "Введите ФИО и должность с заглавной буквы!", Snackbar.LENGTH_LONG).show();
            return;
        }
        //Проверка формы номера телефона
        String validatorReply;
        validatorReply = DataValidator.phoneCheck(newUserData.getPhoneNumber());
        if (validatorReply != null) {
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }
        //Валидация пароля
        validatorReply = DataValidator.passwordCheck(newUserData.getPassword());
        if (validatorReply != null) { //если к строке есть замечания
            Snackbar.make(v, validatorReply, Snackbar.LENGTH_LONG).show();
            return;
        }
        //Проверка совпадения паролей в полях
        if (!newUserData.getPassword().equals(password_d)) {
            Snackbar.make(v, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
        } else {
            newUserData.setPhoneNumber(newUserData.getPhoneNumber()
                    .replaceAll(" ", "")
                    .replaceAll("-", ""));
            UserRequest updatedUser = new UserRequest(newUserData.getName(),
                    newUserData.getSurname(),
                    newUserData.getPatronum(),
                    newUserData.getPosition(),
                    newUserData.getPhoneNumber(),
                    newUserData.getEmail(),
                    newUserData.getPassword());
            new UpdateTask(oldUserData.getEmail(), oldUserData.getPassword(), updatedUser).execute();
        }
    };

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
        if (!DataValidator.capitalLetterCheck(user.getName()) ||
                !DataValidator.capitalLetterCheck(user.getSurname()) ||
                !DataValidator.capitalLetterCheck(user.getPatronum()) ||
                !DataValidator.capitalLetterCheck(user.getPosition())) {
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            oldUserData = (User) getArguments().getSerializable("user");
            Log.e("GETTING_ARGUMENT", oldUserData.toString());
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
        ((EditText) rootView.findViewById(R.id.update_edit_name)).setText(oldUserData.getName());
        ((EditText) rootView.findViewById(R.id.update_edit_surname)).setText(oldUserData.getSurname());
        ((EditText) rootView.findViewById(R.id.update_edit_patronum)).setText(oldUserData.getPatronum());
        ((EditText) rootView.findViewById(R.id.update_edit_position)).setText(oldUserData.getPosition());
        phoneEdit.setText(oldUserData.getPhoneNumber());
        ((EditText) rootView.findViewById(R.id.update_edit_email)).setText(oldUserData.getEmail());
        ((EditText) rootView.findViewById(R.id.update_edit_pass)).setText(oldUserData.getPassword());
        ((EditText) rootView.findViewById(R.id.update_edit_pass_d)).setText(oldUserData.getPassword());

        //прослушка в кнопку
        updateButton = rootView.findViewById(R.id.update_butt_save);
        updateButton.setOnClickListener(editButtListener);
    }

    private class UpdateTask extends AsyncTask<Void, Void, UserProjectRequest> {
        private final String oldEmail; //текущий логин
        private final String oldPassword; //текущий пароль
        private UserRequest newUserData; //новын данные

        private UpdateTask(String email, String password, UserRequest newUserData) {
            this.oldEmail = email;
            this.oldPassword = password;
            this.newUserData = newUserData;
        }

        @Override
        protected UserProjectRequest doInBackground(Void... voids) {
            UserProjectRequest updatedUserCompany = UserClient.update(oldEmail, oldPassword, newUserData);
            return updatedUserCompany;
        }

        @Override
        protected void onPostExecute(UserProjectRequest updatedUserCompany) {
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

            //Если пароль изменился, то выход из акка
            if (!user.getPassword().equals(oldPassword)) {
                Preferences.removePassword(getActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE));
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