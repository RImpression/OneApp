package com.example.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击轮播图片返回的作者文章推荐实体类
 * Created by RImpression on 2016/5/28 0028.
 */
public class GuideDetailEntity {
    private String item_id;
    private String title;
    private String introduction;
    private String author;
    private String web_url;
    private String number;
    private String type;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 解析轮播图片对应详情数据
     * @param result
     * @return
     */
    public static List<GuideDetailEntity> parse2Json(String result) {
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
                //entity.setWeb_url(object.getString("web_url"));
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
