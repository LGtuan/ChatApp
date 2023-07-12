package com.example.frontend.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.frontend.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Lap2_3Activity extends AppCompatActivity {

    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap23);

        button=findViewById(R.id.btn_lab2_3);
        imageView=findViewById(R.id.img_lap2_3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread myThread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //doc anh
                        Bitmap bitmap=loadAnh("http://tinypic.com/images/goodbye.jpg");
                        //dua anh vao imageview
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                });
                myThread.start();
            }
        });
    }

    private Bitmap loadAnh(String str){
        URL url;
        Bitmap bitmap = null;
        try {
            url=new URL(str);
            bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}