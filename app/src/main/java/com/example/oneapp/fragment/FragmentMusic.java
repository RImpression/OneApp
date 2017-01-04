package com.example.oneapp.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
import com.example.https.OneApi;
import com.example.interfaces.HttpListener;
import com.example.oneapp.R;
import com.example.utils.CircleTransform;
import com.example.utils.DateFormatUtil;
import com.example.utils.PariseUtil;
import com.example.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RImpression on 2016/3/21 0021.
 */
public class FragmentMusic extends Fragment implements View.OnClickListener {

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
    private boolean isClick = false;
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
        Picasso.with(mContext).load(musicEntity.getCover()).fit().centerCrop().into(imgMusic);
        Picasso.with(mContext).load(musicEntity.getAuthor_url()).transform(new CircleTransform()).fit().centerCrop().into(imgAuthor);
        tvAuthorName.setText(musicEntity.getUser_name());
        tvAuthorInfo.setText(musicEntity.getDesc());
        tvMusicTitle.setText(musicEntity.getTitle());
        tvMusicTime.setText(DateFormatUtil.setDataFormat(musicEntity.getMaketime()));
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
        String URL_MUSIC = OneApi.URL_MUSIC +s+ "?";
        MyRequest.getRequest(getContext().getApplicationContext(),URL_MUSIC, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                musicEntity = MusicEntity.parse2JsonMusic(result);
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
     * 请求音乐列表ID数据
     */
    private void getMusicList() {
        MyRequest.getRequest(getContext().getApplicationContext(),OneApi.URL_MUSIC_LIST, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                parse2JsonMusicList(result);
                getMusicRequest(musicList.get(0));
                URL_COMMENT_ALL = OneApi.URL_MUSIC_COMMENT+musicList.get(0)+"/0?";
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
        MyRequest.getRequest(getContext().getApplicationContext(),urlComment, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                commentList = CommentEntity.parse2Json4Comment(result);
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
                startMusic();
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
                isClick = PariseUtil.PariseClick(getContext().getApplicationContext(),tvPraise,isClick);
                break;
            case R.id.tvShare:
                Toast.makeText(getContext().getApplicationContext(),"功能未开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvComment:
                Toast.makeText(getContext().getApplicationContext(),"功能未开发",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private MediaPlayer mediaPlayer = null;
    private int musicType = 0;//音乐的三种状态。0==初始，1==暂停，2==继续
    String musicUrl = "http://music.wufazhuce.com/lsObPs5v_KBby0Oe5Uq_eUoWYOYt";
    private void startMusic() {
        Log.i("musicPlay","music id + " + musicEntity.getMusic_id());
        if (musicType == 0) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(musicUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();
                musicType = 1;
                imgbPlay.setBackground(getResources().getDrawable(R.mipmap.ic_music_pause));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),"此歌曲不支持播放！",Toast.LENGTH_SHORT).show();
            }
        } else if (musicType == 1) {
            mediaPlayer.pause();
            musicType = 2;
            imgbPlay.setBackground(getResources().getDrawable(R.mipmap.ic_music_player));
        } else {
            mediaPlayer.start();
            musicType = 1;
            imgbPlay.setBackground(getResources().getDrawable(R.mipmap.ic_music_pause));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
