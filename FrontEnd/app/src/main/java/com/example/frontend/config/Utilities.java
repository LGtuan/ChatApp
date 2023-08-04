package com.example.frontend.config;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.frontend.R;

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

    public static void showCompleteMessage(Context context, String msg){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.complete_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 300);

        dialog.show();

        Button button = dialog.findViewById(R.id.btn_err_dialog);
        TextView txtMessage = dialog.findViewById(R.id.content_err_dialog);
        txtMessage.setText(msg);

        button.setOnClickListener(v->{
            dialog.dismiss();
        });
    }

    public static void showErrorMessage(Context context, String msg){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.error_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

        Button button = dialog.findViewById(R.id.btn_err_dialog);
        TextView txtMessage = dialog.findViewById(R.id.content_err_dialog);
        txtMessage.setText(msg);

        button.setOnClickListener(v->{
            dialog.dismiss();
        });
    }

    public static String getStringLocalValue(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
