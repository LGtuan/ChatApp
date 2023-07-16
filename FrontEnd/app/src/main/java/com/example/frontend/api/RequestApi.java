package com.example.frontend.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontend.activity.HomeActivity;
import com.example.frontend.config.Constant;
import com.example.frontend.config.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestApi {
    public static void sendRequest(
            Context context,
            String path,
            Map<String,String> body,
            VolleyCallBack callBack
    ) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Constant.URL + path;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                callBack::onSuccess,
                callBack::onError
        ) {
            protected Map<String, String> getParams() {
                SharedPreferences sps = context.getSharedPreferences("auth",Context.MODE_PRIVATE);
                body.put("userId", sps.getString("id",""));
                return body;
            }
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sps = context.getSharedPreferences("auth",Context.MODE_PRIVATE);
                Map<String, String> headers = new HashMap<>();
                headers.put("authorization", "Bearer "+sps.getString("accessToken", ""));
                return headers;
            }
        };
        Utilities.showLoading(context);
        queue.add(stringRequest);
    }

    public static void sendSocketRequest(String path,Context context,JSONObject body) throws JSONException {
            SharedPreferences sps =
                    context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            body.put("userId", sps.getString("id",""));

            JSONObject headers = new JSONObject();
            headers.put("authorization", "Bearer "+sps.getString("accessToken",""));

            JSONObject header = new JSONObject();
            header.put("url", Constant.URL+path);
            header.put("data", body);
            header.put("method", "post");
            header.put("headers", headers);

            HomeActivity.SOCKET.emit("post", header);
    }
}
