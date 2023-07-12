package com.example.frontend.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DemoAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private LoadImageInterface myInterface;
    private Context context;

    public DemoAsyncTask(LoadImageInterface myInterface, Context context) {
        this.myInterface = myInterface;
        this.context = context;
    }

    //ham doc du lieu tu server
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //tra ket qua ve client
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null)//neu ton tai anh
        {
            myInterface.onSuccess(bitmap);//tra anh ve client
        }
        else {
            myInterface.onFail();
        }
    }
}
