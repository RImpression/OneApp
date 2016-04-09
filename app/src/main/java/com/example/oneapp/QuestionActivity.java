package com.example.oneapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.entity.QuestionEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/9.
 */
public class QuestionActivity extends BaseActivity {
    private static final String URL_QUESTION = "http://v3.wufazhuce.com:8000/api/question/";
    private String URL_QUESTION_CONTENT;
    private QuestionEntity questionEntity = null;
    private TextView tvQuestionTitle,tvQuestionContent,tvAnswerTitle,tvDate,tvAnswerContent,tvEditor;
    private String ID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initToolbar(R.string.question,true);
        ID = getIntent().getStringExtra("ID");
        URL_QUESTION_CONTENT = URL_QUESTION+ID+"?";
        //Log.i("ID",ID);
        requestQuestionData(URL_QUESTION_CONTENT);
        initViews();
    }

    private void initViews() {
        tvQuestionTitle = (TextView) findViewById(R.id.tvQuestionTitle);
        tvQuestionContent = (TextView) findViewById(R.id.tvQuestionContent);
        tvAnswerTitle = (TextView) findViewById(R.id.tvAnswerTitle);
        tvAnswerContent = (TextView) findViewById(R.id.tvAnswerContent);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvEditor = (TextView) findViewById(R.id.tvEditor);
    }


    /**
     * 请求问题数据
     * @param URL
     */
    private void requestQuestionData(String URL) {
        new MyRequest(this).getRequest(URL, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                parse2Json(result);
                loadView();
            }

            @Override
            public void onError(VolleyError volleyError) {
                ShowToast("数据请求错误");
                Log.i("result",volleyError.toString());
            }
        });

    }

    //加载布局
    private void loadView() {
        tvQuestionTitle.setText(questionEntity.getQuestion_title());
        tvQuestionContent.setText(questionEntity.getQuestion_content());
        tvAnswerTitle.setText(questionEntity.getAnswer_title());
        tvAnswerContent.setText(Html.fromHtml(questionEntity.getAnswer_content()));
        tvDate.setText(questionEntity.getQuestion_makettime());
        tvEditor.setText(questionEntity.getCharge_edit());
    }

    /**
     * 解析问题数据
     * @param result
     */
    private QuestionEntity parse2Json(String result) {
        try {
            questionEntity = new QuestionEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("data");
            questionEntity.setQuestion_id(object.getString("question_id"));
            questionEntity.setQuestion_title(object.getString("question_title"));
            questionEntity.setQuestion_content(object.getString("question_content"));
            questionEntity.setAnswer_title(object.getString("answer_title"));
            questionEntity.setAnswer_content(object.getString("answer_content"));
            questionEntity.setQuestion_makettime(object.getString("question_makettime"));
            questionEntity.setRecommend_flag(object.getString("recommend_flag"));
            questionEntity.setCharge_edit(object.getString("charge_edt"));
            questionEntity.setLast_update_date(object.getString("last_update_date"));
            questionEntity.setReadnum(object.getInt("read_num"));
            questionEntity.setPraisenum(object.getInt("praisenum"));
            questionEntity.setSharenum(object.getInt("sharenum"));
            questionEntity.setCommentnum(object.getInt("commentnum"));
            return questionEntity;
            //Log.i("json",questionEntity.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
