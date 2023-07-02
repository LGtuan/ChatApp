package com.example.frontend.api;

import com.android.volley.VolleyError;

public interface VolleyCallBack {
    void onSuccess(String data);
    void onError(VolleyError error);
}
