package com.example.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.frontend.activity.LoginActivity;
import com.example.frontend.activity.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonShowLogin,buttonShowRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonShowLogin = findViewById(R.id.btn_show_sign_in);
        buttonShowRegister = findViewById(R.id.btn_show_register);

        buttonShowLogin.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonShowRegister.setOnClickListener(v-> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}