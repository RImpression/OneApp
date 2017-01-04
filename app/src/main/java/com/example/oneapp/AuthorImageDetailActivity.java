package com.example.oneapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.adapter.AuthorImageAdapter;
import com.example.entity.GuideDetailEntity;
import com.example.entity.GuideReadingEntity;
import com.example.https.MyRequest;
import com.example.https.OneApi;
import com.example.interfaces.HttpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图片详情界面
 */
public class AuthorImageDetailActivity extends BaseActivity {
    private String URL_AUTHOR_ALL;
    private GuideReadingEntity guideEntity;
    private List<GuideDetailEntity> detailList;
    private TextView tvGuide;
    private ListView lvArticle;
    private AuthorImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_image_detail);
        guideEntity = (GuideReadingEntity) getIntent().getSerializableExtra("data");
        URL_AUTHOR_ALL = OneApi.URL_BANNER_DETAIL+guideEntity.getId()+"?";
        initToolbar(guideEntity.getTitle(),true);
        requestDetailData(URL_AUTHOR_ALL);
        initViews();
    }

    private void initViews() {
        tvGuide = (TextView) findViewById(R.id.tvGuide);
        lvArticle = (ListView) findViewById(R.id.lvArticle);
        lvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GuideDetailEntity entity = detailList.get(position);
                Intent intent = new Intent();
                intent.putExtra("ID",entity.getItem_id());
                Log.i("ID",entity.getItem_id());
                dataType(entity.getType(),intent);

            }
        });
    }

    /**
     * 片段文章类型1=短篇，2=连载，3=问答
     * @param type
     */
    private void dataType(String type,Intent i) {
        if (type.equals("1")) {
            i.setClass(this,EssayActivity.class);
        } else if (type.equals("2")) {
            i.setClass(this,SerializeActivity.class);
        } else if (type.equals("3")) {
            i.setClass(this,QuestionActivity.class);
        } else {
            ShowToast("数据出错");
        }
        startActivity(i);
    }

    /**
     * 请求轮播图片对应详情数据
     * @param url_author_all
     */
    private void requestDetailData(String url_author_all) {
        MyRequest.getRequest(this.getApplicationContext(),url_author_all, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                detailList = GuideDetailEntity.parse2Json(result);
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



}
