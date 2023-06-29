package com.example.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.frontend.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private ImageButton buttonBack;
    private EditText editEmail, editName, editPassword, editConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView(){
        editEmail = findViewById(R.id.edit_email_login);
        editName = findViewById(R.id.edit_name_register);
        editPassword = findViewById(R.id.edit_password_register);
        editConfirmPassword = findViewById(R.id.edit_password2_register);
        buttonRegister = findViewById(R.id.btn_register);
        buttonBack = findViewById(R.id.btn_back_register);

        buttonRegister.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    private void register(){
        String email = editEmail.getText().toString();
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        String confirmPass = editConfirmPassword.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_back_register:
                onBackPressed();
                break;
        }
    }
}