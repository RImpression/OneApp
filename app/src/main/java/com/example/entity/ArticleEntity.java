package com.example.entity;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by RImpression on 2016/3/25 0025.
 *
 */
public class ArticleEntity {
    //发布显示日期
    private String date;
    //时间
    private String[] time;
    //类型，1=短片，2=连载，3=问答
    private int[] type;

    //以下为类型3
    //问题ID
    private String question_id;
    //问题标题
    private String question_title;
    //回答标题
    private String answer_title;
    //回答内容
    private String answer_content;
    //问题发布时间
    private String question_makettime;

    //以下为类型1
    //正文ID
    private String content_id;
    //短篇标题
    private String hp_title;
    //短篇导读
    private String guide_word;
    //是否拥有语音，类型1,2拥有属性
    private boolean has_audio;


    //以下为类型2
    //长篇ID
    private String id;
    private String serial_id;
    private String number;
    //长篇标题
    private String title;
    //长篇正文
    private String excerpt;
    //阅读数量
    private String read_num;
    //长篇更新时间
    private String maketime;

    //以下为作者信息，类型1,2拥有，2不含wb_name;
    //作者ID
    private String user_id;
    //作者名字
    private String user_name;
    //作者WEB图片地址
    private String web_url;
    //作者微博名称
    private String wb_name;
    //作者简介
    private String desc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public int[] getType() {
        return type;
    }

    public void setType(int[] type) {
        this.type = type;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getAnswer_title() {
        return answer_title;
    }

    public void setAnswer_title(String answer_title) {
        this.answer_title = answer_title;
    }

    public String getQuestion_makettime() {
        return question_makettime;
    }

    public void setQuestion_makettime(String question_makettime) {
        this.question_makettime = question_makettime;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getGuide_word() {
        return guide_word;
    }

    public void setGuide_word(String guide_word) {
        this.guide_word = guide_word;
    }

    public String getHp_title() {
        return hp_title;
    }

    public void setHp_title(String hp_title) {
        this.hp_title = hp_title;
    }

    public boolean isHas_audio() {
        return has_audio;
    }

    public void setHas_audio(boolean has_audio) {
        this.has_audio = has_audio;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getMaketime() {
        return maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
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

    public String getWb_name() {
        return wb_name;
    }

    public void setWb_name(String wb_name) {
        this.wb_name = wb_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
