package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.entity.ArticleEntity;
import com.example.oneapp.BaseActivity;
import com.example.oneapp.EssayActivity;
import com.example.oneapp.QuestionActivity;
import com.example.oneapp.R;
import com.example.oneapp.SerializeActivity;

import java.util.List;


/**
 * Created by lcr on 16/3/30.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHoder> {

    private List<ArticleEntity> mArticleList;
    private List<Integer> typeList;
    private Context mContext;


    public RecyclerAdapter(List<ArticleEntity> mList,Context context){
        this.mArticleList = mList;
        this.mContext = context;
    }

    @Override
    public RecyclerAdapter.RecyclerViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article_card,parent,false);
        return new RecyclerViewHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.RecyclerViewHoder holder, int position) {

        final ArticleEntity entity = mArticleList.get(position);
        typeList = entity.getType();
        holder.tvDateList.setText(entity.getDate());
        for (int i=0;i<typeList.size();i++){
            if (typeList.get(i) == 3){
                holder.cardView3.setVisibility(View.VISIBLE);
                holder.tvType3.setText("问答");
                holder.tvQuestTitle.setText(entity.getQuestion_title());
                holder.tvAnswerTitle.setText(entity.getAnswer_title());
                holder.tvAnswerContent.setText(entity.getAnswer_content());
                holder.cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intentView(entity.getQuestion_id(),QuestionActivity.class);
                    }
                });

            }else if (typeList.get(i) == 2){
                holder.cardView2.setVisibility(View.VISIBLE);
                holder.tvType2.setText("连载");
                holder.tvTitle.setText(entity.getTitle());
                holder.tvUserName.setText(entity.getUser_name());
                holder.tvExcerpt.setText(entity.getExcerpt());
                holder.cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intentView(entity.getId(), SerializeActivity.class);
                    }
                });

            } else if (typeList.get(i) == 1){
                holder.cardView1.setVisibility(View.VISIBLE);
                holder.tvType1.setText("短篇");
                holder.tvHPTitle.setText(entity.getHp_title());
                holder.tvUserName1.setText(entity.getUser_name());
                holder.tvGuideWord.setText(entity.getGuide_word());
                holder.cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intentView(entity.getContent_id(), EssayActivity.class);
                    }
                });
            }
        }
    }

    private void intentView(String id, Class<?> activityClass) {
        Intent intent = new Intent();
        intent.putExtra("ID",id);
        intent.setClass(mContext,activityClass);
        mContext.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return mArticleList.size();
    }





    public class RecyclerViewHoder extends RecyclerView.ViewHolder {
        protected TextView tvType1,tvType2,tvType3;
        protected TextView tvHPTitle,tvTitle,tvQuestTitle;
        protected TextView tvUserName1,tvUserName,tvAnswerTitle;
        protected TextView tvGuideWord,tvExcerpt,tvAnswerContent;
        protected CardView cardView1,cardView2,cardView3;
        protected TextView tvDateList;

        public RecyclerViewHoder(View itemView) {
            super(itemView);
            tvDateList = (TextView) itemView.findViewById(R.id.tvDateList);
            tvType1 = (TextView) itemView.findViewById(R.id.tvType1);
            tvHPTitle = (TextView) itemView.findViewById(R.id.tvHPTitle);
            tvUserName1 = (TextView) itemView.findViewById(R.id.tvUserName1);
            tvGuideWord = (TextView) itemView.findViewById(R.id.tvGuideWord);
            tvType2 = (TextView) itemView.findViewById(R.id.tvType2);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvExcerpt = (TextView) itemView.findViewById(R.id.tvExcerpt);
            tvType3 = (TextView) itemView.findViewById(R.id.tvType3);
            tvQuestTitle = (TextView) itemView.findViewById(R.id.tvQuestionTitle);
            tvAnswerTitle = (TextView) itemView.findViewById(R.id.tvAnswerTitle);
            tvAnswerContent = (TextView) itemView.findViewById(R.id.tvAnserContent);
            cardView1 = (CardView) itemView.findViewById(R.id.cardView1);
            cardView2 = (CardView) itemView.findViewById(R.id.cardView2);
            cardView3 = (CardView) itemView.findViewById(R.id.cardView3);
        }
    }
}
