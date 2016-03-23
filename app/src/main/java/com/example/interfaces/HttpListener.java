package com.example.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by RImpression on 2016/3/21 0021.
 */
public interface HttpListener {
    void onSuccess(String result);

    void onError(VolleyError volleyError);
}
