package com.example.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRegister;
    private ImageButton buttonBack;
    private EditText editEmail, editName, editPassword, editConfirmPassword;
    private TextView textError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView(){
        editEmail = findViewById(R.id.edit_email_register);
        editName = findViewById(R.id.edit_name_register);
        editPassword = findViewById(R.id.edit_password_register);
        editConfirmPassword = findViewById(R.id.edit_password2_register);
        buttonRegister = findViewById(R.id.btn_register);
        buttonBack = findViewById(R.id.btn_back_register);
        textError = findViewById(R.id.text_error_register);

        buttonRegister.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    private void register(){
        String email = editEmail.getText().toString();
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        String confirmPass = editConfirmPassword.getText().toString();

        if(email.equals("") || password.equals("") || name.equals("")){
            textError.setText("Invalid input field.");
            return;
        }

        String path = "api/register";
        Map<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("password", password);
        params.put("confirmPassword",confirmPass);
        params.put("fullName", name);

        RequestApi.sendRequest(this, path, params, new VolleyCallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    if(object.getInt("err") == 200){
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
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