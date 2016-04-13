package com.example.oneapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.adapter.RecyclerAdapter2Movie;
import com.example.entity.MovieEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.example.oneapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RImpression on 2016/3/21 0021.
 */
public class FragmentMovie extends Fragment {

    View view;
    private static final String URL_MOVIE = "http://v3.wufazhuce.com:8000/api/movie/list/0?";
    private boolean isFirst = true;
    private RecyclerView movieRecycleView = null;
    private List<MovieEntity> mDataList = null;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter2Movie adapter2Movie;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirst == true) {
            getMovieRequest();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        if (isFirst == false){
            loadRecyclerView(mDataList);
        }

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie,container,false);
        return view;
    }


    private void initViews() {
        movieRecycleView = (RecyclerView) getView().findViewById(R.id.movieRecycle);
    }

    private void getMovieRequest() {
        new MyRequest(getContext().getApplicationContext()).getRequest(URL_MOVIE, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("movieResult",result);
                mDataList = parse2Json(result);
                loadRecyclerView(mDataList);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(getContext().getApplicationContext(),"请求数据失败",Toast.LENGTH_SHORT).show();
                Log.i("movieResult",volleyError.toString());
            }
        });
    }

    private void loadRecyclerView(List<MovieEntity> mDataList) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        adapter2Movie = new RecyclerAdapter2Movie(getContext(),mDataList);
        movieRecycleView.setLayoutManager(linearLayoutManager);
        movieRecycleView.setAdapter(adapter2Movie);
    }

    /**
     * 解析电影数据
     * @param result
     * @return movieList
     */
    private List<MovieEntity> parse2Json(String result) {
        List<MovieEntity> movieList = null;
        MovieEntity movieEntity = null;
        try {
            movieList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                movieEntity = new MovieEntity();
                JSONObject object = jsonArray.getJSONObject(i);
                movieEntity.setId(object.getString("id"));
                movieEntity.setTitle(object.getString("title"));
                movieEntity.setVerse(object.getString("verse"));
                movieEntity.setVerse_en(object.getString("verse_en"));
                movieEntity.setScore(object.getString("score"));
                movieEntity.setRevisedscore(object.getString("revisedscore"));
                movieEntity.setReleasetime(object.getString("releasetime"));
                movieEntity.setScoretime(object.getString("scoretime"));
                movieEntity.setCover(object.getString("cover"));
                //Log.i("json",movieEntity.getTitle());
                movieList.add(movieEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }
}
