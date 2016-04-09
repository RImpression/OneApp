package com.example.oneapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.example.entity.EssayEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/9.
 */
public class EssayActivity extends BaseActivity {
    private Button btnClick;
    private static final String URL_ESSAY = "http://v3.wufazhuce.com:8000/api/essay/";
    private String URL_ESSAY_CONTENT;
    private String ID;
    private EssayEntity essayEntity = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        initToolbar(R.string.essay,true);
        ID = getIntent().getStringExtra("ID");
        URL_ESSAY_CONTENT = URL_ESSAY+ID+"?";
        initViews();
    }

    private void initViews() {
        btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestEssayData();
            }
        });

    }

    /**
     * 请求短篇数据
     */
    private void requestEssayData() {
        new MyRequest(this).getRequest(URL_ESSAY_CONTENT, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("result",result.toString());
                parse2Json(result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("result",volleyError.toString());
                ShowToast("请求数据错误");
            }
        });

    }


    /**
     * 解析短篇数据
     * @param result
     * @return essayEntity
     */
    private EssayEntity parse2Json(String result) {
        try {
            essayEntity = new EssayEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("data");
            essayEntity.setContent_id(object.getString("content_id"));
            essayEntity.setHp_title(object.getString("hp_title"));
            essayEntity.setSub_title(object.getString("sub_title"));
            essayEntity.setHp_author(object.getString("hp_author"));
            essayEntity.setAuth_it(object.getString("auth_it"));
            essayEntity.setHp_author_introduce(object.getString("hp_author_introduce"));
            essayEntity.setHp_content(object.getString("hp_content"));
            essayEntity.setLast_update_date(object.getString("last_update_date"));
            essayEntity.setGuide_word(object.getString("guide_word"));
            essayEntity.setAudio(object.getString("audio"));
            essayEntity.setPraisenum(object.getInt("praisenum"));
            essayEntity.setSharenum(object.getInt("sharenum"));
            essayEntity.setCommentnum(object.getInt("commentnum"));
            JSONArray jsonArray = object.getJSONArray("author");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject authorObject = jsonArray.getJSONObject(i);
                essayEntity.setUser_id(authorObject.getString("user_id"));
                essayEntity.setUser_name(authorObject.getString("user_name"));
                essayEntity.setWeb_url(authorObject.getString("web_url"));
                essayEntity.setDesc(authorObject.getString("desc"));
                essayEntity.setWb_name(authorObject.getString("wb_name"));
            }
            Log.i("json",essayEntity.toString());
            return essayEntity;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
