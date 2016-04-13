package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.MovieEntity;
import com.example.oneapp.MovieDetailActivitty;
import com.example.oneapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lcr on 16/4/6.
 */
public class RecyclerAdapter2Movie extends RecyclerView.Adapter<RecyclerAdapter2Movie.RecyclerViewHoder> {

    private List<MovieEntity> movieList;
    private Context mContext;

    public RecyclerAdapter2Movie(Context context,List<MovieEntity> dataList) {
        this.mContext = context;
        this.movieList = dataList;
    }



    @Override
    public RecyclerViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item,parent,false);
        return new RecyclerViewHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHoder holder, int position) {
        final MovieEntity entity = movieList.get(position);
        Picasso.with(mContext).load(entity.getCover()).into(holder.imgMovie);
        if (entity.getScore() == "null") {
            holder.tvScore.setVisibility(View.GONE);
            holder.tvText.setVisibility(View.VISIBLE);
            holder.tvText.setText(R.string.movie_text);
        } else {
            holder.tvScore.setVisibility(View.VISIBLE);
            holder.tvText.setVisibility(View.GONE);
            holder.tvScore.setText(entity.getScore());
        }

        holder.rlMovieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,entity.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(mContext, MovieDetailActivitty.class);
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHoder extends RecyclerView.ViewHolder {
        private ImageView imgMovie;
        private TextView tvScore,tvText;
        private RelativeLayout rlMovieItem;

        public RecyclerViewHoder(View itemView) {
            super(itemView);
            imgMovie = (ImageView) itemView.findViewById(R.id.imgMovie);
            tvScore = (TextView) itemView.findViewById(R.id.tvScore);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            rlMovieItem = (RelativeLayout) itemView.findViewById(R.id.rlMovieItem);
        }
    }
}
