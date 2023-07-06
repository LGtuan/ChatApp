package com.example.frontend.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;
import com.example.frontend.model.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class ChatActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        Room room = (Room) intent.getSerializableExtra("room");

        Log.d("room", room.toString());
    }
}