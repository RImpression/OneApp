package com.example.oneapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;

/**
 * Created by RImpression on 2016/3/20 0020.
 */
public class FragmentArticle extends Fragment {

    private String url = "http://v3.wufazhuce.com:8000/api/movie/list/0?";
    private Button btnClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArticleRequest();
            }
        });

    }

    private void initView() {
        btnClick = (Button) getView().findViewById(R.id.btnClick);

    }


    /**
     * 请求阅读文章数据
     */
    private void getArticleRequest() {
        new MyRequest(getContext().getApplicationContext()).getRequest(url, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("articleData",result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(getContext().getApplicationContext(),"Request Error",Toast.LENGTH_SHORT).show();
                Log.i("articleData",volleyError.toString());
            }
        });
    }
}
