package com.example.oneapp;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.adapter.AuthorImageAdapter;
import com.example.entity.GuideDetailEntity;
import com.example.entity.GuideReadingEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AuthorImageDetailActivity extends BaseActivity {
    private static final String URL_AUTHOR = "http://v3.wufazhuce.com:8000/api/reading/carousel/";
    private String URL_AUTHOR_ALL;
    private GuideReadingEntity guideEntity;
    private List<GuideDetailEntity> detailList;
    //private LinearLayout layoutDetail;
    private TextView tvGuide;
    private ListView lvArticle;
    private AuthorImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_image_detail);
        guideEntity = (GuideReadingEntity) getIntent().getSerializableExtra("data");
        Log.i("data",guideEntity.getTitle());
        URL_AUTHOR_ALL = URL_AUTHOR+guideEntity.getId()+"?";
        initToolbar(guideEntity.getTitle(),true);
        requestDetailData(URL_AUTHOR_ALL);
        initViews();
    }

    private void initViews() {
        //layoutDetail = (LinearLayout) findViewById(R.id.layoutDetail);
        tvGuide = (TextView) findViewById(R.id.tvGuide);
        lvArticle = (ListView) findViewById(R.id.lvArticle);
    }

    private void requestDetailData(String url_author_all) {
        new MyRequest(this).getRequest(url_author_all, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                detailList = parse2Json(result);
                loadListView(detailList);
            }

            @Override
            public void onError(VolleyError volleyError) {
                ShowToast("数据请求错误");
                Log.i("result",volleyError.toString());
            }
        });
    }

    private void loadListView(List<GuideDetailEntity> detailList) {
        //layoutDetail.setBackgroundColor();
        imageAdapter = new AuthorImageAdapter(this,detailList);
        lvArticle.setAdapter(imageAdapter);
        tvGuide.setText(Html.fromHtml(guideEntity.getBottom_text()));
    }

    private List<GuideDetailEntity> parse2Json(String result) {
        List<GuideDetailEntity> detailList = null;
        GuideDetailEntity entity = null;
        try {
            detailList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                entity = new GuideDetailEntity();
                JSONObject object = jsonArray.getJSONObject(i);
                entity.setItem_id(object.getString("item_id"));
                entity.setTitle(object.getString("title"));
                entity.setIntroduction(object.getString("introduction"));
                entity.setAuthor(object.getString("author"));
                entity.setWeb_url(object.getString("web_url"));
                entity.setNumber(object.getString("number"));
                entity.setType(object.getString("type"));
                detailList.add(entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detailList;
    }
}
