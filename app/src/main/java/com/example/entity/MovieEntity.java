package com.example.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 解析电影数据
     * @param result
     * @return movieList
     */
    public static List<MovieEntity> parse2Json(String result) {
        List<MovieEntity> movieList = null;
        MovieEntity movieEntity = null;
        try {
            movieList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                movieEntity = new MovieEntity();
                JSONObject object = jsonArray.getJSONObject(i);
                movieEntity.setId(object.getString("id"));
                movieEntity.setTitle(object.getString("title"));
                movieEntity.setVerse(object.getString("verse"));
                movieEntity.setVerse_en(object.getString("verse_en"));
                movieEntity.setScore(object.getString("score"));
                movieEntity.setRevisedscore(object.getString("revisedscore"));
                movieEntity.setReleasetime(object.getString("releasetime"));
                movieEntity.setScoretime(object.getString("scoretime"));
                movieEntity.setCover(object.getString("cover"));
                //Log.i("json",movieEntity.getTitle());
                movieList.add(movieEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

}
