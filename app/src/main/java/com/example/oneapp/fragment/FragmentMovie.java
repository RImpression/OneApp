package com.example.oneapp.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.adapter.RecyclerAdapter2Movie;
import com.example.entity.MovieEntity;
import com.example.https.MyRequest;
import com.example.https.OneApi;
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
public class FragmentMovie extends Fragment implements View.OnClickListener {

    private View view;
    private boolean isFirst = true;
    private RecyclerView movieRecycleView = null;
    private List<MovieEntity> mDataList = null;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter2Movie adapter2Movie;
    private ContentLoadingProgressBar progressBar;
    private FloatingActionButton fabTop;


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
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_movie,container,false);
        }
        return view;
    }

    private void initViews() {
        fabTop = (FloatingActionButton) getView().findViewById(R.id.fabTop);
        movieRecycleView = (RecyclerView) getView().findViewById(R.id.movieRecycle);
        progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.progressBar);
        progressBar.show();

        fabTop.setOnClickListener(this);

        //初始化缩放动画
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(fabTop,"scaleX",0.0f,1.0f);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(fabTop,"scaleY",0.0f,1.0f);
        movieRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 20 || !movieRecycleView.canScrollVertically(-1)) {
//                    Log.i("tag","上滑");
                    fabTop.setVisibility(View.GONE);
                } else if (dy < -30 && fabTop.getVisibility() == View.GONE) {
//                    Log.i("tag","下滑");
                    fabTop.setVisibility(View.VISIBLE);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(animator1).with(animator2);
                    animatorSet.setDuration(200);
                    animatorSet.start();
                }
            }
        });


    }

    private void getMovieRequest() {
        MyRequest.getRequest(getContext().getApplicationContext(),OneApi.URL_MOVIE_LIST, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("movieResult",result);
                mDataList = MovieEntity.parse2Json(result);
                loadRecyclerView(mDataList);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(getContext().getApplicationContext(),"请求数据失败",Toast.LENGTH_SHORT).show();
                Log.i("movieResult",volleyError.toString());
                progressBar.hide();
            }
        });
    }

    private void loadRecyclerView(List<MovieEntity> mDataList) {
        movieRecycleView.setVisibility(View.VISIBLE);
        progressBar.hide();
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        adapter2Movie = new RecyclerAdapter2Movie(getContext(),mDataList);
        movieRecycleView.setLayoutManager(linearLayoutManager);
        movieRecycleView.setAdapter(adapter2Movie);
    }

    //返回顶部
    public void comeBackTop() {
        movieRecycleView.post(new Runnable() {
            @Override
            public void run() {
                movieRecycleView.smoothScrollToPosition(0);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabTop) {
            comeBackTop();
        }
    }
}
