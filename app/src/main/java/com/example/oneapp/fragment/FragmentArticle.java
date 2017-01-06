package com.example.oneapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.adapter.MyAdapter;
import com.example.adapter.RecyclerAdapter;
import com.example.entity.ArticleEntity;
import com.example.entity.GuideReadingEntity;
import com.example.https.MyRequest;
import com.example.https.OneApi;
import com.example.interfaces.HttpListener;
import com.example.oneapp.R;
import com.example.utils.MyLinearLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by RImpression on 2016/3/20 0020.
 */
public class FragmentArticle extends Fragment {

    private Boolean isFirst = true;
    private RecyclerView mRecyclerView;
    private MyLinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;
    private List<ArticleEntity> mDataList = null;


    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合
    private List<View> dots; // 图片标题正文的那些点
    private List<View> dotList;

    private int currentItem = 0; // 当前图片的索引号
    // 定义的五个指示点
    private View dot0,dot1,dot2,dot3,dot4,dot5,dot6,dot7,dot8;

    // 定时任务
    private ScheduledExecutorService scheduledExecutorService;

    // 轮播banner的数据
    private List<GuideReadingEntity> adList = null;
    private View view;
    private RelativeLayout layoutContent;
    private ContentLoadingProgressBar progressBar;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            adViewPager.setCurrentItem(currentItem);
        };
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirst == true){
            getArticleRequest();
            getPhotoRequest();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_article, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        startAd();
        if (isFirst == false){
            loadRecycleView(mDataList);
            loadViewPager();
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        layoutContent = (RelativeLayout) getView().findViewById(R.id.layoutContent);
        progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.progressBar);
        progressBar.show();
        imageViews = new ArrayList<ImageView>();
        // 点
        dots = new ArrayList<View>();
        dotList = new ArrayList<View>();
        dot0 = getView().findViewById(R.id.v_dot0);
        dot1 = getView().findViewById(R.id.v_dot1);
        dot2 = getView().findViewById(R.id.v_dot2);
        dot3 = getView().findViewById(R.id.v_dot3);
        dot4 = getView().findViewById(R.id.v_dot4);
        dot5 = getView().findViewById(R.id.v_dot5);
        dot6 = getView().findViewById(R.id.v_dot6);
        dot7 = getView().findViewById(R.id.v_dot7);
        dot8 = getView().findViewById(R.id.v_dot8);
        dots.add(dot0);
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
        dots.add(dot5);
        dots.add(dot6);
        dots.add(dot7);
        dots.add(dot8);

        adViewPager = (ViewPager) getView().findViewById(R.id.vp);
    }

    private void addDynamicView() {
        // 动态添加图片和下面指示的圆点
        // 初始化图片资源
        for (int i = 0; i < 6; i++) {
            ImageView imageView = new ImageView(getContext());
            // 异步加载图片
            Picasso.with(getContext()).load(adList.get(i).getCover()).fit().centerCrop().into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
            dots.get(i).setVisibility(View.VISIBLE);
            dotList.add(dots.get(i));
        }

    }


    /**
     * 定时任务，5s更换一次
     */
    private void startAd() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,
                TimeUnit.SECONDS);
    }



    private class ScrollTask implements Runnable {

        @Override
        public void run() {
            synchronized (adViewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        private int oldPosition = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            GuideReadingEntity entity = adList.get(position);

            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }
    }


    /**
     * 请求轮播图片数据
     */
    private void getPhotoRequest() {
        MyRequest.getRequest(this.getActivity().getApplicationContext(), OneApi.URL_BANNER_PHOTO, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                adList = GuideReadingEntity.parse2PhotoJson(result);
                loadViewPager();
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("photoRequest", volleyError.toString());
            }
        });

    }





    /**
     * 请求阅读文章数据
     */
    private void getArticleRequest() {
        MyRequest.getRequest(getContext().getApplicationContext(),OneApi.URL_ARTICLE, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                isFirst = false;
                mDataList = ArticleEntity.parse2Json(result);
                loadRecycleView(mDataList);//装载RecycleView
                //Log.i("articleData", result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                isFirst = true;
                Toast.makeText(getContext().getApplicationContext(), "Request Error", Toast.LENGTH_SHORT).show();
                Log.i("articleData", volleyError.toString());
                progressBar.hide();
            }
        });
    }

    private void loadRecycleView(List<ArticleEntity> mDataList) {
        layoutContent.setVisibility(View.VISIBLE);
        progressBar.hide();
//        mRecyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new MyLinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        recyclerAdapter = new RecyclerAdapter(mDataList,getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);
        //添加分割线
        //mRecyclerView.addItemDecoration();
    }

    private void loadViewPager() {
        addDynamicView();
        adViewPager.setAdapter(new MyAdapter(getContext(),adList, imageViews));// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        adViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }




}
