package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.CommentEntity;
import com.example.oneapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RImpression on 2016/5/23 0023.
 */
public class CommentListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CommentEntity> commentList;
    private Context mContext;

    public CommentListAdapter(Context content,List<CommentEntity> entities) {
        this.commentList = entities;
        this.mContext = content;
        this.mInflater = LayoutInflater.from(content);
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentEntity entity = commentList.get(position);
        //Log.i("resultEn",entity.getUser_name());
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_item_comment,null);
            holder.imgAuthor = (ImageView) convertView.findViewById(R.id.imgAuthor);
            holder.tvAuthorName = (TextView) convertView.findViewById(R.id.tvAuthorName);
            holder.tvAuthorTime = (TextView) convertView.findViewById(R.id.tvAuthorTime);
            holder.tvAuthorPraise = (TextView) convertView.findViewById(R.id.tvAuthorPraise);
            holder.tvAuthorComment = (TextView) convertView.findViewById(R.id.tvAuthorComment);
            holder.tvAuthorScore = (TextView) convertView.findViewById(R.id.tvAuthorScore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(entity.getWeb_url()).into(holder.imgAuthor);
        holder.tvAuthorName.setText(entity.getUser_name());
        holder.tvAuthorTime.setText(entity.getInput_date());
        holder.tvAuthorPraise.setText(String.valueOf(entity.getPraisenum()));
        holder.tvAuthorComment.setText(entity.getContent());
        //电影评论有影评分数
        if (entity.getScore() != null){
            holder.tvAuthorScore.setVisibility(View.VISIBLE);
            holder.tvAuthorScore.setText(entity.getScore());
        } else {
            holder.tvAuthorScore.setVisibility(View.GONE);
        }


        return convertView;
    }

    class ViewHolder {
        private TextView tvAuthorName,tvAuthorTime,tvAuthorPraise,tvAuthorComment,tvAuthorScore;
        private ImageView imgAuthor;
    }
}