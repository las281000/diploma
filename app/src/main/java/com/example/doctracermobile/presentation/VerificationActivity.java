package com.example.doctracermobile.presentation;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.doctracermobile.R;
import com.example.doctracermobile.repository.UserClient;
import com.google.android.material.snackbar.Snackbar;

public class VerificationActivity extends AppCompatActivity {

    EditText codeEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);
    }


    public void onClick_reg_finish(View view) {
        String email = this.getIntent().getStringExtra("email");
        codeEdit = (EditText) findViewById(R.id.conf_edit_code);
        String code = codeEdit.getText().toString();
        Log.d("CODE", code);

        new ConfTask(code, email).execute();
    }

    private class ConfTask extends AsyncTask<Void, Void, Boolean> {
        private String code;
        private String email;

        private ConfTask(String code, String email) {
            this.code = code;
            this.email = email;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return UserClient.verifyEmail(code, email);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Intent to_start = new Intent(VerificationActivity.this, StartActivity.class);
                startActivity(to_start);
            } else {
                Snackbar.make(codeEdit, "Неверный код! ПОШЕЛ НАХУЙ))))", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}