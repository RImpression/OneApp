package com.example.oneapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.adapter.CommentListAdapter;
import com.example.entity.CommentEntity;
import com.example.entity.MusicEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.example.oneapp.R;
import com.example.utils.DateFormatUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RImpression on 2016/3/21 0021.
 */
public class FragmentMusic extends Fragment implements View.OnClickListener {

    private static final String URL_MUSICLIST = "http://v3.wufazhuce.com:8000/api/music/idlist/0?";
    private static final String URL_COMMENT = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/561/0?";
    //不完整链接，格式URL_MUSIC+333+?
    private static final String URL_MUSIC = "http://v3.wufazhuce.com:8000/api/music/detail/";
    private  String URL_COMMENT_ALL;
    private Context mContext;
    private ImageView imgMusic,imgAuthor;
    private ImageButton imgbPlay,imgbStory,imgbLyric,imgbInfo;
    private TextView tvAuthorName,tvAuthorInfo,tvMusicTitle,tvMusicTime;
    private TextView tvWord,tvStoryTitle,tvStoryAuthor,tvStoryContent,tvEditor1,tvLyric,tvEditor2,tvMusicInfo,tvEditor3;
    private LinearLayout layoutStory,layoutLyric,layoutInfo;
    private TextView tvShare,tvPraise,tvComment;
    private boolean isFirst = true;
    private MusicEntity musicEntity;
    private List<String> musicList;
    private List<CommentEntity> commentList;
    private ListView lvComment;
    private CommentListAdapter commentAdapter;
    View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirst == true) {
            getMusicList();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_music,container,false);

        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext().getApplicationContext();
        initView();
        if (isFirst == false) {
            loadMusicView(musicEntity);
        }
    }


    private void initView() {
        imgMusic = (ImageView) getView().findViewById(R.id.imgMusic);
        imgAuthor = (ImageView) getView().findViewById(R.id.imgStoryAuthor);
        imgbPlay = (ImageButton) getView().findViewById(R.id.imgbPlay);
        imgbStory = (ImageButton) getView().findViewById(R.id.imgbStory);
        imgbLyric = (ImageButton) getView().findViewById(R.id.imgbLyric);
        imgbInfo = (ImageButton) getView().findViewById(R.id.imgbInfo);
        tvAuthorName = (TextView) getView().findViewById(R.id.tvAuthorName);
        tvAuthorInfo = (TextView) getView().findViewById(R.id.tvAuthorInfo);
        tvMusicTime = (TextView) getView().findViewById(R.id.tvMusicTime);
        tvMusicTitle = (TextView) getView().findViewById(R.id.tvMusicTitle);
        tvWord = (TextView) getView().findViewById(R.id.tvWord);
        tvStoryTitle = (TextView) getView().findViewById(R.id.tvStoryTitle);
        tvStoryAuthor = (TextView) getView().findViewById(R.id.tvStoryAuthor);
        tvStoryContent = (TextView) getView().findViewById(R.id.tvStoryContent);
        tvLyric = (TextView) getView().findViewById(R.id.tvLyric);
        tvEditor1 = (TextView) getView().findViewById(R.id.tvEditor1);
        tvEditor2 = (TextView) getView().findViewById(R.id.tvEditor2);
        tvEditor3 = (TextView) getView().findViewById(R.id.tvEditor3);
        tvMusicInfo = (TextView) getView().findViewById(R.id.tvMusicInfo);
        layoutStory = (LinearLayout) getView().findViewById(R.id.layoutStory);
        layoutLyric = (LinearLayout) getView().findViewById(R.id.layoutLyric);
        layoutInfo = (LinearLayout) getView().findViewById(R.id.layoutInfo);
        tvShare = (TextView) getView().findViewById(R.id.tvShare);
        tvComment = (TextView) getView().findViewById(R.id.tvComment);
        tvPraise = (TextView) getView().findViewById(R.id.tvPraise);
        lvComment = (ListView) getView().findViewById(R.id.lvComment);
        tvShare.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvPraise.setOnClickListener(this);
        imgAuthor.setOnClickListener(this);
        imgbPlay.setOnClickListener(this);
        imgbStory.setOnClickListener(this);
        imgbLyric.setOnClickListener(this);
        imgbInfo.setOnClickListener(this);


    }

    private void loadMusicView(MusicEntity musicEntity) {
        Picasso.with(mContext).load(musicEntity.getCover()).into(imgMusic);
        Picasso.with(mContext).load(musicEntity.getAuthor_url()).into(imgAuthor);
        tvAuthorName.setText(musicEntity.getUser_name());
        tvAuthorInfo.setText(musicEntity.getDesc());
        tvMusicTitle.setText(musicEntity.getTitle());
        tvMusicTime.setText(new DateFormatUtil().setDataFormat(musicEntity.getMaketime()));
        tvStoryTitle.setText(musicEntity.getStory_title());
        tvStoryAuthor.setText(musicEntity.getStory_author_name());
        tvStoryContent.setText(Html.fromHtml(musicEntity.getStory()));
        tvEditor1.setText(musicEntity.getCharge_edit());
        tvEditor2.setText(musicEntity.getCharge_edit());
        tvEditor3.setText(musicEntity.getCharge_edit());
        tvLyric.setText(musicEntity.getLyric());
        tvMusicInfo.setText(musicEntity.getInfo());
        tvShare.setText(musicEntity.getSharenum());
        tvPraise.setText(musicEntity.getPraisenum());
        tvComment.setText(musicEntity.getCommentnum());

    }






    /**
     * 请求音乐数据
     * @param s
     */
    private void getMusicRequest(String s) {
        new MyRequest(getContext().getApplicationContext()).getRequest(URL_MUSIC+s+"?", new HttpListener() {
            @Override
            public void onSuccess(String result) {
                musicEntity = parse2JsonMusic(result);
                loadMusicView(musicEntity);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(mContext,"数据请求失败",Toast.LENGTH_SHORT).show();
                Log.i("musicRequest",volleyError.toString());
            }
        });
    }


    /**
     * 解析音乐数据
     * @param result
     * @return musicEntity
     */
    private MusicEntity parse2JsonMusic(String result) {
        MusicEntity musicEntity = null;
        try {
            musicEntity = new MusicEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject joData = jsonObject.getJSONObject("data");
            musicEntity.setId(joData.getString("id"));
            musicEntity.setTitle(joData.getString("title"));
            musicEntity.setCover(joData.getString("cover"));
            musicEntity.setIsfirst(joData.getString("isfirst"));
            musicEntity.setStory_title(joData.getString("story_title"));
            musicEntity.setStory(joData.getString("story"));
            musicEntity.setLyric(joData.getString("lyric"));
            musicEntity.setInfo(joData.getString("info"));
            musicEntity.setPlatform(joData.getString("platform"));
            musicEntity.setMusic_id(joData.getString("music_id"));
            musicEntity.setCharge_edit(joData.getString("charge_edt"));
            musicEntity.setRelated_to(joData.getString("related_to"));
            musicEntity.setWeb_url(joData.getString("web_url"));
            musicEntity.setPraisenum(joData.getString("praisenum"));
            musicEntity.setSort(joData.getString("sort"));
            musicEntity.setMaketime(joData.getString("maketime"));
            musicEntity.setLast_update_date(joData.getString("last_update_date"));
            musicEntity.setRead_num(joData.getString("read_num"));
            musicEntity.setSharenum(joData.getString("sharenum"));
            musicEntity.setCommentnum(joData.getString("commentnum"));

            JSONObject joAuthor = joData.getJSONObject("author");
            musicEntity.setUser_id(joAuthor.getString("user_id"));
            musicEntity.setUser_name(joAuthor.getString("user_name"));
            musicEntity.setAuthor_url(joAuthor.getString("web_url"));
            musicEntity.setDesc(joAuthor.getString("desc"));

            if (!joData.getString("story_author").equals("null")) {
                //Log.i("joData","ddddd"+joData.getString("story_author"));
                JSONObject joStoryAuthor = joData.getJSONObject("story_author");
                musicEntity.setStory_author_id(joStoryAuthor.getString("user_id"));
                musicEntity.setStory_author_name(joStoryAuthor.getString("user_name"));
                musicEntity.setStory_author_url(joStoryAuthor.getString("web_url"));
            }
            //Log.i("json",musicEntity.getStory_title());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return musicEntity;

    }


    /**
     * 请求音乐列表ID数据
     */
    private void getMusicList() {
        new MyRequest(getContext().getApplicationContext()).getRequest(URL_MUSICLIST, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                parse2JsonMusicList(result);
                getMusicRequest(musicList.get(0));
                URL_COMMENT_ALL = URL_COMMENT+musicList.get(0)+"/0?";
                requestCommentData(URL_COMMENT_ALL);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("musicList", volleyError.toString());
            }
        });
    }

    /**
     * 请求评论数据
     * @param urlComment API
     */
    private void requestCommentData(String urlComment) {
        new MyRequest(mContext).getRequest(urlComment, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                commentList = parse2Json4Comment(result);
                loadCommentListView(commentList);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(mContext,"数据请求错误",Toast.LENGTH_SHORT).show();
                Log.i("result",volleyError.toString());
            }
        });
    }

    private void loadCommentListView(List<CommentEntity> commentList) {
        lvComment.setFocusable(false);
        commentAdapter = new CommentListAdapter(mContext,commentList);
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

    /**
     * 解析音乐列表并添加到MUSUCLIST中
     * @param result
     */
    private void parse2JsonMusicList(String result) {
        try {
            musicList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                musicList.add(jsonArray.getString(i));
                //Log.i("musicList",musicList.toString()+"");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAuthor:

                break;
            case R.id.imgbPlay:

                break;
            case R.id.imgbStory:
                layoutStory.setVisibility(View.VISIBLE);
                layoutLyric.setVisibility(View.GONE);
                layoutInfo.setVisibility(View.GONE);
                imgbStory.setBackground(getResources().getDrawable(R.mipmap.ic_music_story_press));
                imgbLyric.setBackground(getResources().getDrawable(R.mipmap.ic_lyric_normal));
                imgbInfo.setBackground(getResources().getDrawable(R.mipmap.ic_musicinfo_normal));
                break;
            case R.id.imgbLyric:
                layoutLyric.setVisibility(View.VISIBLE);
                layoutStory.setVisibility(View.GONE);
                layoutInfo.setVisibility(View.GONE);
                imgbStory.setBackground(getResources().getDrawable(R.mipmap.ic_story_normal));
                imgbLyric.setBackground(getResources().getDrawable(R.mipmap.ic_lyric_press));
                imgbInfo.setBackground(getResources().getDrawable(R.mipmap.ic_musicinfo_normal));
                break;
            case R.id.imgbInfo:
                layoutInfo.setVisibility(View.VISIBLE);
                layoutStory.setVisibility(View.GONE);
                layoutLyric.setVisibility(View.GONE);
                imgbStory.setBackground(getResources().getDrawable(R.mipmap.ic_story_normal));
                imgbLyric.setBackground(getResources().getDrawable(R.mipmap.ic_lyric_normal));
                imgbInfo.setBackground(getResources().getDrawable(R.mipmap.ic_musicinfo_press));
                break;
            case R.id.tvPraise:
                Toast.makeText(getContext().getApplicationContext(),"功能未开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvShare:
                Toast.makeText(getContext().getApplicationContext(),"功能未开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvComment:
                Toast.makeText(getContext().getApplicationContext(),"功能未开发",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
