package com.example.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.entity.GuideReadingEntity;

import java.util.List;

/**
 * Created by RImpression on 2016/4/5 0005.
 */
public class MyAdapter extends PagerAdapter {

    private List<GuideReadingEntity> DataList;
    private List<ImageView> imageList;
    private Context mContext;

    public MyAdapter(Context context,List<GuideReadingEntity> adList,List<ImageView> imageViews){
        this.DataList = adList;
        this.imageList = imageViews;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.i("adList", DataList.get(0).getTitle() + "444444444444" + position);
        ImageView iv = imageList.get(position);
        ((ViewPager) container).addView(iv);
        final GuideReadingEntity adDomain = DataList.get(position);
        // 在这个方法里面设置图片的点击事件
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 处理跳转逻辑
                Toast.makeText(mContext,adDomain.getTitle(),Toast.LENGTH_SHORT).show();
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
