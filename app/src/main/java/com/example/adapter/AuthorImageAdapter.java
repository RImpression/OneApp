package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entity.GuideDetailEntity;
import com.example.oneapp.R;

import java.util.List;

/**
 * Created by RImpression on 2016/5/28 0028.
 */
public class AuthorImageAdapter extends BaseAdapter {
    private List<GuideDetailEntity> dataList;
    private Context mContext;
    private LayoutInflater mInflater;

    public AuthorImageAdapter(Context context,List<GuideDetailEntity> entities) {
        this.mContext = context;
        this.dataList = entities;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GuideDetailEntity entity = dataList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_author_article,null);
            holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvNumber.setText(String.valueOf(position));
        holder.tvTitle.setText(entity.getTitle());
        holder.tvAuthor.setText(entity.getAuthor());
        holder.tvContent.setText(entity.getIntroduction());
        return convertView;
    }

    class ViewHolder {
        TextView tvNumber,tvTitle,tvAuthor,tvContent;
    }
}
