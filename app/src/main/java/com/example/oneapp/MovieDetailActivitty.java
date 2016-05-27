package com.example.oneapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.example.adapter.CommentListAdapter;
import com.example.entity.CommentEntity;
import com.example.entity.MovieDetailEntity;
import com.example.entity.MovieStoryEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcr on 16/4/13.
 */
public class MovieDetailActivitty extends BaseActivity implements View.OnClickListener {
    private static final String URL_MOVIEDETAIL = "http://v3.wufazhuce.com:8000/api/movie/detail/";
    private static final String URL_MOVIESTORY = "http://v3.wufazhuce.com:8000/api/movie/";
    private static final String URL_COMMENT = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/movie/";
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String ID = getIntent().getStringExtra("movieID");
        String MovieTitle = getIntent().getStringExtra("movieTitle");
        URL_MOVIEDETAIL_ALL = URL_MOVIEDETAIL + ID + "?";
        URL_MOVIESTORY_ALL = URL_MOVIESTORY + ID + "/story/1/0?";
        URL_COMMENT_ALL = URL_COMMENT + ID + "/0?";
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
        imgAuthor.setOnClickListener(this);
        imgbVideo.setOnClickListener(this);
        imgbShare.setOnClickListener(this);
        tvUserScore.setOnClickListener(this);
        tvAuthorPraise.setOnClickListener(this);
    }


    private void loadStoryView() {
        Picasso.with(this).load(movieStoryEntity.getWeb_url()).into(imgAuthor);
        tvAuthorName.setText(movieStoryEntity.getUser_name());
        tvAuthorTime.setText(movieStoryEntity.getInput_date());
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
        new MyRequest(this).getRequest(url, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                movieStoryEntity = parseStory2Json(result);
                loadStoryView();
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("result",volleyError.toString());
                ShowToast("数据请求出错");
            }
        });
    }


    /**
     * 解析电影故事数据
     * @param result
     * @return movieStoryEntity
     */
    private MovieStoryEntity parseStory2Json(String result) {
        MovieStoryEntity storyEntity = null;
        try {
            storyEntity = new MovieStoryEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object= jsonObject.getJSONObject("data");
            storyEntity.setCount(object.getString("count"));
            JSONArray jsonArray = object.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject storyObject = jsonArray.getJSONObject(i);
                storyEntity.setId(storyObject.getString("id"));
                storyEntity.setMovie_id(storyObject.getString("movie_id"));
                storyEntity.setTitle(storyObject.getString("title"));
                storyEntity.setContent(storyObject.getString("content"));
                storyEntity.setUser_id1(storyObject.getString("user_id"));
                storyEntity.setSort(storyObject.getString("sort"));
                storyEntity.setPraisenum(storyObject.getInt("praisenum"));
                storyEntity.setInput_date(storyObject.getString("input_date"));
                storyEntity.setStory_type(storyObject.getString("story_type"));
                JSONObject userObject = storyObject.getJSONObject("user");
                storyEntity.setUser_id(userObject.getString("user_id"));
                storyEntity.setUser_name(userObject.getString("user_name"));
                storyEntity.setWeb_url(userObject.getString("web_url"));
            }
            Log.i("json",storyEntity.toString());
            return storyEntity;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return storyEntity;
    }

    /**
     * 请求电影详情数据
     */
    private void requestMovieDetailData(String url) {
        new MyRequest(this).getRequest(url, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                movieDetailEntity = parseDetail2Json(result);
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
     * 解析电影详情数据
     * @param result
     * @return movieDetailEntity
     */
    private MovieDetailEntity parseDetail2Json(String result) {
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
                entity.setScore(commentObject.getString("score"));
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbVideo:
                ShowToast("功能未开发");
                break;
            case R.id.imgbShare:
                ShowToast("功能未开发");
                break;
            case R.id.tvUserScore:
                ShowToast("功能未开发");
                break;
            case R.id.tvAuthorPraise:
                ShowToast("功能未开发");
                break;
            case R.id.imgAuthor:
                ShowToast("功能未开发");
                break;
        }
    }
}
