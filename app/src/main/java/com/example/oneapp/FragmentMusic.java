package com.example.oneapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.entity.MusicEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RImpression on 2016/3/21 0021.
 */
public class FragmentMusic extends Fragment {

    private String URL_MUSICLIST = "http://v3.wufazhuce.com:8000/api/music/idlist/0?";
    //不完整链接，格式URL_MUSIC+333+?
    private String URL_MUSIC = "http://v3.wufazhuce.com:8000/api/music/detail/";
    private Context mContext;
    private List<String> musicList;
    View view;
    private Button btnmusic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext().getApplicationContext();
        getMusicList();
        initView();
    }

    /**
     * 请求音乐列表ID数据
     */
    private void getMusicList() {
        new MyRequest(mContext).getRequest(URL_MUSICLIST, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                parse2JsonMusicList(result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("musicList",volleyError.toString());
            }
        });
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

    private void initView() {
        btnmusic = (Button) getView().findViewById(R.id.btnMusic);
        btnmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMusicRequest();
            }
        });

    }


    /**
     * 请求音乐数据
     */
    private void getMusicRequest() {
        new MyRequest(mContext).getRequest(URL_MUSIC + musicList.get(0) + "?", new HttpListener() {
            @Override
            public void onSuccess(String result) {
                parse2JsonMusic(result);
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
            musicEntity.setDesc(joAuthor.getString("desc"));

            if (!joData.getString("story_author").equals("null")) {
                Log.i("joData","ddddd"+joData.getString("story_author"));
                JSONObject joStoryAuthor = joData.getJSONObject("story_author");
                musicEntity.setStory_author_id(joStoryAuthor.getString("user_id"));
                musicEntity.setStory_author_name(joStoryAuthor.getString("user_name"));
                musicEntity.setStory_author_url(joStoryAuthor.getString("web_url"));
            }

            Log.i("json",musicEntity.getStory_title());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return musicEntity;

    }
}
