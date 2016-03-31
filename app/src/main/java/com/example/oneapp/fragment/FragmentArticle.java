package com.example.oneapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.adapter.RecyclerAdapter;
import com.example.entity.ArticleEntity;
import com.example.entity.GuideReadingEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.example.oneapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by RImpression on 2016/3/20 0020.
 */
public class FragmentArticle extends Fragment {

    private String URL_ARITICLE = "http://v3.wufazhuce.com:8000/api/reading/index/0?";
    private String URL_PHTOT = "http://v3.wufazhuce.com:8000/api/reading/carousel/?";
    private Boolean isFirst = true;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;
    private List<ArticleEntity> mDataList = null;


    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合
    private List<View> dots; // 图片标题正文的那些点
    private List<View> dotList;

    private int currentItem = 0; // 当前图片的索引号
    // 定义的五个指示点
    private View dot0;
    private View dot1;
    private View dot2;
    private View dot3;
    private View dot4;
    private View dot5;
    private View dot6;
    private View dot7;
    private View dot8;

    // 定时任务
    private ScheduledExecutorService scheduledExecutorService;

    // 轮播banner的数据
    private List<GuideReadingEntity> adList = null;

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
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);

        initView();
        startAd();
        if (isFirst == false){
            loadRecycleView(mDataList);
            adViewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
            // 设置一个监听器，当ViewPager中的页面改变时调用
            adViewPager.setOnPageChangeListener(new MyPageChangeListener());
            addDynamicView();
        }

    }



    private void initView() {

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
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            // 异步加载图片
            Picasso.with(getContext()).load(adList.get(i).getCover()).resize(250,113).centerCrop().into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
            dots.get(i).setVisibility(View.VISIBLE);
            dotList.add(dots.get(i));
        }

    }

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

    private class MyAdapter extends PagerAdapter {

//        private List<ImageView> imageViews = new ArrayList<>();
//        private List<GuideReadingEntity> adList = new ArrayList<>();
//
//
//        public MyAdapter(List<ImageView> imageViews,List<GuideReadingEntity> adList){
//            this.imageViews = imageViews;
//            this.adList = adList;
//        }

        @Override
        public int getCount() {
            return adList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i("adList",adList.get(0).getTitle()+"444444444444"+position);
            Log.i("adList",imageViews.toString()+"444444444444"+position);
            ImageView iv = imageViews.get(position);
            ((ViewPager) container).addView(iv);
            final GuideReadingEntity adDomain = adList.get(position);
            // 在这个方法里面设置图片的点击事件
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 处理跳转逻辑
                }
            });
            return iv;
        }



        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }

    }









    /**
     * 请求轮播图片数据
     */
    private void getPhotoRequest() {
        new MyRequest(getContext().getApplicationContext()).getRequest(URL_PHTOT, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                adList = parse2PhotoJson(result);
                addDynamicView();
                adViewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
                // 设置一个监听器，当ViewPager中的页面改变时调用
                adViewPager.setOnPageChangeListener(new MyPageChangeListener());

            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("photoRequest", volleyError.toString());
            }
        });

    }


    /**
     * 解析轮播图片数据
     * @param result
     */
    private List<GuideReadingEntity> parse2PhotoJson(String result) {
        List<GuideReadingEntity> entitiyList = null;
        GuideReadingEntity guideEntity = null;
        try {
            entitiyList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                guideEntity = new GuideReadingEntity();
                JSONObject object = jsonArray.getJSONObject(i);
                guideEntity.setId(object.getString("id"));
                guideEntity.setTitle(object.getString("title"));
                guideEntity.setCover(object.getString("cover"));
                guideEntity.setBottom_text(object.getString("bottom_text"));
                guideEntity.setBgcolor(object.getString("bgcolor"));
                guideEntity.setPv_url(object.getString("pv_url"));
                Log.i("json",guideEntity.getTitle());
                entitiyList.add(guideEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entitiyList;
    }


    /**
     * 请求阅读文章数据
     */
    private void getArticleRequest() {
        new MyRequest(getContext().getApplicationContext()).getRequest(URL_ARITICLE, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                isFirst = false;
                mDataList = parse2Json(result);
                loadRecycleView(mDataList);//装载RecycleView
                //Log.i("articleData", result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                isFirst = true;
                Toast.makeText(getContext().getApplicationContext(), "Request Error", Toast.LENGTH_SHORT).show();
                Log.i("articleData", volleyError.toString());
            }
        });
    }

    private void loadRecycleView(List<ArticleEntity> mDataList) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new RecyclerAdapter(mDataList);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);
        //添加分割线
        //mRecyclerView.addItemDecoration();
    }


    /**
     * 文章数据解析
     * @param result
     * @return
     */
    private List<ArticleEntity> parse2Json(String result) {
        List<ArticleEntity> articleEntityList=null;
        try {
           articleEntityList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            ArticleEntity articleEntity = null;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                articleEntity= new ArticleEntity();
                JSONObject jsb = jsonArray.getJSONObject(i);
                articleEntity.setDate(jsb.getString("date"));
                JSONArray jaItem = jsb.getJSONArray("items");
                for (int j = 0; j < jaItem.length(); j++) {
                    JSONObject joItem = jaItem.getJSONObject(j);
                    articleEntity.setTime(joItem.getString("time"));
                    articleEntity.setType(joItem.getInt("type"));

                    if (articleEntity.getType().get(j) == 3){   //类型3
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setQuestion_id(joContent.getString("question_id"));
                        articleEntity.setQuestion_title(joContent.getString("question_title"));
                        articleEntity.setAnswer_title(joContent.getString("answer_title"));
                        articleEntity.setAnswer_content(joContent.getString("answer_content"));
                        articleEntity.setQuestion_makettime(joContent.getString("question_makettime"));
                        //Log.i("json",articleEntity.getQuestion_title());

                    } else if (articleEntity.getType().get(j) == 2){  //类型2
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setId(joContent.getString("id"));
                        articleEntity.setSerial_id(joContent.getString("serial_id"));
                        articleEntity.setNumber(joContent.getString("number"));
                        articleEntity.setTitle(joContent.getString("title"));
                        articleEntity.setExcerpt(joContent.getString("excerpt"));
                        articleEntity.setRead_num(joContent.getString("read_num"));
                        articleEntity.setMaketime(joContent.getString("maketime"));
                        articleEntity.setHas_audio(joContent.getBoolean("has_audio"));

                        JSONObject joAuthor = joContent.getJSONObject("author");
                        articleEntity.setUser_id(joAuthor.getString("user_id"));
                        articleEntity.setUser_name(joAuthor.getString("user_name"));
                        articleEntity.setWeb_url(joAuthor.getString("web_url"));
                        articleEntity.setDesc(joAuthor.getString("desc"));


                    } else if (articleEntity.getType().get(j) == 1){  //类型1
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setContent_id(joContent.getString("content_id"));
                        articleEntity.setHp_title(joContent.getString("hp_title"));
                        articleEntity.setGuide_word(joContent.getString("guide_word"));
                        articleEntity.setHas_audio(joContent.getBoolean("has_audio"));
                        JSONArray jaAuthor = joContent.getJSONArray("author");
                        for (int k=0;k<jaAuthor.length();k++){
                            JSONObject joAuthor = jaAuthor.getJSONObject(k);
                            articleEntity.setUser_id(joAuthor.getString("user_id"));
                            articleEntity.setUser_name(joAuthor.getString("user_name"));
                            articleEntity.setWeb_url(joAuthor.getString("web_url"));
                            articleEntity.setDesc(joAuthor.getString("desc"));
                            articleEntity.setWb_name(joAuthor.getString("wb_name"));
                            Log.i("json",joAuthor.toString());
                        }

                    }
                    //Log.i("json", articleEntity.getDate());
                }
                articleEntityList.add(articleEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articleEntityList;
    }

}
