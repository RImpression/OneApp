package com.example.https;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.interfaces.HttpListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RImpression on 2016/3/21 0021.
 */
public class MyRequest {

    static RequestQueue mQueue;


    /**
     * GET请求
     * @param url  API链接
     * @param httpListener 自定义接口
     */
    public static void getRequest(Context mContext,String url, final HttpListener httpListener){
        if (mQueue == null){
            mQueue = new Volley().newRequestQueue(mContext);
        }
        MyStringRequest mRequest = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                httpListener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                httpListener.onError(volleyError);
            }
        });
        mQueue.add(mRequest);
    }


    /**
     * POST请求，带参数
     * @param url API链接
     * @param param1 post参数
     * @param param2 post参数的值
     * @param httpListener 返回接口回调
     */
    public static void postRequest(Context mContext,String url, final String[] param1, final String[] param2, final HttpListener httpListener){
        if (mQueue == null) {
            mQueue = new Volley().newRequestQueue(mContext);
        }
        MyStringRequest mRequest = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                httpListener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                httpListener.onError(volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                for (int i=0;i<param1.length;i++){
                    map.put(param1[i],param2[i]);
                }
                return map;
            }
        };
        mQueue.add(mRequest);
    }


    /**
     * POST请求，不带参数
     * @param url
     * @param httpListener
     */
    public static void postRequest(Context mContext,String url, final HttpListener httpListener){
        if (mQueue == null) {
            mQueue = new Volley().newRequestQueue(mContext);
        }
        MyStringRequest mRequest = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                httpListener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                httpListener.onError(volleyError);
            }
        });
        mQueue.add(mRequest);
    }


}







