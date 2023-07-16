package com.example.frontend.config;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

public class Utilities {
    private static ProgressDialog progressDialog; // loading dialog

    public static void ShowErrorDialog(Context context){

    }

    public static void showLoading(Context context) {
        hideLoading(); // Ẩn bất kỳ thông báo tải nào đang hiển thị trước đó
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static String getStringLocalValue(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
