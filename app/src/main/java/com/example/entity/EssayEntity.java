package com.example.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/9.
 * 短篇实体类
 */
public class EssayEntity {
    //短篇ID
    private String content_id;
    //短篇标题
    private String hp_title;
    //副标题
    private String sub_title;
    //作者
    private String hp_author;
    //作者简介
    private String auth_it;
    //编辑者
    private String hp_author_introduce;
    //短篇内容
    private String hp_content;
    //匹配时间
    private String makettime;
    //最后更新时间
    private String last_update_date;
    //导读
    private String guide_word;
    //语音
    private String audio;
    //作者ID
    private String user_id;
    //作者名字
    private String user_name;
    //作者头像链接
    private String web_url;
    //作者简介
    private String desc;
    //作者微博名
    private String wb_name;
    //点赞数
    private int praisenum;
    //分享数
    private int sharenum;
    //评论数
    private int commentnum;

    public String getHp_title() {
        return hp_title;
    }

    public void setHp_title(String hp_title) {
        this.hp_title = hp_title;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getHp_author() {
        return hp_author;
    }

    public void setHp_author(String hp_author) {
        this.hp_author = hp_author;
    }

    public String getAuth_it() {
        return auth_it;
    }

    public void setAuth_it(String auth_it) {
        this.auth_it = auth_it;
    }

    public String getHp_author_introduce() {
        return hp_author_introduce;
    }

    public void setHp_author_introduce(String hp_author_introduce) {
        this.hp_author_introduce = hp_author_introduce;
    }

    public String getHp_content() {
        return hp_content;
    }

    public void setHp_content(String hp_content) {
        this.hp_content = hp_content;
    }

    public String getMakettime() {
        return makettime;
    }

    public void setMakettime(String makettime) {
        this.makettime = makettime;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getGuide_word() {
        return guide_word;
    }

    public void setGuide_word(String guide_word) {
        this.guide_word = guide_word;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWb_name() {
        return wb_name;
    }

    public void setWb_name(String wb_name) {
        this.wb_name = wb_name;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public int getSharenum() {
        return sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

    /**
     * 解析短篇数据
     * @param result
     * @return essayEntity
     */
    public static EssayEntity parse2Json(String result) {
        try {
            EssayEntity essayEntity = new EssayEntity();
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
