package com.example.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.frontend.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonLogin, buttonShowForgotPass;
    private ImageButton buttonBack;
    private EditText editEmail, editPassword;
    private TextView textError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {
        buttonLogin = findViewById(R.id.btn_login);
        buttonBack = findViewById(R.id.btn_back_login);
        buttonShowForgotPass = findViewById(R.id.btn_show_forgot_pass);
        textError = findViewById(R.id.text_error_login);
        editPassword = findViewById(R.id.edit_password_login);
        editEmail = findViewById(R.id.edit_email_login);

        buttonBack.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonShowForgotPass.setOnClickListener(this);
    }

    private void login () {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if(email.equals("") || password.equals("")){
            textError.setText("Invalid input field.");
        }


    }

    private void showForgotPassActivity(){
        Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_back_login:
                onBackPressed();
                break;
            case R.id.btn_show_forgot_pass:
                showForgotPassActivity();
                break;
        }
    }
}