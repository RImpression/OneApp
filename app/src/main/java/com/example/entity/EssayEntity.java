package com.example.entity;

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
}
