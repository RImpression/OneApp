package com.example.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcr on 16/4/13.
 */
public class MovieDetailEntity {

    private String id;
    //标题
    private String title;
    //引导图片
    private String indexcover;
    //内容图片
    private String detailcover;

    private String video;

    private String verse;

    private String verse_en;
    //评分
    private String score;

    private String revisedscore;

    private String review;
    //关键词
    private String keywords;

    private String movie_id;

    private String info;

    private String officialstory;

    private String charge_edt;

    private int praisenum;

    private String sort;

    private String releasetime;

    private String scoretime;

    private String maketime;

    private String last_update_date;

    private String read_num;

    private List<String> photo = new ArrayList<>();

    private int sharenum;

    private int commentnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndexcover() {
        return indexcover;
    }

    public void setIndexcover(String indexcover) {
        this.indexcover = indexcover;
    }

    public String getDetailcover() {
        return detailcover;
    }

    public void setDetailcover(String detailcover) {
        this.detailcover = detailcover;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getOfficialstory() {
        return officialstory;
    }

    public void setOfficialstory(String officialstory) {
        this.officialstory = officialstory;
    }

    public String getCharge_edt() {
        return charge_edt;
    }

    public void setCharge_edt(String charge_edt) {
        this.charge_edt = charge_edt;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getMaketime() {
        return maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo.add(photo);
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
     * 解析电影详情数据
     * @param result
     * @return movieDetailEntity
     */
    public static MovieDetailEntity parseDetail2Json(String result) {
        MovieDetailEntity detailEntity = null;
        try {
            detailEntity = new MovieDetailEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("data");
            detailEntity.setId(object.getString("id"));
            detailEntity.setTitle(object.getString("title"));
            detailEntity.setIndexcover(object.getString("indexcover"));
            detailEntity.setDetailcover(object.getString("detailcover"));
            detailEntity.setVideo(object.getString("video"));
            detailEntity.setVerse(object.getString("verse"));
            detailEntity.setVerse_en(object.getString("verse_en"));
            detailEntity.setScore(object.getString("score"));
            detailEntity.setRevisedscore(object.getString("revisedscore"));
            detailEntity.setReview(object.getString("review"));
            detailEntity.setKeywords(object.getString("keywords"));
            detailEntity.setMovie_id(object.getString("movie_id"));
            detailEntity.setInfo(object.getString("info"));
            detailEntity.setOfficialstory(object.getString("officialstory"));
            detailEntity.setCharge_edt(object.getString("charge_edt"));
            detailEntity.setPraisenum(object.getInt("praisenum"));
            detailEntity.setSort(object.getString("sort"));
            detailEntity.setReleasetime(object.getString("releasetime"));
            detailEntity.setScoretime(object.getString("scoretime"));
            detailEntity.setMaketime(object.getString("maketime"));
            detailEntity.setLast_update_date(object.getString("last_update_date"));
            detailEntity.setRead_num(object.getString("read_num"));
            detailEntity.setSharenum(object.getInt("sharenum"));
            detailEntity.setCommentnum(object.getInt("commentnum"));
            JSONArray jsonArray = object.getJSONArray("photo");
            for (int i=0;i<jsonArray.length();i++) {
                //JSONObject photoObject = jsonArray.getJSONObject(i);
                detailEntity.setPhoto(String.valueOf(jsonArray.get(i)));
            }
            //Log.i("jsonphoto",detailEntity.getPhoto().toString());
            //Log.i("json",detailEntity.toString());
            return detailEntity;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return detailEntity;
    }


}
