package com.example.oneapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.adapter.CommentListAdapter;
import com.example.entity.CommentEntity;
import com.example.entity.QuestionEntity;
import com.example.https.MyRequest;
import com.example.https.OneApi;
import com.example.interfaces.HttpListener;
import com.example.utils.DateFormatUtil;
import com.example.utils.PariseUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lcr on 16/4/9.
 */
public class QuestionActivity extends BaseActivity implements View.OnClickListener {
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
    private Boolean isClick = false;
    private ContentLoadingProgressBar progressBar;
    private RelativeLayout layoutContent;
    private FloatingActionButton fabTop;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initToolbar(R.string.question,true);
        ID = getIntent().getStringExtra("ID");
        URL_QUESTION_CONTENT = OneApi.URL_QUESTION+ID+"?";
        URL_COMMENT_ALL = OneApi.URL_QUESTION_COMMMENT+ID+"/0?";
        //Log.i("ID",ID);
        //请求问答数据
        requestQuestionData(URL_QUESTION_CONTENT);
        //请求评论数据
        requestCommentData(URL_COMMENT_ALL);
        initViews();
    }

    //用于监听屏幕滑动Y坐标
    float y1 = 0;
    float y2 = 0;
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
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);
        fabTop = (FloatingActionButton) findViewById(R.id.fabTop);
        scrollView = (ScrollView) findViewById(R.id.myScrollView);
        fabTop.setOnClickListener(this);
        tvPraise.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        progressBar.show();
        //初始化缩放动画
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(fabTop,"scaleX",0.0f,1.0f);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(fabTop,"scaleY",0.0f,1.0f);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    y1 = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    y2 = event.getY();
                    if(y1 - y2 > 20 || scrollView.getScrollY() == 0) {
                        fabTop.setVisibility(View.GONE);
//                        Log.i("state","向上滑");
                    } else if(y2 - y1 > 50  && fabTop.getVisibility() == View.GONE) {
                        fabTop.setVisibility(View.VISIBLE);
//                        Log.i("state","向下滑");
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(animator1).with(animator2);
                        animatorSet.setDuration(200);
                        animatorSet.start();
                    }
                }
                return false;
            }
        });
    }




    /**
     * 请求问题数据
     * @param URL
     */
    private void requestQuestionData(String URL) {
        MyRequest.getRequest(this.getApplicationContext(),URL, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                questionEntity = QuestionEntity.parse2Json(result);
                loadView();
            }

            @Override
            public void onError(VolleyError volleyError) {
                ShowToast("数据请求错误");
                Log.i("result",volleyError.toString());
                progressBar.hide();
            }
        });

    }

    /**
     * 请求评论数据
     * @param urlComment API
     */
    private void requestCommentData(String urlComment) {
        MyRequest.getRequest(this.getApplicationContext(),urlComment, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                commentList = CommentEntity.parse2Json4Comment(result);
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



    //加载布局
    private void loadView() {
        //布局可见
        layoutContent.setVisibility(View.VISIBLE);
        progressBar.hide();
        tvQuestionTitle.setText(questionEntity.getQuestion_title());
        tvQuestionContent.setText(questionEntity.getQuestion_content());
        tvAnswerTitle.setText(questionEntity.getAnswer_title());
        tvAnswerContent.setText(Html.fromHtml(questionEntity.getAnswer_content()));
        tvDate.setText(DateFormatUtil.setDataFormat(questionEntity.getQuestion_makettime()));
        tvEditor.setText(questionEntity.getCharge_edit());
        tvPraise.setText(String.valueOf(questionEntity.getPraisenum()));
        tvComment.setText(String.valueOf(questionEntity.getCommentnum()));
        tvShare.setText(String.valueOf(questionEntity.getSharenum()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPraise:
                isClick = PariseUtil.PariseClick(this.getApplicationContext(),tvPraise,isClick);
                break;
            case R.id.tvComment:
                ShowToast("功能未开发");
                break;
            case R.id.tvShare:
                ShowToast("功能未开发");
                break;
            case R.id.fabTop:
                comeBackTob();
                break;
        }

    }

    private void comeBackTob() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        fabTop.setVisibility(View.GONE);
    }


}
