package com.example.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/9.
 * 问题实体类
 */
public class QuestionEntity {
    //问题ID
    private String question_id;
    //问题标题
    private String question_title;
    //问题详情
    private String question_content;
    //回答标题
    private String answer_title;
    //回答内容
    private String answer_content;
    //问题匹配时间
    private String question_makettime;
    //推荐
    private String recommend_flag;
    //编辑者
    private String charge_edit;
    //最后更新时间
    private String last_update_date;
    //阅读数
    private int readnum;
    //点赞数
    private int praisenum;
    //分享数
    private int sharenum;
    //评论数
    private int commentnum;

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getAnswer_title() {
        return answer_title;
    }

    public void setAnswer_title(String answer_title) {
        this.answer_title = answer_title;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getQuestion_makettime() {
        return question_makettime;
    }

    public void setQuestion_makettime(String question_makettime) {
        this.question_makettime = question_makettime;
    }

    public String getRecommend_flag() {
        return recommend_flag;
    }

    public void setRecommend_flag(String recommend_flag) {
        this.recommend_flag = recommend_flag;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getCharge_edit() {
        return charge_edit;
    }

    public void setCharge_edit(String charge_edit) {
        this.charge_edit = charge_edit;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public int getReadnum() {
        return readnum;
    }

    public void setReadnum(int readnum) {
        this.readnum = readnum;
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
     * 解析问题数据
     * @param result
     */
    public static QuestionEntity parse2Json(String result) {
        try {
            QuestionEntity questionEntity = new QuestionEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("data");
            questionEntity.setQuestion_id(object.getString("question_id"));
            questionEntity.setQuestion_title(object.getString("question_title"));
            questionEntity.setQuestion_content(object.getString("question_content"));
            questionEntity.setAnswer_title(object.getString("answer_title"));
            questionEntity.setAnswer_content(object.getString("answer_content"));
            questionEntity.setQuestion_makettime(object.getString("question_makettime"));
            questionEntity.setRecommend_flag(object.getString("recommend_flag"));
            questionEntity.setCharge_edit(object.getString("charge_edt"));
            questionEntity.setLast_update_date(object.getString("last_update_date"));
            questionEntity.setReadnum(object.getInt("read_num"));
            questionEntity.setPraisenum(object.getInt("praisenum"));
            questionEntity.setSharenum(object.getInt("sharenum"));
            questionEntity.setCommentnum(object.getInt("commentnum"));
            return questionEntity;
            //Log.i("json",questionEntity.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
