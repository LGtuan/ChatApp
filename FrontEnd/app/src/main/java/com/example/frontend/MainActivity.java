package com.example.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.frontend.activity.HomeActivity;
import com.example.frontend.activity.LoginActivity;
import com.example.frontend.activity.RegisterActivity;
import com.example.frontend.config.Utilities;

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

        checkAuth();
    }

    private void checkAuth(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken","");
        String id = sharedPreferences.getString("id","");
        String email = sharedPreferences.getString("email","");

        if(!accessToken.equals("") && !id.equals("") && !email.equals("")){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}