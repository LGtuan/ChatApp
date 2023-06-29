package com.example.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.frontend.R;


public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin, buttonShowForgotPass;
    private ImageButton buttonBack;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        initView();

    }

    private void initView() {
        buttonLogin = findViewById(R.id.btn_login);
        buttonBack = findViewById(R.id.btn_back_login);
        buttonShowForgotPass = findViewById(R.id.btn_show_forgot_pass);

        buttonBack.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonShowForgotPass.setOnClickListener(this);
    }

    private void login () {

    }

    private void showForgotPassActivity(){

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

                break;
            default:
                break;
        }
    }
}