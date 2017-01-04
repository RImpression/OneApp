package com.example.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcr on 16/3/27.
 * 轮播图片类
 */
public class GuideReadingEntity implements Serializable {

    //轮播ID
    private String id;
    //标题
    private String title;
    //图片／横
    private String cover;
    //底部文字
    private String bottom_text;
    //底部颜色
    private String bgcolor;
    //返回信息SUCCESS
    private String pv_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPv_url() {
        return pv_url;
    }

    public void setPv_url(String pv_url) {
        this.pv_url = pv_url;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBottom_text() {
        return bottom_text;
    }

    public void setBottom_text(String bottom_text) {
        this.bottom_text = bottom_text;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 解析轮播图片数据
     * @param result
     */
    public static List<GuideReadingEntity> parse2PhotoJson(String result) {
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
                //Log.i("json",guideEntity.getTitle());
                entitiyList.add(guideEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entitiyList;
    }


}
