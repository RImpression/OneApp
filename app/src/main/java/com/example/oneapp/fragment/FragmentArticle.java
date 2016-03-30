package com.example.oneapp.fragment;

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
import com.example.entity.ArticleEntity;
import com.example.entity.GuideReadingEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.example.oneapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RImpression on 2016/3/20 0020.
 */
public class FragmentArticle extends Fragment {

    private String URL_ARITICLE = "http://v3.wufazhuce.com:8000/api/reading/index/0?";
    private String URL_PHTOT = "http://v3.wufazhuce.com:8000/api/reading/carousel/?";
    private Button btnClick;
    private Button btnPhoto;
    private Boolean isFirst = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirst == true){
            getArticleRequest();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();


    }



    private void initView() {

    }

    /**
     * 请求轮播图片数据
     */
    private void getPhotoRequest() {
        new MyRequest(getContext().getApplicationContext()).getRequest(URL_PHTOT, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                parse2PhotoJson(result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("photoRequest", volleyError.toString());
            }
        });

    }


    /**
     * 解析轮播图片数据
     * @param result
     */
    private List<GuideReadingEntity> parse2PhotoJson(String result) {
        List<GuideReadingEntity> entitiyList = null;
        GuideReadingEntity guideEntity = null;
        try {
            entitiyList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                guideEntity = new GuideReadingEntity();
                JSONObject object = jsonArray.getJSONObject(i);
                guideEntity.setId(object.getString("id"));
                guideEntity.setTitle(object.getString("title"));
                guideEntity.setCover(object.getString("cover"));
                guideEntity.setBottom_text(object.getString("bottom_text"));
                guideEntity.setBgcolor(object.getString("bgcolor"));
                guideEntity.setPv_url(object.getString("pv_url"));
                Log.i("json",guideEntity.getTitle());
                entitiyList.add(guideEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entitiyList;
    }


    /**
     * 请求阅读文章数据
     */
    private void getArticleRequest() {
        new MyRequest(getContext().getApplicationContext()).getRequest(URL_ARITICLE, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                isFirst = false;
                parse2Json(result);
                Log.i("articleData", result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                isFirst = true;
                Toast.makeText(getContext().getApplicationContext(), "Request Error", Toast.LENGTH_SHORT).show();
                Log.i("articleData", volleyError.toString());
            }
        });
    }


    /**
     * 文章数据解析
     * @param result
     * @return
     */
    private List<ArticleEntity> parse2Json(String result) {
        List<ArticleEntity> articleEntityList=null;
        try {
           articleEntityList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            ArticleEntity articleEntity = null;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                articleEntity= new ArticleEntity();
                JSONObject jsb = jsonArray.getJSONObject(i);
                articleEntity.setDate(jsb.getString("date"));
                JSONArray jaItem = jsb.getJSONArray("items");
                for (int j = 0; j < jaItem.length(); j++) {
                    JSONObject joItem = jaItem.getJSONObject(j);
                    articleEntity.setTime(joItem.getString("time"));
                    articleEntity.setType(joItem.getInt("type"));

                    if (articleEntity.getType().get(j) == 3){   //类型3
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setQuestion_id(joContent.getString("question_id"));
                        articleEntity.setQuestion_title(joContent.getString("question_title"));
                        articleEntity.setAnswer_title(joContent.getString("answer_title"));
                        articleEntity.setAnswer_content(joContent.getString("answer_content"));
                        articleEntity.setQuestion_makettime(joContent.getString("question_makettime"));
                        //Log.i("json",articleEntity.getQuestion_title());

                    } else if (articleEntity.getType().get(j) == 2){  //类型2
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setId(joContent.getString("id"));
                        articleEntity.setSerial_id(joContent.getString("serial_id"));
                        articleEntity.setNumber(joContent.getString("number"));
                        articleEntity.setTitle(joContent.getString("title"));
                        articleEntity.setExcerpt(joContent.getString("excerpt"));
                        articleEntity.setRead_num(joContent.getString("read_num"));
                        articleEntity.setMaketime(joContent.getString("maketime"));
                        articleEntity.setHas_audio(joContent.getBoolean("has_audio"));

                        JSONObject joAuthor = joContent.getJSONObject("author");
                        articleEntity.setUser_id(joAuthor.getString("user_id"));
                        articleEntity.setUser_name(joAuthor.getString("user_name"));
                        articleEntity.setWeb_url(joAuthor.getString("web_url"));
                        articleEntity.setDesc(joAuthor.getString("desc"));


                    } else if (articleEntity.getType().get(j) == 1){  //类型1
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setContent_id(joContent.getString("content_id"));
                        articleEntity.setHp_title(joContent.getString("hp_title"));
                        articleEntity.setGuide_word(joContent.getString("guide_word"));
                        articleEntity.setHas_audio(joContent.getBoolean("has_audio"));
                        JSONArray jaAuthor = joContent.getJSONArray("author");
                        for (int k=0;k<jaAuthor.length();k++){
                            JSONObject joAuthor = jaAuthor.getJSONObject(k);
                            articleEntity.setUser_id(joAuthor.getString("user_id"));
                            articleEntity.setUser_name(joAuthor.getString("user_name"));
                            articleEntity.setWeb_url(joAuthor.getString("web_url"));
                            articleEntity.setDesc(joAuthor.getString("desc"));
                            articleEntity.setWb_name(joAuthor.getString("wb_name"));
                            Log.i("json",joAuthor.toString());
                        }

                    }
                    Log.i("json", articleEntity.getDate());
                }
                articleEntityList.add(articleEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articleEntityList;
    }

}
