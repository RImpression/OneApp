package com.example.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/13.
 */
public class MovieStoryEntity {
    //故事条数
    private String count;
    private String id;
    private String movie_id;
    private String title;
    private String content;
    //作者I，无用
    //private String user_id1;
    //分类
    private String sort;
    private int praisenum;
    private String input_date;
    private String story_type;
    private String user_id;
    private String user_name;
    private String web_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getStory_type() {
        return story_type;
    }

    public void setStory_type(String story_type) {
        this.story_type = story_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    /**
     * 解析电影故事数据
     * @param result
     * @return movieStoryEntity
     */
    public static MovieStoryEntity parseStory2Json(String result) {
        MovieStoryEntity storyEntity = null;
        try {
            storyEntity = new MovieStoryEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object= jsonObject.getJSONObject("data");
            storyEntity.setCount(object.getString("count"));
            JSONArray jsonArray = object.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject storyObject = jsonArray.getJSONObject(i);
                storyEntity.setId(storyObject.getString("id"));
                storyEntity.setMovie_id(storyObject.getString("movie_id"));
                storyEntity.setTitle(storyObject.getString("title"));
                storyEntity.setContent(storyObject.getString("content"));
                //storyEntity.setUser_id(storyObject.getString("user_id1"));
                storyEntity.setSort(storyObject.getString("sort"));
                storyEntity.setPraisenum(storyObject.getInt("praisenum"));
                storyEntity.setInput_date(storyObject.getString("input_date"));
                storyEntity.setStory_type(storyObject.getString("story_type"));
                JSONObject userObject = storyObject.getJSONObject("user");
                storyEntity.setUser_id(userObject.getString("user_id"));
                storyEntity.setUser_name(userObject.getString("user_name"));
                storyEntity.setWeb_url(userObject.getString("web_url"));
                Log.i("info",storyEntity.getWeb_url() + "222");
                if (storyEntity.getWeb_url().equals("")) {
                    storyEntity.setWeb_url("http://image.wufazhuce.com/FvNnsE2f_tS6BI0XnwsYYEPe-5U5");
                }
            }
            Log.i("json",storyEntity.toString());
            return storyEntity;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return storyEntity;
    }
}
