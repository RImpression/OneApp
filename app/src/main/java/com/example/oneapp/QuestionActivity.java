package com.example.oneapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.adapter.CommentListAdapter;
import com.example.entity.CommentEntity;
import com.example.entity.QuestionEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lcr on 16/4/9.
 */
public class QuestionActivity extends BaseActivity implements View.OnClickListener {
    private static final String URL_QUESTION = "http://v3.wufazhuce.com:8000/api/question/";
    private static final String URL_COMMENT = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/question/";
    private String URL_QUESTION_CONTENT;
    private String URL_COMMENT_ALL;
    private QuestionEntity questionEntity = null;
    private TextView tvQuestionTitle,tvQuestionContent,tvAnswerTitle,tvDate,tvAnswerContent,tvEditor;
    private String ID;
    private TextView tvPraise,tvComment,tvShare;
    private List<CommentEntity> commentList = null;
    //评论
    private ListView lvComment;
    private CommentListAdapter commentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initToolbar(R.string.question,true);
        ID = getIntent().getStringExtra("ID");
        URL_QUESTION_CONTENT = URL_QUESTION+ID+"?";
        URL_COMMENT_ALL = URL_COMMENT+ID+"/0?";
        //Log.i("ID",ID);
        //请求问答数据
        requestQuestionData(URL_QUESTION_CONTENT);
        //请求评论数据
        requestCommentData(URL_COMMENT_ALL);
        initViews();
    }



    private void initViews() {
        tvQuestionTitle = (TextView) findViewById(R.id.tvQuestionTitle);
        tvQuestionContent = (TextView) findViewById(R.id.tvQuestionContent);
        tvAnswerTitle = (TextView) findViewById(R.id.tvAnswerTitle);
        tvAnswerContent = (TextView) findViewById(R.id.tvAnswerContent);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvEditor = (TextView) findViewById(R.id.tvEditor);
        tvPraise = (TextView) findViewById(R.id.tvPraise);
        tvComment = (TextView) findViewById(R.id.tvComment);
        tvShare = (TextView) findViewById(R.id.tvShare);
        lvComment = (ListView) findViewById(R.id.lvComment);
        tvPraise.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvShare.setOnClickListener(this);
    }


    /**
     * 请求问题数据
     * @param URL
     */
    private void requestQuestionData(String URL) {
        new MyRequest(this).getRequest(URL, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                parse2Json(result);
                loadView();
            }

            @Override
            public void onError(VolleyError volleyError) {
                ShowToast("数据请求错误");
                Log.i("result",volleyError.toString());
            }
        });

    }

    /**
     * 请求评论数据
     * @param urlComment API
     */
    private void requestCommentData(String urlComment) {
        new MyRequest(this).getRequest(urlComment, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                commentList = parse2Json4Comment(result);
                loadCommentListView(commentList);
            }

            @Override
            public void onError(VolleyError volleyError) {
                ShowToast("数据请求错误");
                Log.i("result",volleyError.toString());
            }
        });
    }

    private void loadCommentListView(List<CommentEntity> commentList) {
        lvComment.setFocusable(false);
        commentAdapter = new CommentListAdapter(this,commentList);
        lvComment.setAdapter(commentAdapter);
    }

    /**
     * 解析评论数据
     * @param result
     * @return commentList
     */
    private List<CommentEntity> parse2Json4Comment(String result) {
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
                //Log.i("json",entity.getUser_name());
                commentList.add(entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    //加载布局
    private void loadView() {
        tvQuestionTitle.setText(questionEntity.getQuestion_title());
        tvQuestionContent.setText(questionEntity.getQuestion_content());
        tvAnswerTitle.setText(questionEntity.getAnswer_title());
        tvAnswerContent.setText(Html.fromHtml(questionEntity.getAnswer_content()));
        tvDate.setText(questionEntity.getQuestion_makettime());
        tvEditor.setText(questionEntity.getCharge_edit());
        tvPraise.setText(String.valueOf(questionEntity.getPraisenum()));
        tvComment.setText(String.valueOf(questionEntity.getCommentnum()));
        tvShare.setText(String.valueOf(questionEntity.getSharenum()));

    }

    /**
     * 解析问题数据
     * @param result
     */
    private QuestionEntity parse2Json(String result) {
        try {
            questionEntity = new QuestionEntity();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPraise:
                ShowToast("功能未开发");
                break;
            case R.id.tvComment:
                ShowToast("功能未开发");
                break;
            case R.id.tvShare:
                ShowToast("功能未开发");
                break;
        }

    }
}
