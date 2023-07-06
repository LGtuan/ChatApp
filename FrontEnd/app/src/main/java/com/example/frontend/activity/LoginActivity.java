package com.example.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.frontend.R;
import com.example.frontend.api.RequestApi;
import com.example.frontend.api.VolleyCallBack;
import com.example.frontend.config.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        editEmail = findViewById(R.id.edit_email_register);

        buttonBack.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonShowForgotPass.setOnClickListener(this);
    }

    private void login () {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if(email.equals("") || password.equals("")){
            textError.setText("Invalid input field.");
            return;
        }

        String path = "api/user/login";
        Map<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("password", password);

        RequestApi.sendRequest(this, path, params, new VolleyCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject object = new JSONObject(json);
                    JSONObject data = object.getJSONObject("data");
                    if(object.getInt("err") == 200){
                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("auth",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("accessToken", data.getString("accessToken"));
                        editor.putString("email", data.getString("email"));
                        editor.putString("nickName", data.getString("nickName"));
                        editor.putString("id", data.getString("id"));
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }else {

                    }
                    Utilities.hideLoading();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("ABCD", error.toString());
                Utilities.hideLoading();
            }
        });
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