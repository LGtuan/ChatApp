package com.example.frontend.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class DemoAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private DemoInterface myInterface;

    public DemoAsyncTask(DemoInterface myInterface, Context context){
        this.myInterface = myInterface;
    }



    @Override
    protected Bitmap doInBackground(String... strings) {
        return null;
    }
}
