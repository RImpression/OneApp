package com.example.entity;

/**
 * Created by lcr on 16/3/27.
 */
public class MovieEntity {

    //电影ID
    private String id;
    //电影名称
    private String title;
    //诗
    private String verse;
    private String verse_en;
    //分数
    private String score;
    //修订分数
    private String revisedscore;
    //发布时间
    private String releasetime;
    //打分时间
    private String scoretime;
    //图片
    private String cover;

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerse_en() {
        return verse_en;
    }

    public void setVerse_en(String verse_en) {
        this.verse_en = verse_en;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRevisedscore() {
        return revisedscore;
    }

    public void setRevisedscore(String revisedscore) {
        this.revisedscore = revisedscore;
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public String getScoretime() {
        return scoretime;
    }

    public void setScoretime(String scoretime) {
        this.scoretime = scoretime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
