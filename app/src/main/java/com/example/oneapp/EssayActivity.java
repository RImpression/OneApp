package com.example.oneapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.entity.EssayEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/9.
 */
public class EssayActivity extends BaseActivity implements View.OnClickListener {
    private static final String URL_ESSAY = "http://v3.wufazhuce.com:8000/api/essay/";
    private String URL_ESSAY_CONTENT;
    private String ID;
    private EssayEntity essayEntity = null;
    private TextView tvEssayTitle,tvEssayContent;
    private TextView tvAuthorName,tvAuthorTime,tvEditor,tvPraise,tvComment,tvShare;
    private ImageView imgAuthor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        initToolbar(R.string.essay,true);
        ID = getIntent().getStringExtra("ID");
        URL_ESSAY_CONTENT = URL_ESSAY+ID+"?";
        requestEssayData();
        initViews();
    }

    private void initViews() {
        tvEssayTitle = (TextView) findViewById(R.id.tvEssayTtile);
        tvEssayContent = (TextView) findViewById(R.id.tvEssayContent);
        tvAuthorName = (TextView) findViewById(R.id.tvAuthorname);
        tvAuthorTime = (TextView) findViewById(R.id.tvAuthorTime);
        tvPraise = (TextView) findViewById(R.id.tvPraise);
        tvComment = (TextView) findViewById(R.id.tvComment);
        tvShare = (TextView) findViewById(R.id.tvShare);
        tvEditor = (TextView) findViewById(R.id.tvEditor);
        imgAuthor = (ImageView) findViewById(R.id.imgAuthor);

        imgAuthor.setOnClickListener(this);
        tvPraise.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvShare.setOnClickListener(this);

    }

    /**
     * 请求短篇数据
     */
    private void requestEssayData() {
        new MyRequest(this).getRequest(URL_ESSAY_CONTENT, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result.toString());
                parse2Json(result);
                loadView();
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("result",volleyError.toString());
                ShowToast("请求数据错误");
            }
        });

    }

    /**
     * 加载布局
     */
    private void loadView() {
        tvAuthorName.setText(essayEntity.getUser_name());
        tvAuthorTime.setText(essayEntity.getLast_update_date());
        tvEssayTitle.setText(essayEntity.getHp_title());
        tvEssayContent.setText(Html.fromHtml(essayEntity.getHp_content()));
        tvEditor.setText(essayEntity.getHp_author_introduce());
        tvPraise.setText(String.valueOf(essayEntity.getPraisenum()));
        tvComment.setText(String.valueOf(essayEntity.getCommentnum()));
        tvShare.setText(String.valueOf(essayEntity.getSharenum()));
        Picasso.with(this).load(essayEntity.getWeb_url()).into(imgAuthor);
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



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAuthor:
                ShowToast("功能未开发");
                break;
            case R.id.tvPraise:
                ShowToast("功能未开发");
                break;
            case R.id.tvComment:
                ShowToast("功能未开发");
                break;
            case R.id.tvShare:
                ShowToast("功能未开发");
                break;
        }

    }
}
