package com.example.frontend.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.frontend.R;

public class Lap2Activity extends AppCompatActivity implements LoadImageInterface {

    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap2);

        button = findViewById(R.id.btn_lab2);
        imageView = findViewById(R.id.img_lap2);
        button.setOnClickListener(v-> {
            new DemoAsyncTask(this,this)
                    .execute("http://tinypic.com/images/goodbye.jpg");
        });

    }

    @Override
    public void onSuccess(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onFail() {
        Toast.makeText(this, "Load image fail", Toast.LENGTH_LONG);
    }
}