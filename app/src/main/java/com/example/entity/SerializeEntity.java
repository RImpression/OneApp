package com.example.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/9.
 * 连载实体类
 */
public class SerializeEntity {
    //连载ID，查询连接用
    private String id;
    //连载ID
    private String serial_id;
    //连载篇数
    private String number;
    //标题
    private String title;
    //摘抄
    private String excerpt;
    //内容
    private String content;
    //编辑者
    private String charge_edit;
    //阅读数
    private int read_num;
    //匹配时间
    private String maketime;
    //最后更新时间
    private String last_update_date;
    // 语音
    private String audio;
    //输入名字
    private String input_name;
    //最后更新名字
    private String last_update_name;
    //作者ID
    private String user_id;
    //作者名字
    private String user_name;
    //作者头像链接
    private String web_url;
    //作者简介
    private String desc;
    //点赞数
    private int praisenum;
    //分享数
    private int sharenum;
    //评论数
    private int commentnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCharge_edit() {
        return charge_edit;
    }

    public void setCharge_edit(String charge_edit) {
        this.charge_edit = charge_edit;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getMaketime() {
        return maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getInput_name() {
        return input_name;
    }

    public void setInput_name(String input_name) {
        this.input_name = input_name;
    }

    public String getLast_update_name() {
        return last_update_name;
    }

    public void setLast_update_name(String last_update_name) {
        this.last_update_name = last_update_name;
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
     * 解析连载数据
     * @param result
     * @return serializeEntity
     */
    public static SerializeEntity parse2Json(String result) {
        try {
            SerializeEntity serializeEntity = new SerializeEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("data");
            serializeEntity.setId(object.getString("id"));
            serializeEntity.setSerial_id(object.getString("serial_id"));
            serializeEntity.setNumber(object.getString("number"));
            serializeEntity.setTitle(object.getString("title"));
            serializeEntity.setExcerpt(object.getString("excerpt"));
            serializeEntity.setContent(object.getString("content"));
            serializeEntity.setCharge_edit(object.getString("charge_edt"));
            serializeEntity.setRead_num(object.getInt("read_num"));
            serializeEntity.setMaketime(object.getString("maketime"));
            serializeEntity.setLast_update_date(object.getString("last_update_date"));
            serializeEntity.setAudio(object.getString("audio"));
            serializeEntity.setInput_name(object.getString("input_name"));
            serializeEntity.setLast_update_name(object.getString("last_update_name"));
            serializeEntity.setPraisenum(object.getInt("praisenum"));
            serializeEntity.setSharenum(object.getInt("sharenum"));
            serializeEntity.setCommentnum(object.getInt("commentnum"));
            JSONObject authorObject = object.getJSONObject("author");
            serializeEntity.setUser_id(authorObject.getString("user_id"));
            serializeEntity.setUser_name(authorObject.getString("user_name"));
            serializeEntity.setWeb_url(authorObject.getString("web_url"));
            serializeEntity.setDesc(authorObject.getString("desc"));

            //Log.i("json",serializeEntity.toString());
            return serializeEntity;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
