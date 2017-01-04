package com.example.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 各个评论实体类
 * Created by RImpression on 2016/5/23 0023.
 */
public class CommentEntity {
    //数量
    private int count;
    private String id;
    //引用
    private String quote;
    private String content;
    private int praisenum;
    private String input_date;
    private String user_id;
    private String user_name;
    private String web_url;
    //用户
    private String touser;
    private String type;
    private String score = null;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
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

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    /**
     * 解析评论数据
     * @param result
     * @return commentList
     */
    public static List<CommentEntity> parse2Json4Comment(String result) {
        List<CommentEntity> commentList = null;
        CommentEntity entity = null;
        try {
            commentList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("data");
            JSONArray jsonArray = object.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                entity = new CommentEntity();
                entity.setCount(object.getInt("count"));
                JSONObject commentObject = jsonArray.getJSONObject(i);
                entity.setId(commentObject.getString("id"));
                entity.setQuote(commentObject.getString("quote"));
                entity.setContent(commentObject.getString("content"));
                entity.setPraisenum(commentObject.getInt("praisenum"));
                entity.setInput_date(commentObject.getString("input_date"));
                entity.setTouser(commentObject.getString("touser"));
                entity.setType(commentObject.getString("type"));
                JSONObject userObject = commentObject.getJSONObject("user");
                entity.setUser_id(userObject.getString("user_id"));
                entity.setUser_name(userObject.getString("user_name"));
                entity.setWeb_url(userObject.getString("web_url"));
                if (entity.getWeb_url().equals("")) {
                    entity.setWeb_url("http://image.wufazhuce.com/FvNnsE2f_tS6BI0XnwsYYEPe-5U5");
                }
                //Log.i("json",entity.getUser_name());
                commentList.add(entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentList;
    }
}
