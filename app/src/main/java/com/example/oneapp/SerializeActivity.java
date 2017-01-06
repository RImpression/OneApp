package com.example.oneapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.adapter.CommentListAdapter;
import com.example.entity.CommentEntity;
import com.example.entity.SerializeEntity;
import com.example.https.MyRequest;
import com.example.https.OneApi;
import com.example.interfaces.HttpListener;
import com.example.utils.CircleTransform;
import com.example.utils.DateFormatUtil;
import com.example.utils.PariseUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcr on 16/4/9.
 */
public class SerializeActivity extends BaseActivity implements View.OnClickListener {

    private String URL_SERIALIZE_CONTENT;
    private String URL_COMMENT_ALL;
    private String ID;
    private SerializeEntity serializeEntity = null;
    private TextView tvAuthorName,tvAuthorTime,tvSerlTitle,tvSerlContent,tvPraise,tvComment,tvShare,tvEditor;
    private ImageView imgAuthor;
    private List<CommentEntity> commentList;
    private ListView lvComment;
    private CommentListAdapter commentAdapter;
    private Boolean isClick = false;
    private RelativeLayout layoutContent;
    private ContentLoadingProgressBar progressBar;
    private ScrollView scrollView;
    private FloatingActionButton fabTop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize);
        initToolbar(R.string.serialize,true);
        ID = getIntent().getStringExtra("ID");
        URL_SERIALIZE_CONTENT = OneApi.URL_SERIALIZE +ID+ "?";
        URL_COMMENT_ALL = OneApi.URL_SERIALIZE_COMMENT +ID+ "/0?";
        requestSerializeData();
        requestCommentData(URL_COMMENT_ALL);
        initViews();
    }


    //用于监听屏幕滑动Y坐标
    float y1 = 0;
    float y2 = 0;
    private void initViews() {
        tvAuthorName = (TextView) findViewById(R.id.tvAuthorname);
        tvAuthorTime = (TextView) findViewById(R.id.tvAuthorTime);
        tvSerlTitle = (TextView) findViewById(R.id.tvSerlTtile);
        tvSerlContent = (TextView) findViewById(R.id.tvSerlContent);
        tvEditor = (TextView) findViewById(R.id.tvEditor);
        tvPraise = (TextView) findViewById(R.id.tvPraise);
        tvComment = (TextView) findViewById(R.id.tvComment);
        tvShare = (TextView) findViewById(R.id.tvShare);
        imgAuthor = (ImageView) findViewById(R.id.imgAuthor);
        lvComment = (ListView) findViewById(R.id.lvComment);
        layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        progressBar.show();
        scrollView = (ScrollView) findViewById(R.id.myScrollView);
        fabTop = (FloatingActionButton) findViewById(R.id.fabTop);

        fabTop.setOnClickListener(this);
        tvPraise.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        imgAuthor.setOnClickListener(this);

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
     * 请求连载数据
     */
    private void requestSerializeData() {
        MyRequest.getRequest(this.getApplicationContext(),URL_SERIALIZE_CONTENT, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                serializeEntity = SerializeEntity.parse2Json(result);
                loadView(serializeEntity);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("result",volleyError.toString());
                ShowToast("请求数据错误");
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



    /**
     * 加载布局
     */
    private void loadView(SerializeEntity serializeEntity) {
        layoutContent.setVisibility(View.VISIBLE);
        progressBar.hide();
        tvAuthorName.setText(serializeEntity.getUser_name());
        tvAuthorTime.setText(DateFormatUtil.setDataFormat(serializeEntity.getMaketime()));
        tvSerlTitle.setText(serializeEntity.getTitle());
        tvSerlContent.setText(Html.fromHtml(serializeEntity.getContent()));
        tvEditor.setText(serializeEntity.getCharge_edit());
        tvPraise.setText(String.valueOf(serializeEntity.getPraisenum()));
        tvComment.setText(String.valueOf(serializeEntity.getCommentnum()));
        tvShare.setText(String.valueOf(serializeEntity.getSharenum()));
        Picasso.with(this).load(serializeEntity.getWeb_url()).transform(new CircleTransform()).fit().centerCrop().into(imgAuthor);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAuthor:
                ShowToast("功能未开发");
                break;
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
