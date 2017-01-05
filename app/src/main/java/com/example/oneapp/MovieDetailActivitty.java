package com.example.oneapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.adapter.CommentListAdapter;
import com.example.entity.CommentEntity;
import com.example.entity.MovieDetailEntity;
import com.example.entity.MovieStoryEntity;
import com.example.https.MyRequest;
import com.example.https.OneApi;
import com.example.interfaces.HttpListener;
import com.example.utils.CircleTransform;
import com.example.utils.DateFormatUtil;
import com.example.utils.PariseUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lcr on 16/4/13.
 */
public class MovieDetailActivitty extends BaseActivity implements View.OnClickListener {
    private String URL_MOVIEDETAIL_ALL;
    private String URL_MOVIESTORY_ALL;
    private String URL_COMMENT_ALL;
    private MovieDetailEntity movieDetailEntity = null;
    private MovieStoryEntity movieStoryEntity = null;
    private ImageView imgMovie,imgAuthor;
    private ImageButton imgbVideo,imgbShare;
    private TextView tvScore,tvUserScore,tvAuthorName,tvAuthorTime,tvAuthorPraise,tvStoryTitle,tvStoryContent;
    private List<CommentEntity> commentList;
    private ListView lvComment;
    private CommentListAdapter commentAdapter;
    private Boolean isClick = false;
    private LinearLayout layoutContent;
    private ContentLoadingProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String ID = getIntent().getStringExtra("movieID");
        String MovieTitle = getIntent().getStringExtra("movieTitle");
        URL_MOVIEDETAIL_ALL = OneApi.URL_MOVIE + ID + "?";
        URL_MOVIESTORY_ALL = OneApi.URL_MOVIE_STORY + ID + "/story/1/0?";
        URL_COMMENT_ALL = OneApi.URL_MOVIE_COMMENT + ID + "/0?";
        requestMovieStoryData(URL_MOVIESTORY_ALL);
        requestMovieDetailData(URL_MOVIEDETAIL_ALL);
        requestCommentData(URL_COMMENT_ALL);
        initToolbar(MovieTitle,true);
        initViews();
    }

    private void initViews() {
        imgMovie = (ImageView) findViewById(R.id.imgMovie);
        imgAuthor = (ImageView) findViewById(R.id.imgAuthor);
        imgbVideo = (ImageButton) findViewById(R.id.imgbVideo);
        imgbShare = (ImageButton) findViewById(R.id.imgbShare);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvUserScore = (TextView) findViewById(R.id.tvUserScore);
        tvAuthorName = (TextView) findViewById(R.id.tvAuthorname);
        tvAuthorTime = (TextView) findViewById(R.id.tvAuthorTime);
        tvStoryTitle = (TextView) findViewById(R.id.tvStoryTitle);
        tvStoryContent = (TextView) findViewById(R.id.tvStoryContent);
        tvAuthorPraise = (TextView) findViewById(R.id.tvAuthorPraise);
        lvComment = (ListView) findViewById(R.id.lvComment);
        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        progressBar.show();
        imgAuthor.setOnClickListener(this);
        imgbVideo.setOnClickListener(this);
        imgbShare.setOnClickListener(this);
        tvUserScore.setOnClickListener(this);
        tvAuthorPraise.setOnClickListener(this);
    }


    private void loadStoryView(MovieStoryEntity movieStoryEntity) {
        layoutContent.setVisibility(View.VISIBLE);
        progressBar.hide();
        Picasso.with(this).load(movieStoryEntity.getWeb_url()).transform(new CircleTransform()).fit().centerCrop().into(imgAuthor);
        tvAuthorName.setText(movieStoryEntity.getUser_name());
        tvAuthorTime.setText(DateFormatUtil.setDataFormat(movieStoryEntity.getInput_date()));
        tvStoryTitle.setText(movieStoryEntity.getTitle());
        tvStoryContent.setText(Html.fromHtml(movieStoryEntity.getContent()));
        tvAuthorPraise.setText(String.valueOf(movieStoryEntity.getPraisenum()));
    }

    private void loadDetailView() {
        Picasso.with(this).load(movieDetailEntity.getDetailcover()).into(imgMovie);
        if (movieDetailEntity.getScore() == "null") {
            tvUserScore.setText(R.string.user_score);
        }
    }


    /**
     * 请求电影故事数据
     */
    private void requestMovieStoryData(String url) {
        MyRequest.getRequest(this.getApplicationContext(),url, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                movieStoryEntity = MovieStoryEntity.parseStory2Json(result);
                loadStoryView(movieStoryEntity);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("result",volleyError.toString());
                ShowToast("数据请求出错");
                progressBar.hide();
            }
        });
    }




    /**
     * 请求电影详情数据
     */
    private void requestMovieDetailData(String url) {
        MyRequest.getRequest(this.getApplicationContext(),url, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                movieDetailEntity = MovieDetailEntity.parseDetail2Json(result);
                loadDetailView();
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("result",volleyError.toString());
                ShowToast("数据请出错");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbVideo:
                startVideo();
                //ShowToast("功能未开发");
                break;
            case R.id.imgbShare:
                ShowToast("功能未开发");
                break;
            case R.id.tvUserScore:
                ShowToast("功能未开发");
                break;
            case R.id.tvAuthorPraise:
                isClick = PariseUtil.PariseClick(this.getApplicationContext(),tvAuthorPraise,isClick);
                break;
            case R.id.imgAuthor:
                ShowToast("功能未开发");
                break;
        }
    }

    /**
     * 调用系统视频播放器播放网络视屏
     */
    private void startVideo() {
        Intent openVideo = new Intent(Intent.ACTION_VIEW);
        openVideo.setDataAndType(Uri.parse(movieDetailEntity.getVideo()), "video/*");
        startActivity(openVideo);
    }
}
