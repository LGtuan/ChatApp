package com.example.frontend.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontend.config.Constant;
import com.example.frontend.config.Utilities;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestApi {
    public static void SendRequest(
            Context context,
            String path,
            Map<String,String> params,
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
                return params;
            }
        };

        Utilities.showLoading(context);
        queue.add(stringRequest);
    }
}
