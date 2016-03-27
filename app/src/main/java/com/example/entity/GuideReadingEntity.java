package com.example.entity;

/**
 * Created by lcr on 16/3/27.
 * 轮播图片类
 */
public class GuideReadingEntity {

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
}
